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

  function emitEnum(schema, avroValidate, out) {
    out[qName(schema)] = function(value) {
      if (schema.symbols.indexOf(value) === -1) {
        throw new TypeError('invalid ' + qName(schema) + ' value \"' + value + '\"');
      }
      return value;
    };
    schema.symbols.forEach(function(symbol) {
      out[qName(schema)][symbol] = symbol;
    });
  }

  function emitRecord(schema, avroValidate, out) {
    var rec,
      fieldNames = schema.fields.map(function(f) { return f.name; });

    // Constructor
    rec = out[qName(schema)] = function(data) {
      this.__data = {};
      if (typeof data !== "undefined") {
        this.update(data);
      }
    };

    rec.schema = JSON.stringify(schema);
    rec.fieldNames = JSON.stringify(fieldNames);

    rec.prototype.update = function(data) {
      if (!data || typeof data !== "object" || data instanceof Array) {
        throw new TypeError("attempt to update with a non-Object: " + data);
      }
      for (var fieldName in data) {
        if (data.hasOwnProperty(fieldName)) {
          if (rec.fieldNames.indexOf(fieldName) === -1) {
            throw new TypeError("no such field: " + fieldName);
          }
          this[fieldName] = data[fieldName];
        }
      }
    };

    rec.prototype.toJSON = function() {
      this.__avroValidateRecord();
      return this.__data;
    };

    schema.fields.forEach(function(field) {
      return Object.defineProperty(rec.prototype, field.name, {
        get: function() {
          return this.__data[field.name];
        },
        set: function(newVal) {
          this.__avroValidateField(field.name, newVal);
          this.__data[field.name] = newVal;
        }
      });
    });

    rec.prototype.__avroValidateField = function(fieldName, newVal) {
      var field = schema.fields.filter(function(f) { return f.name === fieldName; })[0];
      return avroValidate(field.type, newVal, true, out.__typemap, schema.namespace); // TODO: set this record's namespace as the enclosingNamespace
    };

    rec.prototype.__avroValidateRecord = function() {
      var self = this;
      schema.fields.forEach(function(field) {
        self.__avroValidateField(field.name, self.__data[field.name]);
      });
    };
  }

  function emitFixed(schema, avroValidate, out) {
    // TODO
  }

  function emit(schema, avroValidate, out) {
    var emitFnTable = {
      record: emitRecord,
      'enum': emitEnum,
      fixed: emitFixed
    };
    return emitFnTable[schema.type](schema, avroValidate, out);
  }

  // * Returns an object containing JS classes for the Avro schema.
  function compile(schema, avro, enclosingNamespace) {
    var out = {};
    var types = avro.analyze(schema, enclosingNamespace);
    types.forEach(function(t) {
      emit(t, avro.validate, out);
    });
    out.__typemap = avro.makeTypeMap(types);
    return out;
  }

  if (typeof exports !== 'undefined') {
    exports.Avro = exports.Avro || {};
    exports.Avro.compile = compile;
  } else {
    this.Avro = this.Avro || {};
    this.Avro.compile = compile;
  }
}).call(this);
