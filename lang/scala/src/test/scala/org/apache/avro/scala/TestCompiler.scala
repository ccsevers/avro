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

import java.io.File
import java.io.FileInputStream
import java.io.FilenameFilter
import java.io.FileOutputStream

import scala.collection.JavaConverters._

import org.apache.avro.Schema
import org.apache.avro.scala.Text.implicitCamelCaseableFromString
import org.apache.commons.io.IOUtils
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestScalaCompiler extends FunSuite {
  test("compile schema") {
    val baseDir = new File("src/test")
    require(baseDir.exists, baseDir)
    val outDir = new File(baseDir, "scala/org/apache/avro/scala/test/generated/scala")
    val inDir = new File(baseDir, "resources/testdata")
    CompilerApp.compileAndWrite(outDir, inDir, CompilerApp.SchemaInput)
  }

  test("compile protocol") {
    val baseDir = new File("src/test")
    require(baseDir.exists, baseDir)
    val outDir = new File(baseDir, "scala/org/apache/avro/scala/test/generated/scala")
    val inDir = new File(baseDir, "resources/testdata")
    CompilerApp.compileAndWrite(outDir, inDir, CompilerApp.ProtocolInput)
  }
}
