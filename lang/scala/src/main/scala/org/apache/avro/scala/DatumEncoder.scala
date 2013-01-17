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

import org.apache.avro.scala.Text.implicitFormatableFromString
import org.apache.avro.scala.Text.implicitIndentableFromString
import org.apache.avro.Schema
import org.apache.avro.Schema

/**
 * Generic schema encoder. Should be inherited and customized.
 *
 * Assumes bytes and fixed are represented as Array[Byte].
 *
 * @returns a Scala template with the following string parameters:
 *     <li> encoder Encoder to encode with.
 *     <li> value Value being encoded.
 */
class ScalaDatumEncoder {
  def apply(schema: Schema): String = {
    return schema.getType match {
      case Schema.Type.NULL =>
        "%(encoder).writeNull()"
      case Schema.Type.BOOLEAN =>
        "%(encoder).writeBoolean(%(value))"
      case Schema.Type.INT =>
        "%(encoder).writeInt(%(value))"
      case Schema.Type.LONG =>
        "%(encoder).writeLong(%(value))"
      case Schema.Type.FLOAT =>
        "%(encoder).writeFloat(%(value))"
      case Schema.Type.DOUBLE =>
        "%(encoder).writeDouble(%(value))"
      case Schema.Type.STRING =>
        "%(encoder).writeString(%(value))"
      case Schema.Type.FIXED =>
        "%(encoder).writeFixed(%(value))"
      case Schema.Type.BYTES =>
        "%(encoder).writeBytes(%(value))"
      case Schema.Type.ENUM =>
        "%(encoder).writeEnum(0/* %(value) */) // TODO: Not Implemented"
      case Schema.Type.ARRAY => {
        val nestedEncoder = this.apply(schema.getElementType)
        """|%(encoder).writeArrayStart()
           |%(encoder).setItemCount(%(value).size)
           |for (arrayItem <- %(value)) {
           |  %(encoder).startItem()
           |%(nestedEncoder)
           |}
           |%(encoder).writeArrayEnd()"""
          .stripMargin
          .trim
          .xformat(
            'nestedEncoder -> nestedEncoder.xformat('value -> "arrayItem").indent(2))
      }
      case Schema.Type.MAP => {
        val nestedEncoder = this.apply(schema.getValueType)
        """|%(encoder).writeMapStart()
           |%(encoder).setItemCount(%(value).size)
           |for ((mapKey, mapValue) <- %(value)) {
           |  %(encoder).startItem()
           |  %(encoder).writeString(mapKey)
           |%(nestedEncoder)
           |}
           |%(encoder).writeMapEnd()"""
          .stripMargin
          .trim
          .xformat('nestedEncoder -> nestedEncoder.xformat('value -> "mapValue").indent(2))
      }
      case Schema.Type.UNION => {
        TypeMap.unionAsOption(schema) match {
          case None => "%(value).encode(%(encoder))"
          case Some((optionalSchema, nullIndex, optionIndex)) =>
            val nestedEncoder = this.apply(optionalSchema)
            """|%(value) match {
               |  case None => {
               |    %(encoder).writeIndex(%(nullIndex))
               |    %(encoder).writeNull()
               |  }
               |  case Some(optionalValue) => {
               |    %(encoder).writeIndex(%(optionIndex))
               |    %(nestedEncoder)
               |  }
               |}"""
            .stripMargin
            .trim
            .xformat(
                'nullIndex -> nullIndex,
                'optionIndex -> optionIndex,
                'nestedEncoder -> nestedEncoder.xformat('value -> "optionalValue"))
        }
      }
      case Schema.Type.RECORD =>
        "%(value).encode(%(encoder))"
    }
  }
}

/**
 * Datum encoder for immutable records.
 *
 * Bytes and fixed are declared as Seq[Byte], but implemented as Array[Byte].
 */
class RecordEncoder extends ScalaDatumEncoder {
  override def apply(schema: Schema): String = {
    return schema.getType match {
      case Schema.Type.FIXED =>
        "%(encoder).writeBytes(%(value).asInstanceOf[Array[Byte]])"
      case Schema.Type.BYTES =>
        "%(encoder).writeBytes(%(value).asInstanceOf[Array[Byte]])"
      case _ => return super.apply(schema)
    }
  }
}

/** Singleton immutable record encoder. */
object RecordEncoder extends RecordEncoder

/**
 * Datum encoder for mutable records.
 *
 * Bytes are declared as Buffer[Byte].
 * Fixed are declared as Array[Byte].
 */
class MutableRecordEncoder extends ScalaDatumEncoder {
  override def apply(schema: Schema): String = {
    return schema.getType match {
      case Schema.Type.BYTES =>
        "%(encoder).writeBytes(%(value).toArray)"
      case _ => return super.apply(schema)
    }
  }
}

/** Singleton mutable record encoder. */
object MutableRecordEncoder extends MutableRecordEncoder
