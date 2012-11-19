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
  analyzeEnum = analyzer.analyzeEnum,
  analyzeRecord = analyzer.analyzeRecord;

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
      test.deepEqual(namespaceAndName(analyzeEnum(s.alreadySplit)), ['a.b', 'MyEnum']);
      test.deepEqual(namespaceAndName(analyzeEnum(s.fullyQualifiedName)), ['a.b', 'MyEnum']);
      test.deepEqual(namespaceAndName(analyzeEnum(s.noNamespace)), [undefined, 'MyEnum']);
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
      test.deepEqual(namespaceAndName(analyzeRecord(s.alreadySplit)), ['a.b', 'MyRecord']);
      test.deepEqual(namespaceAndName(analyzeRecord(s.fullyQualifiedName)), ['a.b', 'MyRecord']);
      test.deepEqual(namespaceAndName(analyzeRecord(s.noNamespace)), [undefined, 'MyRecord']);
      test.done();
    }
  }
};

