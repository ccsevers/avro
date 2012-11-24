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

  var analyzer = require('./analyzer.js'),
    emitter = require('./emitter.js');

  // * Generates code for `schema`.
  function compile(schema, enclosingNamespace) {
    var types = analyzer.analyze(schema, enclosingNamespace);
    return types.map(function(t) {
      return emitter.emit(t);
    }).join('\n\n/////////////////////////////////////////////////\n\n') +
      '\n' + emitter.emitTypeMap(analyzer.makeTypeMap(types));
  }

  function compileIndividually(schema, enclosingNamespace) {
    var types = analyzer.analyze(schema, enclosingNamespace);
    var parts = {};
    types.forEach(function(t) {
      parts[t.name] = emitter.emit(t) + '\n';
    });
    parts['_typemap'] = emitter.emitTypeMap(analyzer.makeTypeMap(types));
    return parts;
  }

  if (typeof exports !== 'undefined') {
    exports.compile = compile;
    exports.compileIndividually = compileIndividually;
  }
}).call(this);
