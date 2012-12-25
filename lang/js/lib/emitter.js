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

  function constructorLHS(schema) {
    return (schema.namespace ? qName(schema) : ('var ' + schema.name));
  }

  function emitEnum(schema, out) {
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

  // * Returns a record emitter for the given `recordSchema`, which must be
  // * pre-analyzed by `analyzeRecord`.
  var record = {
    emitConstructor: function(schema, out) {
      out[qName(schema)] = function(data) {
        this.__data = {};
        if (typeof data !== "undefined") {
          this.update(data);
        }
      };
    },
    emitSchema: function(schema, out) {
      var fieldNames = schema.fields.map(function(f) { return f.name; });
      out[qName(schema)].schema = JSON.stringify(schema);
      out[qName(schema)].fieldNames = JSON.stringify(fieldNames);
    },
    emitUpdateFn: function(schema, out) {
      out[qName(schema)].prototype.update = function(data) {
        if (!data || typeof data !== "object" || data instanceof Array) {
          throw new TypeError("attempt to update with a non-Object: " + data);
        }
        for (var fieldName in data) {
          if (data.hasOwnProperty(fieldName)) {
            if (out[qName(schema)].fieldNames.indexOf(fieldName) === -1) {
              throw new TypeError("no such field: " + fieldName);
            }
            this[fieldName] = data[fieldName];
          }
        }
      };
    },
    emitJsonFn: function(schema, out) {
      out[qName(schema)].prototype.toJSON = function() {
        this.__avroValidateRecord();
        return this.__data;
      };
    },
    emitProtoProperties: function(schema, out) {
      schema.fields.forEach(function(field) {
        return Object.defineProperty(out[qName(schema)].prototype, field.name, {
          get: function() {
            return this.__data[field.name];
          },
          set: function(newVal) {
            this.__avroValidateField(field.name, newVal);
            this.__data[field.name] = newVal;
          }
        });
      });
    },
    emitAvroValidateFieldFn: function(schema, out) {
      out[qName(schema)].prototype.__avroValidateField = function(fieldName, newVal) {
        var field = schema.fields.filter(function(f) { return f.name === fieldName; })[0];
        return global.Avro.validate(field.type, newVal, true, out._typemap, schema.namespace); // TODO: set this record's namespace as the enclosingNamespace
      };
    },
    emitAvroValidateFns: function(schema, out) {
      record.emitAvroValidateFieldFn(schema, out);
      out[qName(schema)].prototype.__avroValidateRecord = function() {
        var self = this;
        schema.fields.forEach(function(field) {
          self.__avroValidateField(field.name, self.__data[field.name]);
        });
      };
    },
    emit: function(schema, out) {
      record.emitConstructor(schema, out);
      record.emitSchema(schema, out);
      record.emitUpdateFn(schema, out);
      record.emitJsonFn(schema, out);
      record.emitProtoProperties(schema, out);
      record.emitAvroValidateFns(schema, out);
    }
  };

  function emitFixed(schema) {
    // TODO
  }

  function emit(schema, out) {
    var emitFnTable = {
      record: record.emit,
      'enum': emitEnum,
      fixed: emitFixed
    };
    return emitFnTable[schema.type](schema, out);
  }

  if (typeof exports !== 'undefined') {
    exports.emitEnum = emitEnum;
    exports.record = record;
    exports.emit = emit;
    // TODO: emitFixed
  }
}).call(this);
