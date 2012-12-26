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
 strict: false
*/

var analyzer = require('../lib/analyzer.js'),
  analyze = analyzer.Avro.analyze;

function namespaceAndName(schema) {
  return [schema.namespace, schema.name];
}

exports.test = {
  'analyze enum': {
    'splits fullname into name and namespace': function(test) {
      var s = {
        alreadySplit: {type: 'enum', name: 'MyEnum', namespace: 'a.b', symbols: []},
        fullyQualifiedName: {type: 'enum', name: 'a.b.MyEnum', symbols: []},
        noNamespace: {type: 'enum', name: 'MyEnum', symbols: []}
      };
      test.deepEqual(namespaceAndName(analyze(s.alreadySplit)[0]), ['a.b', 'MyEnum']);
      test.deepEqual(namespaceAndName(analyze(s.fullyQualifiedName)[0]), ['a.b', 'MyEnum']);
      test.deepEqual(namespaceAndName(analyze(s.noNamespace)[0]), [undefined, 'MyEnum']);
      test.done();
    }
  },

  'analyze record': {
    'splits fullname into name and namespace': function(test) {
      var s = {
        alreadySplit: {type: 'record', name: 'MyRecord', namespace: 'a.b', fields: []},
        fullyQualifiedName: {type: 'record', name: 'a.b.MyRecord', fields: []},
        noNamespace: {type: 'record', name: 'MyRecord', fields: []}
      };
      test.deepEqual(namespaceAndName(analyze(s.alreadySplit)[0]), ['a.b', 'MyRecord']);
      test.deepEqual(namespaceAndName(analyze(s.fullyQualifiedName)[0]), ['a.b', 'MyRecord']);
      test.deepEqual(namespaceAndName(analyze(s.noNamespace)[0]), [undefined, 'MyRecord']);
      test.done();
    },

    'flattens base schema and collects nested named types': {
      'empty record': function(test) {
        var s = {type: 'record', name: 'A', fields: []};
        test.deepEqual(analyze(s), [s]);
        test.done();
      },
      'record with one non-named-type field': function(test) {
        var s = {type: 'record', name: 'A', fields: [{name: 's', type: 'string'}]};
        test.deepEqual(analyze(s), [s]);
        test.done();
      },
      'record with one inline-defined named-type field': function(test) {
        var s = {type: 'record', name: 'A', fields: [
          {name: 'b', type: {type: 'record', name: 'B', fields: []}}
        ]};
        test.deepEqual(analyze(s), [
          {type: 'record', name: 'A', fields: [{name: 'b', type: 'B'}]},
          {type: 'record', name: 'B', fields: []}
        ]);
        test.done();
      },
      'record with nested inline-defined named-type children': function(test) {
        var s = {type: 'record', name: 'A', fields: [
          {name: 'b', type: {type: 'record', name: 'B', fields: [
            {name: 'c', type: {type: 'record', name: 'C', fields: []}}
          ]}}
        ]};
        test.deepEqual(analyze(s), [
          {type: 'record', name: 'A', fields: [{name: 'b', type: 'B'}]},
          {type: 'record', name: 'B', fields: [{name: 'c', type: 'C'}]},
          {type: 'record', name: 'C', fields: []}
        ]);
        test.done();
      },
      'record with unqualified sub-records': function(test) {
        var s = {type: 'record', name: 'A', namespace: 'a', fields: [
          {name: 'b', type: {type: 'record', name: 'B', fields: []}},
          {name: 'bb', type: 'B'},
          {name: 'bbb', type: 'a.B'},
          {name: 'c', type: {type: 'record', name: 'c.C', fields: []}},
          {name: 'cc', type: 'c.C'},
          {name: 'd', type: {type: 'record', name: 'D', namespace: 'd', fields: []}},
          {name: 'dd', type: 'd.D'}
        ]};
        test.deepEqual(analyze(s), [
          {type: 'record', name: 'A', namespace: 'a', fields: [
            {name: 'b', type: 'a.B'},
            {name: 'bb', type: 'a.B'},
            {name: 'bbb', type: 'a.B'},
            {name: 'c', type: 'c.C'},
            {name: 'cc', type: 'c.C'},
            {name: 'd', type: 'd.D'},
            {name: 'dd', type: 'd.D'}
          ]},
          {type: 'record', name: 'B', namespace: 'a', fields: []},
          {type: 'record', name: 'C', namespace: 'c', fields: []},
          {type: 'record', name: 'D', namespace: 'd', fields: []}
        ]);        
        test.done();
      },
      'record with primitive fields': function(test) {
        var s = {type: 'record', name: 'A', namespace: 'a', fields: [{name: 's', type: 'string'}]};
        test.deepEqual(analyze(s), [
          {type: 'record', name: 'A', namespace: 'a', fields: [{name: 's', type: 'string'}]}
        ]);
        test.done();
      }
    }
  },

  'analyze union': {
    'collect branches': function(test) {
      test.deepEqual(
        analyze([
          'string',
          {type: 'record', name: 'A', fields: [{name: 'b', type: {type: 'record', name: 'B', fields: []}}]},
          {type: 'record', name: 'C', fields: [{name: 'a', type: 'A'}, {name: 'b', type: 'B'}]}
        ]),
        [{type: 'record', name: 'A', fields: [{name: 'b', type: 'B'}]},
         {type: 'record', name: 'B', fields: []},
         {type: 'record', name: 'C', fields: [{name: 'a', type: 'A'}, {name: 'b', type: 'B'}]}]
      );
      test.done();
    }
  },

  'analyze array': function(test) {
    test.deepEqual(
      analyze({type: 'array', items: {type: 'record', name: 'A', fields: []}}),
      [{type: 'record', name: 'A', fields: []}]
    );
    test.done();
  },

  'analyze map': function(test) {
    test.deepEqual(
      analyze({type: 'map', values: {type: 'record', name: 'A', fields: []}}),
      [{type: 'record', name: 'A', fields: []}]
    );
    test.done();
  },

  'analyze fixed': function(test) {
    test.deepEqual(
      analyze({type: 'fixed', name: 'A', size: 10}),
      [{type: 'fixed', name: 'A', size: 10}]
    );
    test.done();
  },

  'makeTypeMap': function(test) {
    var types = analyze({type: 'fixed', name: 'A', namespace: 'a.b', size: 10});
    test.deepEqual(
      analyzer.Avro.makeTypeMap(types),
      {'a.b.A': {type: 'fixed', name: 'A', namespace: 'a.b', size: 10}}
    );
    test.done();
  }
};

