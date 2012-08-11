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

import org.apache.avro.scala.test.generated.scala.{MutableRecordWithAllTypes, RecordWithAllTypes}
import scala.collection.mutable

object Fixtures {
  def recordWithAllTypes(stringField: String = "string") = new RecordWithAllTypes(
    nullField  = null,
    booleanField = false,
    intField = 1,
    longField = 1L,
    floatField = 1.0f,
    doubleField = 1.0,
    stringField = stringField,
    bytesField = List(1, 2, 3),
    fixedField = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),  // TODO(taton) the size of the array should be validated
    intArrayField = List(1, 2, 3),
    intMapField = Map("x" -> 1, "y" -> 2),
    intArrayArrayField = List(List(1, 2), List(3, 4)),
    intMapMapField = Map("a" -> Map("x" -> 1), "b" -> Map("y" -> 2)))

  def mutableRecordWithAllTypes(stringField: String = "string") = new MutableRecordWithAllTypes(
    nullField  = null,
    booleanField = false,
    intField = 1,
    longField = 1L,
    floatField = 1.0f,
    doubleField = 1.0,
    stringField = stringField,
    bytesField = mutable.Buffer[Byte](1, 2, 3),
    fixedField = Array[Byte](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),  // TODO(taton) the size of the array should be validated
    intArrayField = mutable.ArrayBuffer[Int](1, 2, 3),
    intMapField = mutable.HashMap[String, Int]("x" -> 1, "y" -> 2),
    intArrayArrayField = mutable.Buffer[mutable.Buffer[Int]](mutable.Buffer(1, 2), mutable.Buffer(3, 4)),
    intMapMapField = mutable.HashMap[String, mutable.Map[String, Int]]("a" -> mutable.HashMap("x" -> 1), "b" -> mutable.HashMap("y" -> 2)))

}
