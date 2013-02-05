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

import java.io.ByteArrayInputStream

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

import org.apache.avro.scala.test.generated.RecordWithDefaults

/**
 * Tests the generated code.
 */
@RunWith(classOf[JUnitRunner])
class TestDefaults
  extends FunSuite {

  test("default string value") {
    assert(RecordWithDefaults().stringField === "default string")
  }

  test("default empty map value") {
    assert(RecordWithDefaults().mapFieldEmptyDefault === Map())
  }

  test("default non-empty map value") {
    val defaultValue = Map("a" -> "aa", "b\"b" -> "bb\"bb")
    assert(RecordWithDefaults().mapFieldNonemptyDefault === defaultValue)
  }

  /*test("default nested map value") {
    val defaultValue = Map("a" -> Map("aa" -> "aaa"))
    assert((new RecordWithDefaults).mapFieldNestedDefault === defaultValue)
    assert((new MutableRecordWithDefaults).mapFieldNestedDefault === defaultValue)
  }*/
}
