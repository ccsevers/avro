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
class TestScalaCompiler
  extends FunSuite {

  final val Overwrite = true

  /** Reads a file content into a text string. */
  def readFile(file: File): String = {
    val istream = new FileInputStream(file)
    require(istream != null)
    return IOUtils.readLines(istream).asScala.mkString("\n")
  }

  /** Writes a text file. */
  def writeFile(file: File, content: String): Unit = {
    require(file.getParentFile.exists || file.getParentFile.mkdirs())
    val ostream = new FileOutputStream(file)
    ostream.write(content.getBytes)
    ostream.close()
  }

  test("compile") {
    val dir = new File("src/test/resources/testdata")
    require(dir.exists, dir)
    object filter extends FilenameFilter {
      override def accept(dir: File, name: String): Boolean = { return name.endsWith(".avsc") }
    }
    for (jsonSourceFile <- dir.listFiles(filter)) {
      val name = jsonSourceFile.getName.stripSuffix(".avsc")
      val genDir = new File(jsonSourceFile.getParent, "org/apache/avro/scala/test/generated/scala")
      val scalaFile = new File(genDir, "%s.scala".format(name.toUpperCamelCase))
      println("%s -> %s".format(jsonSourceFile, scalaFile))
      val schemaJsonSource = readFile(jsonSourceFile)
      val schema = Schema.parse(schemaJsonSource)
      schema.getType match {
        case Schema.Type.UNION => {
          val source = new StringBuilder()
          for (schemaType <- schema.getTypes.asScala) {
            val compiler = new Compiler(schemaType)
            val scalaGeneratedSource = compiler.compile()
            source.append("\n\n").append(scalaGeneratedSource)
          }
          if (!Overwrite && scalaFile.exists()) {
            assert(readFile(scalaFile) == source.toString)
          } else {
            writeFile(scalaFile, source.toString)
          }
        }
        case Schema.Type.RECORD | Schema.Type.ENUM => {
          val compiler = new Compiler(schema)
          val scalaGeneratedSource = compiler.compile()
          if (!Overwrite && scalaFile.exists()) {
            assert(readFile(scalaFile) == scalaGeneratedSource.toString)
          } else {
            writeFile(scalaFile, scalaGeneratedSource)
          }
        }
        case _ => println("Unhandled top-level schema: " + schema)
      }
    }
  }
}
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
class TestScalaCompiler
  extends FunSuite {

  /** Reads a file content into a text string. */
  def readFile(file: File): String = {
    val istream = new FileInputStream(file)
    require(istream != null)
    return IOUtils.readLines(istream).asScala.mkString("\n")
  }

  /** Writes a text file. */
  def writeFile(file: File, content: String): Unit = {
    require(file.getParentFile.exists || file.getParentFile.mkdirs())
    val ostream = new FileOutputStream(file)
    ostream.write(content.getBytes)
    ostream.close()
  }

  test("compile") {
    val dir = new File("src/test/resources/testdata")
    require(dir.exists, dir)
    object filter extends FilenameFilter {
      override def accept(dir: File, name: String): Boolean = { return name.endsWith(".avsc") }
    }
    for (jsonSourceFile <- dir.listFiles(filter)) {
      val name = jsonSourceFile.getName.stripSuffix(".avsc")
      val genDir = new File(jsonSourceFile.getParent, "org/apache/avro/scala/test/generated/scala")
      val scalaFile = new File(genDir, "%s.scala".format(name.toUpperCamelCase))
      println("%s -> %s".format(jsonSourceFile, scalaFile))
      val schemaJsonSource = readFile(jsonSourceFile)
      val schema = Schema.parse(schemaJsonSource)
      schema.getType match {
        case Schema.Type.UNION => {
          val source = new StringBuilder()
          for (schemaType <- schema.getTypes.asScala) {
            val compiler = new Compiler(schemaType)
            val scalaGeneratedSource = compiler.compile()
            source.append("\n\n").append(scalaGeneratedSource)
          }
          if (scalaFile.exists()) {
            assert(readFile(scalaFile) == source.toString)
          } else {
            writeFile(scalaFile, source.toString)
          }
        }
        case Schema.Type.RECORD | Schema.Type.ENUM => {
          val compiler = new Compiler(schema)
          val scalaGeneratedSource = compiler.compile()
          if (scalaFile.exists()) {
            assert(readFile(scalaFile) == scalaGeneratedSource.toString)
          } else {
            writeFile(scalaFile, scalaGeneratedSource)
          }
        }
        case _ => println("Unhandled top-level schema: " + schema)
      }
    }
  }
}