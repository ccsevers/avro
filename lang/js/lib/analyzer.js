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

  // TODO: these are duplicated in emitter - remove duplication. and if these
  // change here, also update in emitter
  var Avro = {};
  Avro.PrimitiveTypes = ['null', 'boolean', 'int', 'long', 'float', 'double', 'bytes', 'string'];
  Avro.ComplexTypes = ['record', 'enum', 'array', 'map', 'union', 'fixed'];
  Avro.PredefinedTypes = Avro.PrimitiveTypes.concat(Avro.ComplexTypes);
  Avro.NamedTypes = ['record', 'enum', 'fixed'];

  // * Normalizes a type's fullname to a namespace (possibly undefined)
  // * and a name.
  function qualifySchemaName(schema, enclosingNamespace) {
    if (typeof schema === 'string') {
      if (Avro.PrimitiveTypes.indexOf(schema) === -1) {
        schema = qualifiedName(schema, enclosingNamespace);
      }
    } else if (schema.name) {
      if (schema.name.indexOf('.') !== -1) {
        var lastDot = schema.name.lastIndexOf('.');
        schema.namespace = schema.name.substring(0, lastDot);
        schema.name = schema.name.substring(lastDot + 1);
      } else {
        schema.namespace = schema.namespace || enclosingNamespace;
        if (!schema.namespace) {
          delete schema.namespace;
        }
      }
    }
    return schema;
  }

  function qualifiedName(schema, enclosingNamespace) {
    var namespace = schema.namespace || enclosingNamespace,
      name = (typeof schema === 'string') ? schema : schema.name;
    if (name && Avro.PrimitiveTypes.indexOf(name) === -1) {
      if (namespace && name.indexOf('.') === -1) {
        return namespace + '.' + name;
      } else {
        return name;
      }
    } else {
      // if not named type, return original schema
      return schema;
    }
  }

  function analyzeRecord(schema, enclosingNamespace, callerTypes) {
    var newTypes = [schema];
    qualifySchemaName(schema, schema.namespace || enclosingNamespace);
    schema.fields.forEach(function(field) {
      field.type = qualifySchemaName(field.type, schema.namespace);
      newTypes.push.apply(newTypes, analyze(field.type, enclosingNamespace, callerTypes.concat(newTypes)));
      field.type = qualifiedName(field.type, schema.namespace);
    });
    return newTypes;
  }

  function analyzeEnum(schema, enclosingNamespace, callerTypes) {
    return [schema];
  }

  function analyzeArray(schema, enclosingNamespace, callerTypes) {
    return analyze(schema.items, enclosingNamespace, callerTypes);
  }

  function analyzeMap(schema, enclosingNamespace, callerTypes) {
    return analyze(schema.values, enclosingNamespace, callerTypes);
  }

  function analyzeFixed(schema, enclosingNamespace, callerTypes) {
    return [schema];
  }

  var analyzeFnTable = {
    record: analyzeRecord,
    'enum': analyzeEnum,
    array: analyzeArray,
    map: analyzeMap,
    fixed: analyzeFixed
  };

  function analyze(schema, enclosingNamespace, callerTypes) {
    callerTypes = callerTypes || [];

    // TODO: handle type refs like {type: 'a.b.C'} (equiv to 'a.b.C' according to Avro spec?)
    if (typeof schema === 'string') {
      if (Avro.PrimitiveTypes.indexOf(schema) === -1) {
        // field type is a user-defined type - check that we've defined it
        var isDefined = callerTypes.some(function(t) { return qualifiedName(t, enclosingNamespace) === qualifiedName(schema, enclosingNamespace); });
        if (!isDefined) {
          throw new Error('undefined type: "' + schema + '" in enclosing namespace "' + enclosingNamespace + '"');
        }
      }
      return []; // no new types defined here
    } else if (schema instanceof Array) {
      // union
      var newTypes = [];
      schema.forEach(function (branch) {
        newTypes.push.apply(newTypes, analyze(branch, enclosingNamespace, callerTypes.concat(newTypes)));
      });
      return newTypes;
    } else {
      if (Avro.NamedTypes.indexOf(schema.type) !== -1) {
        qualifySchemaName(schema, schema.namespace || enclosingNamespace);
      }
      return analyzeFnTable[schema.type](schema, schema.namespace || enclosingNamespace, callerTypes);
    }
  }

  /**
   * Turns `userTypes`, an Array of flattened types, into an object whose keys are the type names
   * and whose objects are the type definitions.
   */
  function makeTypeMap(userTypes) {
    var typeMap = {};
    userTypes.forEach(function(type) {
      typeMap[qualifiedName(type)] = type;
    });
    return typeMap;
  }

  if (typeof exports !== 'undefined') {
    exports.Avro = exports.Avro || {};
    exports.Avro.analyze = analyze;
    exports.Avro.makeTypeMap = makeTypeMap;
  } else {
    this.Avro = this.Avro || {};
    this.Avro.analyze = analyze;
    this.Avro.makeTypeMap = makeTypeMap;
  }
}).call(this);
