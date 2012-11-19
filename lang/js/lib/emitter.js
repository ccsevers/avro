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
      return qName(schema) + '.prototype.__data = {};\n' + 
        schema.fields.map(function(field) {
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
      if (typeof field.type === 'string') {
        if (Avro.PrimitiveTypes.indexOf(field.type) !== -1) {
          switch (field.type) {
          case 'null':
            return 'if (fieldVal !== null) {\n' +
              '  throw new TypeError("Avro validation failed: expected null for field ' + field.name + '");\n' +
              '}';
          case 'boolean':
            return 'if (typeof fieldVal !== "boolean" && !(field instanceof Boolean)) {\n' +
              '  throw new TypeError("Avro validation failed: expected boolean for field ' + field.name + '");\n' +
              '}';
          case 'int':
          case 'long':
          case 'float':
          case 'double':
            return 'if (typeof fieldVal !== "number") {\n' +
              '  throw new TypeError("Avro validation failed: expected number for field ' + field.name + '");\n' +
              '}';
          case 'string':
            return 'if (typeof fieldVal !== "string") {\n' +
              '  throw new TypeError("Avro validation failed: expected string for field ' + field.name + '");\n' +
              '}';
          case 'bytes':
            return 'throw new TypeError("Avro bytes type not yet supported");';
          default:
          }
        } else {
          // TODO: user-defined type
          return;
        }
      } else if (Avro.ComplexTypes.indexOf(field.type.type) !== -1) {
        // TODO: Avro complex type
        return;
      }
      throw new TypeError('unknown field type to validate: ' + JSON.stringify(field));
    },
    emitAvroValidateFieldFn: function(schema, field) {
      return qName(schema) + '.prototype.__avroValidate_' + field.name + ' = function(fieldVal) {\n' +
        '  if (typeof fieldVal === "undefined") {\n' +
        '    throw new TypeError("Avro validation failed: missing value for field ' + field.name + '");\n' +
        '  }\n' +
        '\n' +
        record.emitAvroValidateFieldBlock(schema, field) + '\n' +
        '};';
    },
    emitAvroValidateFns: function(schema) {
      return schema.fields.map(function(field) {
        return record.emitAvroValidateFieldFn(schema, field);
      }).join('\n') + '\n' +
        qName(schema) + '.prototype.__avroValidate = function() {' +
        schema.fields.map(function(field) {
          return 'this.__avroValidate_' + field.name + '(this.__data.' + field.name + ');';
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

  var emitFnTable = {
    record: record.emit,
    'enum': emitEnum,
    fixed: emitFixed
  };

  function emit(schema) {
    return emitFnTable[schema.type](schema);
  }

  if (typeof exports !== 'undefined') {
    exports.emitEnum = emitEnum;
    exports.record = record;
    exports.emitNamespaceInit = emitNamespaceInit;
    exports.emit = emit;
    // TODO: emitFixed
  }
}).call(this);
