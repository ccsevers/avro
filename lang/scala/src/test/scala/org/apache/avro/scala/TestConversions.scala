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

import java.util
import org.apache.avro.util.Utf8

/**
 * Tests the generated code.
 */
@RunWith(classOf[JUnitRunner])
class TestConversions
  extends FunSuite {

  test("scalaToJava") {
    val scalaMap = Map[String, Map[String, String]]("a" -> Map("aa" -> "aaa"))
    val javaMap = Conversions.scalaToJava(scalaMap).asInstanceOf[java.util.Map[String, java.util.Map[String, String]]]
    assert(javaMap.get("a").get("aa") === "aaa")
    javaMap.remove("a")
    assert(javaMap.get("a") === null)
  }

  test("javaToScala") {
    val javaMap: java.util.HashMap[String, java.util.Map[String, String]] =
      new java.util.HashMap[String, java.util.Map[String, String]]()
    val javaSubmap = new java.util.HashMap[String, String]()
    javaSubmap.put("aa", "aaa")
    javaMap.put("a", javaSubmap)
    val scalaMap = Conversions.javaToScala(javaMap).asInstanceOf[collection.mutable.Map[String, collection.mutable.Map[String, String]]]
    assert(scalaMap("a")("aa") === "aaa")
  }

  test("Utf8 key map to Scala map yields map with String keys") {
    val javaMap: java.util.Map[CharSequence, String] = new java.util.HashMap()
    javaMap.put(new Utf8("a"), "aa")
    val scalaMap = Conversions.javaToScala(javaMap).asInstanceOf[collection.mutable.Map[String, String]]
    assert(scalaMap("a") === "aa")
  }
}
