/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.avro.scala

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.apache.avro.scala.Text.implicitCamelCaseableFromString

@RunWith(classOf[JUnitRunner])
class TestText
  extends FunSuite {

  test("toCamelCase") {
    assert("fooBar".toCamelCase === "fooBar")
    assert("FooBar".toCamelCase === "FooBar")

    assert("foo_bar".toCamelCase === "fooBar")
    assert("foo_bar".toUpperCamelCase === "FooBar")
    assert("fooBar".toUpperCamelCase === "FooBar")
    assert("Foo_bar".toLowerCamelCase === "fooBar")
    assert("FooBar".toLowerCamelCase === "fooBar")
    assert("foo_".toCamelCase === "foo")
    assert("_foo_".toCamelCase === "Foo")
  }
}
