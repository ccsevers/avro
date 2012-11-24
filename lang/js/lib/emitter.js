// Licensed to the Apache Software Foundation (ASF) under one or more
// contributor license agreements.  See the NOTICE file distributed with
// this work for additional information regarding copyright ownership.
// The ASF licenses this file to You under the Apache License, Version 2.0
// (the "License"); you may not use this file except in compliance with
// the License.  You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

(function() {
  'use strict';

  // TODO: these are duplicated in analyzer - remove duplication. and if these
  // change here, also update in analyzer
  var Avro = {};
  Avro.PrimitiveTypes = ['null', 'boolean', 'int', 'long', 'float', 'double', 'bytes', 'string'];
  Avro.ComplexTypes = ['record', 'enum', 'array', 'map', 'union', 'fixed'];

  /**
   * Qualified "fullname" of an Avro type (namespace + name); e.g., "x.y.MyRecord"
   */
  function qName(schema) {
    if (schema.namespace) {
      return schema.namespace + '.' + schema.name;
    } else {
      return schema.name;
    }
  }

  function constructorLHS(schema) {
    return (schema.namespace ? qName(schema) : ('var ' + schema.name));
  }

  function emitEnum(schema) {
    var o = [];
    o.push(emitNamespaceInit(schema.namespace));
    o.push(
      constructorLHS(schema) + ' = function(value) {\n' +
      '  if (' + qName(schema) + '.symbols.indexOf(value) === -1) {\n' +
      '    throw new TypeError("invalid ' + qName(schema) + ' value \\"" + value + "\\"");\n' +
      '  }\n' +
      '  return value;\n' +
      '}\n' +
      qName(schema) + '.symbols = ' + JSON.stringify(schema.symbols) + ';'
    );
    schema.symbols.forEach(function(symbol) {
      o.push(qName(schema) + '.' + symbol + ' = "' + symbol + '";');
    });
    return o.join('\n');
  }

  function emitSection(title) {
    return '\n\n///////////////////\n// ' + title + '\n';
  }

  function emitIncludes() {
    return 'if (typeof require !== "undefined" && typeof Avro === "undefined") {\n' +
      '  var Avro = require("../lib/validator.js").Avro;\n' +
      '}';
  }

  function emitNamespaceInit(namespace) {
    if (!namespace) { return ''; }
    var parts = namespace.split('.'), i, len = parts.length, part, o, breadcrumb;
    o = ['if (typeof ' + parts[0] + ' === "undefined") {\n' +
         '  var ' + parts[0] + ' = {};\n' +
         '}'];
    breadcrumb = parts[0];
    for (i = 1; i < len; i += 1) {
      breadcrumb += '.' + parts[i];
      o.push(
        'if (!' + breadcrumb + ') {\n' +
        '  ' + breadcrumb + ' = {};\n' +
        '}\n');
    }
    return o.join('\n');
  }

  // * Returns a record emitter for the given `recordSchema`, which must be
  // * pre-analyzed by `analyzeRecord`.
  var record = {
    emitDocComment: function(schema) {
      return '/**\n * ' + qName(schema) + ' (AUTOGENERATED)\n */';
    },
    emitConstructor: function(schema) {
      return constructorLHS(schema) + ' = function(data) {\n' +
        '  this.__data = {};\n' +
        '  if (typeof data !== "undefined") {\n' +
        '    this.update(data);\n' +
        '  }\n' +
        '};';
    },
    emitSchema: function(schema) {
      var fieldNames = schema.fields.map(function(f) { return f.name; });
      return qName(schema) + '.schema = ' + JSON.stringify(schema) + ';\n' +
        qName(schema) + '.fieldNames = ' + JSON.stringify(fieldNames) + ';';
    },
    emitUpdateFn: function(schema) {
      return qName(schema) + '.prototype.update = function(data) {\n' +
        '  if (!data || typeof data !== "object" || data instanceof Array) {\n' +
        '    throw new TypeError("attempt to update with a non-Object: " + data);\n' +
        '  }\n' +
        '  for (fieldName in data) {\n' +
        '    if (data.hasOwnProperty(fieldName)) {\n' +
        '      if (' + qName(schema) + '.fieldNames.indexOf(fieldName) === -1) {\n' +
        '        throw new TypeError("no such field: " + fieldName);\n' +
        '      }\n' +
        '      this[fieldName] = data[fieldName];\n' +
        '    }\n' +
        '  }\n' +
        '};';
    },
    emitJsonFn: function(schema) {
      return qName(schema) + '.prototype.toJSON = function() {\n' +
        '  this.__avroValidate();\n' +
        '  return this.__data;\n' +
        '}';
    },
    emitProtoProperties: function(schema) {
      return schema.fields.map(function(field) {
        var newVal = 'new_' + field.name;
        return 'Object.defineProperty(' + qName(schema) + '.prototype, "' + field.name + '", {\n' +
          '  get: function() {\n' +
          '    return this.__data.' + field.name + ';\n' +
          '  },\n' +
          '  set: function(' + newVal + ') {\n' +
          '    this.__avroValidate_' + field.name + '(' + newVal + ');\n' +
          '    this.__data.' + field.name + ' = ' + newVal + ';\n' +
          '  }\n' +
          '});';
      }).join('\n');
    },
    emitAvroValidateFieldBlock: function(schema, field) {
      return '  Avro.validate(' + JSON.stringify(field.type) + ', fieldVal, true, __AvroTypeMap, ' + JSON.stringify(schema.namespace) + ');'; // TODO: set this record's namespace as the enclosingNamespace
    },
    emitAvroValidateFieldFn: function(schema, field) {
      return qName(schema) + '.prototype.__avroValidate_' + field.name + ' = function(fieldVal) {\n' +
        record.emitAvroValidateFieldBlock(schema, field).replace(/\n/g, '\n  ') + '\n' +
        '};';
    },
    emitAvroValidateFns: function(schema) {
      return schema.fields.map(function(field) {
        return record.emitAvroValidateFieldFn(schema, field);
      }).join('\n') + '\n' +
        qName(schema) + '.prototype.__avroValidate = function() {\n' +
        schema.fields.map(function(field) {
          return '  this.__avroValidate_' + field.name + '(this.__data.' + field.name + ');';
        }).join('\n') + '\n' +
        '}';
    },
    emit: function(schema) {
      return [
        record.emitDocComment(schema),
        emitNamespaceInit(schema.namespace),
        record.emitConstructor(schema),
        record.emitSchema(schema),

        emitSection('Setters'),
        record.emitUpdateFn(schema),
        record.emitJsonFn(schema),
        record.emitProtoProperties(schema),

        emitSection('Validation'),
        record.emitAvroValidateFns(schema)
      ].join('\n');
    }
  };

  function emitFixed(schema) {
    // TODO
  }

  function emitTypeMap(typeMap) {
    var typeMapAssignments = [];
    for (var typeName in typeMap) {
      if (typeMap.hasOwnProperty(typeName)) {
        typeMapAssignments.push(
          '__AvroTypeMap["' + typeName + '"] = ' + JSON.stringify(typeMap[typeName]) + ';'
        );
      }
    }
    return 'if (typeof __AvroTypeMap === "undefined") {\n' +
      '  var __AvroTypeMap = {};\n' +
      '}\n' +
      typeMapAssignments.join('\n');
  }

  var emitFnTable = {
    record: record.emit,
    'enum': emitEnum,
    fixed: emitFixed
  };

  function emit(schema) {
    return [emitIncludes(),
            '',
            emitFnTable[schema.type](schema)].join('\n');
  }

  if (typeof exports !== 'undefined') {
    exports.emitEnum = emitEnum;
    exports.record = record;
    exports.emitNamespaceInit = emitNamespaceInit;
    exports.emit = emit;
    exports.emitTypeMap = emitTypeMap;
    // TODO: emitFixed
  }
}).call(this);
