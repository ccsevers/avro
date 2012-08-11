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

import scala.collection.JavaConverters._

import org.apache.avro.scala.Text.implicitCamelCaseableFromString
import org.apache.avro.scala.Text.implicitFormatableFromString
import org.apache.avro.scala.Text.implicitIndentableFromString
import org.apache.avro.Schema

/**
 * Generates code template for a decoder, given a schema.
 *
 * Generated code templates parameters are:
 *  <li> decoder Decoder to decode from
 */
class DatumDecoder {
  /**
   * @param schema Schema to generate the decoder of.
   * @param field Record field this decoder is for, if applicable.
   */
  def apply(
    schema: Schema,
    field: Option[(Schema, Schema.Field)] = None): String = {

    return schema.getType match {
      case Schema.Type.NULL => 
        "{%(decoder).readNull(); null}"
      case Schema.Type.BOOLEAN =>
        "%(decoder).readBoolean()"
      case Schema.Type.INT =>
        "%(decoder).readInt()"
      case Schema.Type.LONG =>
        "%(decoder).readLong()"
      case Schema.Type.FLOAT =>
        "%(decoder).readFloat()"
      case Schema.Type.DOUBLE =>
        "%(decoder).readDouble()"
      case Schema.Type.STRING =>
        "%(decoder).readString()"

      case Schema.Type.FIXED =>
        "{ val bytes = new Array[Byte](%(nbytes)); %(decoder).readFixed(bytes); bytes }"
          .xformat('nbytes -> schema.getFixedSize)
      case Schema.Type.BYTES =>
        // TODO(taton) this is far from optimal
        "%(decoder).readBytes(null).array.toBuffer"

      case Schema.Type.ENUM =>
        val enumFQName = "%s.%s".format(schema.getNamespace, schema.getName.toCamelCase)
        "%(enumName)(%(decoder).readEnum())".xformat('enumName -> enumFQName)

      case Schema.Type.ARRAY => {
        val cast = schema.getElementType.getType match {
          case Schema.Type.ARRAY
            | Schema.Type.MAP => ".asInstanceOf[%s]".format(TypeMap(schema, Mutable, Abstract))
          case _ => ""
        }
        val nestedDecoder = this.apply(schema.getElementType)
        """|{
           |  val array = %(array)
           |  var blockSize: Long = %(decoder).readArrayStart()
           |  while(blockSize != 0L) {
           |    for (_ <- 0L until blockSize) {
           |      val arrayItem = (
           |%(nestedDecoder))
           |      array.append(arrayItem)
           |    }
           |    blockSize = %(decoder).arrayNext()
           |  }
           |  array%(cast)
           |}"""
          .stripMargin
          .trim
          .xformat(
            'nestedDecoder -> nestedDecoder.indent(10),
            'array -> "%s()".format(TypeMap(schema, Mutable, Concrete)),
            'cast -> cast)
      }
      case Schema.Type.MAP => {
        val cast = schema.getValueType.getType match {
          case Schema.Type.ARRAY
            | Schema.Type.MAP => ".asInstanceOf[%s]".format(TypeMap(schema, Mutable, Abstract))
          case _ => ""
        }
        val nestedDecoder = this.apply(schema.getValueType)
        """|{
           |  val map = %(map)
           |  var blockSize: Long = %(decoder).readMapStart()
           |  while (blockSize != 0L) {
           |    for (_ <- 0L until blockSize) {
           |      val key: String = %(decoder).readString()
           |      val value = (
           |%(nestedDecoder))
           |      map += (key -> value)
           |    }
           |    blockSize = %(decoder).mapNext()
           |  }
           |map%(cast)
           |}"""
          .stripMargin
          .trim
          .xformat(
            'nestedDecoder -> nestedDecoder.xformat('value -> "mapValue").indent(8),
            'map -> "%s()".format(TypeMap(schema, Mutable, Concrete)),
            'cast -> cast)
      }
      case Schema.Type.RECORD => {
        return "{ val record = new %(mutableClass)(); record.decode(%(decoder)); record }"
          .xformat('mutableClass -> TypeMap(schema, Mutable, Concrete))
      }
      case Schema.Type.UNION => {
        TypeMap.unionAsOption(schema) match {
          case None =>
            field match {
              case None =>
                throw new RuntimeException("Unable to generate decoder for unnamed union: " + schema)
              case Some((recordSchema, recordField)) =>
                "%(unionTypeName).decode(%(decoder))".xformat('unionTypeName ->
                  TypeMap(schema,
                    mutable = Immutable,
                    concrete = Concrete,
                    field = field))
            }
          case Some((optionalSchema, nullIndex, optionIndex)) => {
            val nestedDecoder = this.apply(optionalSchema)
            """|%(decoder).readIndex() match {
               |  case %(nullIndex) => { %(decoder).readNull(); None }
               |  case %(optionIndex) => Some(%(nestedDecoder))
               |}"""
              .stripMargin
              .trim
              .xformat(
                  'nullIndex -> nullIndex,
                  'optionIndex -> optionIndex,
                  'nestedDecoder -> nestedDecoder)
          }
        }
      }
    }
  }
}

/** Singleton decoder generator */
object DatumDecoder extends DatumDecoder
