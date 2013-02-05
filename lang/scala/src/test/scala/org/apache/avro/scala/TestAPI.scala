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

/**
 * Tests the generated code.
 */
@RunWith(classOf[JUnitRunner])
class TestAPI extends FunSuite {
  import org.apache.avro.scala.test.generated._

  test("empty record") {
    EmptyRecord()
  }

  test("record with all types") {
    RecordWithAllTypes(
      nullField  = null,
      booleanField = false,
      intField = 1,
      longField = 1L,
      floatField = 1.0f,
      doubleField = 1.0,
      stringField = "string",
      bytesField = List(1, 2, 3),
      fixedField = Seq.range(0, 16), // TODO(taton) the size of the array should be validated
      intArrayField = List(1, 2, 3),
      intMapField = Map("x" -> 1, "y" -> 2),
      intArrayArrayField = List(List(1, 2), List(3, 4)),
      intMapMapField = Map("a" -> Map("x" -> 1), "b" -> Map("y" -> 2))
    )
  }

  // test("nested record") {
  //   Container(contained = new Contained(data = 1))
  // }

  test("union as optional field") {
    UnionOptional(optionalField = None)
    UnionOptional(optionalField = Some("10"))
  }

  test("union with many cases") {
    UnionMany(unionField = UnionMany.UnionFieldUnionInt(1))
  }

  test("schema on record object") {
    val obj: RecordType[EmptyRecord] = EmptyRecord
    assert(obj.schema === EmptyRecord.schema)
  }

  test("fromJson on record object") {
    val record1 = new RecordWithString("a")
    val record2 = RecordWithString.fromJson(record1.toJson)
    assert(record1 === record2)
  }

  test("fromJsonArray on type object") {
    val jsonArray = """[{"string_field":"a"},{"string_field":"b"}]"""
    assert(RecordWithString.fromJsonArray(jsonArray) === List(new RecordWithString("a"), new RecordWithString("b")))
  }

  test("toJsonArray on type object") {
    val records = List(new RecordWithString("a"), new RecordWithString("b"))
    assert(RecordWithString.toJsonArray(records) === """[{"string_field":"a"},{"string_field":"b"}]""")
  }
}
