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

  // * Normalizes a type's fullname to a namespace (possibly undefined)
  // * and a name.
  function _qualifiedNamespaceAndName(namespace, name) {
    if (name.indexOf('.') !== -1) {
      var lastDot = name.lastIndexOf('.');
      namespace = name.substring(0, lastDot);
      name = name.substring(lastDot + 1);
    }
    return {namespace: namespace, name: name};
  }

  function analyzeEnum(schema) {
    var nn = _qualifiedNamespaceAndName(schema.namespace, schema.name);
    if (nn.namespace) {
      schema.namespace = nn.namespace;
    } else {
      delete schema.namespace;
    }
    schema.name = nn.name;
    return schema;
  }

  if (typeof exports !== 'undefined') {
    exports.analyzeEnum = analyzeEnum;
  }
}).call(this);
