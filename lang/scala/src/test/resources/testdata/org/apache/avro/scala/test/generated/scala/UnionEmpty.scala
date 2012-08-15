// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class UnionEmpty(
    val unionField : org.apache.avro.scala.test.generated.scala.UnionEmpty.ImmutableUnionFieldUnionType
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(unionField : org.apache.avro.scala.test.generated.scala.UnionEmpty.ImmutableUnionFieldUnionType = this.unionField): UnionEmpty =
    new UnionEmpty(
      unionField = unionField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return UnionEmpty.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => unionField.getData.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def toMutable: MutableUnionEmpty =
    new MutableUnionEmpty(
      this.unionField.toMutable
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionEmpty] ||
    other.isInstanceOf[MutableUnionEmpty]
}

class MutableUnionEmpty(
    var unionField : org.apache.avro.scala.test.generated.scala.UnionEmpty.MutableUnionFieldUnionType = null
) extends org.apache.avro.scala.MutableRecordBase[UnionEmpty] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return UnionEmpty.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => unionField.getData.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => this.unionField = org.apache.avro.scala.test.generated.scala.UnionEmpty.MutableUnionFieldUnionType(value)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionEmpty = {
    return new UnionEmpty(
      unionField = this.unionField.toImmutable
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.unionField = org.apache.avro.scala.test.generated.scala.UnionEmpty.UnionFieldUnionType.decode(decoder)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionEmpty] ||
    other.isInstanceOf[MutableUnionEmpty]

}

object UnionEmpty {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionEmpty",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "union_field",
          |    "type" : [ ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class UnionFieldUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  abstract class ImmutableUnionFieldUnionType extends UnionFieldUnionType {
    def toMutable: MutableUnionFieldUnionType
  }
  
  object UnionFieldUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): MutableUnionFieldUnionType = {
      decoder.readIndex() match {
        
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  
  
  abstract class MutableUnionFieldUnionType
      extends UnionFieldUnionType
      with org.apache.avro.scala.Decodable {
    def toImmutable: ImmutableUnionFieldUnionType
  }
  
  object MutableUnionFieldUnionType {
    def apply(data: Any): MutableUnionFieldUnionType = data match {
      
      case _ => throw new java.io.IOException("Bad union data: " + data)
    }
  }
}

}  // package org.apache.avro.scala.test.generated.scala
