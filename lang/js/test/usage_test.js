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
global.Avro = require('../lib/validator.js').Avro;

exports.test = {
  'enum': {
    'returns input value iff input is valid symbol; otherwise throws': function(test) {
      var MyEnum = compiler.compile({type: 'enum', name: 'MyEnum', symbols: ['A']}).MyEnum;
      test.equal(MyEnum('A'), 'A');
      test.throws(function() { return MyEnum('B'); });
      test.throws(function() { return MyEnum(); });
      test.throws(function() { return MyEnum(['A']); });
      test.done();
    }
  },

  'records': {
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
      this.complexFieldsRecord = {type: 'record', name: 'ComplexFieldsRecord', fields: [
        {name: 'mapField', type: {type: 'map', values: 'int'}},
        {name: 'mapFieldNested', type: {type: 'map', values: {type: 'map', values: 'string'}}},
        {name: 'arrayField', type: {type: 'array', items: 'string'}},
        {name: 'arrayFieldNested', type: {type: 'array', items: {type: 'map', values: 'string'}}},
        {name: 'fixedField', type: {type: 'fixed', size: 4}},
        {name: 'enumField', type: {type: 'enum', symbols: ['A']}}
      ]};
      done();
    },
    'constructor': function(test) {
      var A = compiler.compile(this.emptyRecord).A;
      test.ok(new A({}));
      test.ok(new A());
      test.throws(function() { return new A({z: 1}); });
      test.throws(function() { return new A('a'); });
      test.throws(function() { return new A(null); });
      test.throws(function() { return new A([1]); });
      test.done();
    },
    'constructor in namespace': function(test) {
      var NamespacedRecord = compiler.compile(this.namespacedRecord)['x.y.NamespacedRecord'];
      test.ok(new NamespacedRecord());
      test.done();
    },
    'update': function(test) {
      var SFR = compiler.compile(this.stringFieldRecord).StringFieldRecord,
        sfr = new SFR({stringField: 'a'});
      test.equal(sfr.stringField, 'a');
      sfr.update({stringField: 'b'});
      test.equal(sfr.stringField, 'b');
      test.done();
    },
    'two objects': function(test) {
      var SFR = compiler.compile(this.stringFieldRecord).StringFieldRecord,
        sfr1 = new SFR({stringField: 'a'}),
        sfr2 = new SFR({stringField: 'b'});
      test.equal(sfr1.stringField, 'a');
      test.equal(sfr2.stringField, 'b');
      sfr1.stringField = 'aa';
      test.equal(sfr1.stringField, 'aa');
      test.equal(sfr2.stringField, 'b');
      test.done();
    },
    'getters and setters': function(test) {
      var SFR = compiler.compile(this.stringFieldRecord).StringFieldRecord,
        sfr = new SFR();
      test.equal(sfr.stringField, undefined);
      sfr.stringField = 'b';
      test.equal(sfr.stringField, 'b');
      test.done();
    },
    'JSON.stringify': function(test) {
      var SFR = compiler.compile(this.stringFieldRecord).StringFieldRecord,
        sfr = new SFR();
      test.throws(function() { JSON.stringify(sfr); }); // incomplete (no stringField value)
      sfr.stringField = 'b';
      test.equal(JSON.stringify(sfr), '{"stringField":"b"}');
      test.done();
    },
    'complexFieldsRecord': {
      'setUp': function(done) {
        this.ComplexFieldsRecord = compiler.compile(this.complexFieldsRecord).ComplexFieldsRecord;
        this.cfr = new this.ComplexFieldsRecord();
        done();
      },
      'constructor': function(test) {
        var cfr = new this.ComplexFieldsRecord();
        test.done();
      },
      'field validation': function(test) {
        var cfr = this.cfr;

        cfr.mapField = {a: 1};
        test.deepEqual(cfr.mapField, {a: 1});
        test.throws(function() { cfr.mapField = {a: 'a'}; });
        test.throws(function() { cfr.mapField = ['a']; });
        test.throws(function() { cfr.mapField = {a: 1, b: 'b'}; });
        test.deepEqual(cfr.mapField, {a: 1}); // unchanged

        cfr.mapFieldNested = {a: {aa: 'aaa'}};
        test.deepEqual(cfr.mapFieldNested, {a: {aa: 'aaa'}});
        test.throws(function() { cfr.mapFieldNested = {a: 'a'}; });
        test.throws(function() { cfr.mapFieldNested = {a: {aa: 1}}; });
        test.deepEqual(cfr.mapFieldNested, {a: {aa: 'aaa'}}); // unchanged

        cfr.arrayField = ['a'];
        test.deepEqual(cfr.arrayField, ['a']);
        test.throws(function() { cfr.arrayField = [1]; });
        test.throws(function() { cfr.arrayField = ['a', 1]; });
        test.throws(function() { cfr.arrayField = {a: 1}; });
        test.deepEqual(cfr.arrayField, ['a']); // unchanged

        cfr.arrayFieldNested = [{'a': 'aa'}];
        test.deepEqual(cfr.arrayFieldNested, [{'a': 'aa'}]);
        test.throws(function() { cfr.arrayFieldNested = [1]; });
        test.throws(function() { cfr.arrayFieldNested = [{a: 1}]; });
        test.throws(function() { cfr.arrayFieldNested = [{a: []}]; });
        test.deepEqual(cfr.arrayFieldNested, [{'a': 'aa'}]); // unchanged

        cfr.fixedField = 'aaaa';
        test.equal(cfr.fixedField, 'aaaa');
        test.throws(function() { cfr.fixedField = 'aaa'; });
        test.throws(function() { cfr.fixedField = 'aaaaa'; });
        test.throws(function() { cfr.fixedField = 1000; });
        test.equal(cfr.fixedField, 'aaaa'); // unchanged

        cfr.enumField = 'A';
        test.equal(cfr.enumField, 'A');
        test.throws(function() { cfr.enumField = 'B'; });
        test.throws(function() { cfr.enumField = 3; });
        test.throws(function() { cfr.enumField = ['A']; });
        test.equal(cfr.enumField, 'A'); // unchanged

        test.done();
      }
    },
    'Avro field validation': {
      'StringFieldRecord (simple)': function(test) {
        var SFR = compiler.compile(this.stringFieldRecord).StringFieldRecord,
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
        var MFR = compiler.compile(this.manyFieldsRecord).ManyFieldsRecord,
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

