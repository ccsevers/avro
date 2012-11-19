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

/*jshint
 strict: false, evil: true, newcap: false
*/

var compiler = require('../lib/compiler.js');

function compileAndEval(schema) {
  var src = compiler.compile(schema);
  return eval(src + '; ' + (schema.namespace ? schema.namespace + '.' : '') + schema.name + ';');
}

exports.test = {
  'enum': {
    'returns input value iff input is valid symbol; otherwise throws': function(test) {
      var MyEnum = compileAndEval({type: 'enum', name: 'MyEnum', symbols: ['A']});
      test.equal(MyEnum('A'), 'A');
      test.throws(function() { return MyEnum('B'); });
      test.throws(function() { return MyEnum(); });
      test.throws(function() { return MyEnum(['A']); });
      test.done();
    }
  },

  'record': {
    setUp: function(done) {
      this.emptyRecord = {type: 'record', name: 'A', fields: []};
      this.namespacedRecord = {type: 'record', name: 'NamespacedRecord', namespace: 'x.y', fields: []};
      this.stringFieldRecord = {type: 'record', name: 'StringFieldRecord', fields: [{name: 'stringField', type: 'string'}]};
      this.manyFieldsRecord = {type: 'record', name: 'ManyFieldsRecord', fields: [
        {name: 'nullField', type: 'null'},
        {name: 'booleanField', type: 'boolean'},
        {name: 'intField', type: 'int'},
        {name: 'longField', type: 'long'},
        {name: 'floatField', type: 'float'},
        {name: 'doubleField', type: 'double'},
        {name: 'stringField', type: 'string'},
        {name: 'bytesField', type: 'bytes'}
      ]};
      done();
    },
    'constructor': function(test) {
      var A = compileAndEval(this.emptyRecord);
      test.ok(new A({}));
      test.ok(new A());
      test.throws(function() { return new A({z: 1}); });
      test.throws(function() { return new A('a'); });
      test.throws(function() { return new A(null); });
      test.throws(function() { return new A([1]); });
      test.done();
    },
    'constructor in namespace': function(test) {
      var NamespacedRecord = compileAndEval(this.namespacedRecord);
      test.ok(new NamespacedRecord());
      test.done();
    },
    'update': function(test) {
      var SFR = compileAndEval(this.stringFieldRecord),
        sfr = new SFR({stringField: 'a'});
      test.equal(sfr.stringField, 'a');
      sfr.update({stringField: 'b'});
      test.equal(sfr.stringField, 'b');
      test.done();
    },
    'getters and setters': function(test) {
      var SFR = compileAndEval(this.stringFieldRecord),
        sfr = new SFR();
      test.equal(sfr.stringField, undefined);
      sfr.stringField = 'b';
      test.equal(sfr.stringField, 'b');
      test.done();
    },
    'JSON.stringify': function(test) {
      var SFR = compileAndEval(this.stringFieldRecord),
        sfr = new SFR();
      test.throws(function() { JSON.stringify(sfr); }); // incomplete (no stringField value)
      sfr.stringField = 'b';
      test.equal(JSON.stringify(sfr), '{"stringField":"b"}');
      test.done();
    },
    'Avro field validation': {
      'StringFieldRecord (simple)': function(test) {
        var SFR = compileAndEval(this.stringFieldRecord),
        sfr = new SFR();
        function expectThrows() {
          test.throws(function() { sfr.stringField = undefined; });
          test.throws(function() { sfr.stringField = null; });
          test.throws(function() { sfr.stringField = 5; });
          test.throws(function() { sfr.stringField = {a: 1}; });
          test.throws(function() { sfr.stringField = [1,2]; });
          test.throws(function() { sfr.stringField = function() {}; });
        }
        expectThrows();
        sfr.stringField = 'a';
        expectThrows();
        test.equal(sfr.stringField, 'a');
        test.done();
      },
      'ManyFieldsRecord': function(test) {
        var MFR = compileAndEval(this.manyFieldsRecord),
          mfr = new MFR();
        function expectThrows() {
          test.throws(function() { mfr.nullField = undefined; });
          test.throws(function() { mfr.nullField = 1; });
          test.throws(function() { mfr.booleanField = 'a'; });
          test.throws(function() { mfr.intField = 'a'; }); // TODO: warn if setting int/long field to a non-integer
          test.throws(function() { mfr.longField = 'a'; });
          test.throws(function() { mfr.floatField = 'a'; });
          test.throws(function() { mfr.doubleField = 'a'; });
          test.throws(function() { mfr.stringField = 3; });
        }
        expectThrows();
        mfr.nullField = null;
        mfr.booleanField = true;
        mfr.intField = 1;
        mfr.longField = 2;
        mfr.floatField = 3.5;
        mfr.doubleField = 4.5;
        mfr.stringField = 'a';
        test.throws(function() { mfr.bytesField = 'a'; }); // TODO: not yet implemented
        expectThrows();
        test.equal(mfr.nullField, null);
        test.equal(mfr.booleanField, true);
        test.equal(mfr.intField, 1);
        test.equal(mfr.longField, 2);
        test.equal(mfr.floatField, 3.5);
        test.equal(mfr.doubleField, 4.5);
        test.equal(mfr.stringField, 'a');
        test.done();
      }
    }
  }
};

