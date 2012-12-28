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

  var Avro = {
    PrimitiveTypes: ['null', 'boolean', 'int', 'long', 'float', 'double', 'bytes', 'string'],
    ComplexTypes: ['record', 'enum', 'array', 'map', 'union', 'fixed']
  };
  Avro.PredefinedTypes = Avro.PrimitiveTypes.concat(Avro.ComplexTypes);

  // * Returns an array of [namespace, name].
  function parseTypeFullName(name) {
    var lastDot = name.lastIndexOf('.');
    if (lastDot === -1) {
      return [null, name];
    } else {
      return [name.substring(0, lastDot), name.substring(lastDot + 1)];
    }
  }

  function qualifiedName(schema, enclosingNamespace) {
    var name, namespace = schema.namespace || enclosingNamespace, nn;

    if (typeof schema === 'string') {
      if (Avro.PrimitiveTypes.indexOf(schema) !== -1) {
        return schema;
      } else {
        name = schema;
      }
    } else if (schema.name) {
      name = schema.name;
    } else if (schema.type) {
      if (Avro.PrimitiveTypes.indexOf(schema.type) !== -1) {
        return schema.type;
      }
      name = schema.type;
    } else {
      throw new Error('Could not find a name for schema ' + JSON.stringify(schema) + '.');
    }

    nn = parseTypeFullName(name);
    namespace = nn[0] || namespace;
    name = nn[1];

    if (namespace) {
      return namespace + '.' + name;
    } else {
      return name;
    }
  }

  Avro.validate = function(schema, value, throwOnFailure, userTypes, enclosingNamespace) {
    throwOnFailure = throwOnFailure === false ? false : true;
    userTypes = userTypes || {};
    var qualName, namespace;

    if (schema instanceof Array) {
      namespace = enclosingNamespace;
    } else {
      qualName = qualifiedName(schema, enclosingNamespace),
      namespace = (schema instanceof Array) ? enclosingNamespace : parseTypeFullName(qualName)[0];
    }

    function validationError(msg) {
      if (throwOnFailure) {
        throw new TypeError('Avro validation failed: ' + msg + ' (value was ' + JSON.stringify(value) + ', schema was ' + JSON.stringify(schema) + ').');
      } else {
        return false;
      }
    }

    function verifyIsString(value) {
      return (typeof value === 'string') || validationError('expected string');
    }

    function verifyIsNonNullObject(value) {
      return (value !== null && typeof value === 'object') || validationError('expected JavaScript object');
    }

    function verifyIsMapLikeObject(value) {
      return verifyIsNonNullObject(value) &&
        (!(value instanceof Array) || validationError('expected JavaScript non-Array object'));
    }

    function validatePrimitive(schema, value) {
      function verifyIsInteger(value) {
        return (Math.floor(value) === value || validationError('expected integer'));
      }

      function verifyIsNumber(value) {
        return (typeof value === 'number' || validationError('expected number'));
      }

      switch (schema) {
      case 'null':
        return (value === null) || validationError('expected null');

      case 'boolean':
        return (value === true || value === false) || validationError('expected boolean');

      case 'int':
        return verifyIsNumber(value) && verifyIsInteger(value) &&
          (Math.abs(value) <= Math.pow(2, 31) || validationError('expected 32-bit integer'));

      case 'long':
        return verifyIsNumber(value) && verifyIsInteger(value) &&
          (Math.abs(value) <= Math.pow(2, 63) || validationError('expected 64-bit integer'));

      case 'float':
        return verifyIsNumber(value); // TODO: handle NaN?

      case 'double':
        return verifyIsNumber(value); // TODO: handle NaN?

      case 'bytes':
        throw new Error('Avro primitive type "bytes" not yet implemented.'); // TODO

      case 'string':
        return verifyIsString(value);

      default:
        throw new Error('Unhandled Avro primitive type: ' + JSON.stringify(schema) + '.');
      }
    }

    function validateComplexType(schema, value) {
      var i, len, key;
      switch (schema.type) {
      case 'record':
        userTypes[qualName] = schema;
        if (!verifyIsMapLikeObject(value)) { // TODO: ugly
          return verifyIsMapLikeObject(value);
        }
        var fieldNames = [];
        for (i = 0, len = schema.fields.length; i < len; i++) {
          var field = schema.fields[i];
          fieldNames.push(field.name);
          if (!Avro.validate(field.type, value[field.name], throwOnFailure, userTypes, namespace)) {
            return validationError('record field ' + JSON.stringify(field.name) + ' failed to validate');
          }
        }
        // Ensure there are no extraneous fields on the object.
        for (key in value) {
          if (value.hasOwnProperty(key)) {
            if (fieldNames.indexOf(key) === -1) {
              return validationError('extraneous field ' + JSON.stringify(key) + ' on object but not in record schema');
            }
          }
        }
        return true;
        
      case 'enum':
        userTypes[qualName] = schema;
        return verifyIsString(value) &&
          (schema.symbols.indexOf(value) !== -1 || validationError('expected enum value to be one of ' + JSON.stringify(schema.symbols)));

      case 'array':
        if (!(value instanceof Array)) {
          return validationError('expected Array');
        }
        for (i = 0, len = value.length; i < len; i++) {
          if (!Avro.validate(schema.items, value[i], throwOnFailure, userTypes, namespace)) {
            return validationError('array item at index ' + i.toString() + ' failed to validate');
          }          
        }
        return true;

      case 'map':
        if (!verifyIsMapLikeObject(value)) { // TODO: ugly
          return verifyIsMapLikeObject(value);
        }
        for (key in value) {
          if (value.hasOwnProperty(key)) {
            if (!Avro.validate(schema.values, value[key], throwOnFailure, userTypes, namespace)) {
              return validationError('map entry at key ' + JSON.stringify(key) + ' failed to validate');
            }
          }
        }
        return true;

      case 'fixed':
        userTypes[qualName] = schema;
        return verifyIsString(value) &&
          (value.length === schema.size || validationError('expected fixed size ' + schema.size));
      default:
        throw new Error('Unhandled Avro complex type: ' + JSON.stringify(schema));
      }
    }

    if (typeof schema === 'string') {
      if (Avro.PrimitiveTypes.indexOf(schema) !== -1) {
        return validatePrimitive(schema, value);
      } else if (userTypes[qualName]) {
        return Avro.validate(userTypes[qualName], value, throwOnFailure, userTypes, namespace);
      } else {
        return validationError('unknown type ' + JSON.stringify(schema) + ' (user types are ' + JSON.stringify(Object.keys(userTypes)) + ')');
      }
    } else if (schema instanceof Array) { // union
      var validated = 0;
      for (var i = 0; i < schema.length; i++) {
        var branchName = qualifiedName(schema[i], namespace);
        console.log('branchName=', branchName, 'schema=', schema, 'ns=', namespace);
        if (Avro.validate(schema[i], value === null ? value : value[branchName], false, userTypes, namespace)) {
          validated += 1;
        }
      }
      if (validated === 0) {
        return validationError('value did not pass validation with any of the union branches');
      } else if (validated === 1) {
        return true;
      } else {
        return validationError('multiple union branches validated, expected only 1');
      }
    } else if (typeof schema === 'object') {
      if (Avro.PrimitiveTypes.indexOf(schema.type) !== -1) {
        return Avro.validate(schema.type, value, throwOnFailure, userTypes, namespace);
      } else if (Avro.ComplexTypes.indexOf(schema.type) !== -1) {
        return validateComplexType(schema, value);
      } else if (userTypes[qualName]) {
        return Avro.validate(userTypes[qualName], value, throwOnFailure, userTypes, namespace);
      } else {
        return validationError('unknown type ' + JSON.stringify(schema) + ' (user types are ' + JSON.stringify(userTypes) + ')');
      }
    } else {
      throw new TypeError('Invalid Avro schema: ' + JSON.stringify(schema));
    }
  };

  if (typeof exports !== 'undefined') {
    exports.Avro = exports.Avro || {};
    exports.Avro.validate = Avro.validate;
  } else {
    this.Avro = this.Avro || {};
    this.Avro.validate = Avro.validate;
  }
}).call(this);

