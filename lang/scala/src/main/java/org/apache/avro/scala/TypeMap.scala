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

import scala.collection.JavaConverters.collectionAsScalaIterableConverter

import org.apache.avro.Schema
import org.apache.avro.scala.Text.implicitCamelCaseableFromString

/** Mapping between Avro types and Scala types. */
class TypeMap {
  /**
   * Mapping between Avro types and Scala types.
   *
   * @param schema Avro type to map.
   * @param mutable Whether to map to mutable or immutable types.
   * @param interface Whether to map to interface or concrete types.
   * @param field Record field this type is for, if applicable.
   */
  def apply(
    schema: Schema,
    mutable: MutableFlag,
    concrete: ConcreteFlag,
    field: Option[(Schema, Schema.Field)] = None): String = {

    schema.getType match {
      case Schema.Type.NULL => return "Null"
      case Schema.Type.BOOLEAN => return "Boolean"
      case Schema.Type.INT => return "Int"
      case Schema.Type.LONG => return "Long"
      case Schema.Type.FLOAT => return "Float"
      case Schema.Type.DOUBLE => return "Double"
      case Schema.Type.ENUM => return schema.getFullName
      case Schema.Type.STRING => return "String"
      case Schema.Type.FIXED => {
        return (mutable, concrete) match {
          case (Mutable, _) => "Array[Byte]"
          case (Immutable, _) => "Seq[Byte]" // Underlying implementation must be Array[Byte]
        }
      }
      case Schema.Type.BYTES => {
        return (mutable, concrete) match {
          case (Mutable, _) => "scala.collection.mutable.Buffer[Byte]"
          case (Immutable, Abstract) => "Seq[Byte]" // Underlying implementation as Array
          case (Immutable, Concrete) => "Array[Byte]"
        }
      }
      case Schema.Type.ARRAY => {
        val elementType = this.apply(schema.getElementType, mutable, concrete)
        return (mutable, concrete) match {
          case (Mutable, Abstract) =>
            "scala.collection.mutable.Buffer[%s]".format(elementType)
          case (Mutable, Concrete) =>
            "scala.collection.mutable.ArrayBuffer[%s]".format(elementType)
          case (Immutable, Abstract) =>
            "Seq[%s]".format(elementType)
          case (Immutable, Concrete) =>
            "List[%s]".format(elementType)
        }
      }
      case Schema.Type.MAP => {
        val valueType = this.apply(schema.getValueType, mutable, concrete)
        return (mutable, concrete) match {
          case (Mutable, Abstract) =>
            "scala.collection.mutable.Map[String, %s]".format(valueType)
          case (Mutable, Concrete) =>
            "scala.collection.mutable.HashMap[String, %s]".format(valueType)
          case (Immutable, Abstract) =>
            "Map[String, %s]".format(valueType)
          case (Immutable, Concrete) =>
            "HashMap[String, %s]".format(valueType)
        }
      }
      case Schema.Type.RECORD => {
        return mutable match {
          case Mutable =>
            "%s.scala.Mutable%s".format(schema.getNamespace, schema.getName.toUpperCamelCase)
          case Immutable =>
            "%s.scala.%s".format(schema.getNamespace, schema.getName.toUpperCamelCase)
        }
      }
      case Schema.Type.UNION => {
        unionAsOption(schema) match {
          case None =>
            field match {
              case None =>
                throw new RuntimeException("Unable to generate unnamed union types: " + schema)
              case Some((recordSchema, field)) =>
                return "%s.scala.%s.%s%sUnionType".format(
                  recordSchema.getNamespace,
                  recordSchema.getName.toUpperCamelCase,
                  if (mutable == Mutable) "Mutable" else "",
                  field.name.toUpperCamelCase)
            }

          case Some((optionalSchema, _, _)) =>
            return "Option[%s]".format(this.apply(optionalSchema, mutable, concrete))
        }
      }
    }
    throw new RuntimeException("Unknown schema type: " + schema)
  }

  /**
   * Identify union types that are compiled to Option[X].
   *
   * @param schema Union schema type to test
   * @return If the union compiles to Option[X], this returns a tuple with:
   *      <li> the schema of the optional field,
   *           ie. the schema describing X in Option[X]
   *      <li> the index for the null case
   *      <li> the index for the non-null case
   *      Otherwise, this returns None to indicate the union compiles to custom case classes.
   */
  def unionAsOption(schema: Schema): Option[(Schema, Int, Int)] = {
    require(schema.getType == Schema.Type.UNION)
    val types: Iterable[Schema] = schema.getTypes.asScala
    if ((types.size == 2) && types.exists { sc: Schema => (sc.getType == Schema.Type.NULL) }) {
      val (nullIndex, optionIndex) = {
        if (schema.getTypes.get(0).getType == Schema.Type.NULL) (0, 1) else (1, 0)
      }
      return Some((types.filter(_.getType != Schema.Type.NULL).head, nullIndex, optionIndex))
    }
    return None
  }

}

/** Default instance. */
object TypeMap extends TypeMap
