// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class UnionEmpty(
    val unionField: org.apache.avro.scala.test.generated.scala.UnionEmpty.UnionFieldUnionType
) extends org.apache.avro.scala.ImmutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return UnionEmpty.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => unionField.getData.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionEmpty] ||
    other.isInstanceOf[MutableUnionEmpty]
}

class MutableUnionEmpty(
    var unionField: org.apache.avro.scala.test.generated.scala.UnionEmpty.MutableUnionFieldUnionType = null
) extends org.apache.avro.scala.MutableRecordBase[UnionEmpty] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return UnionEmpty.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => unionField.getData.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {println("%s PUT %d %s" format (getClass.getSimpleName, index, value))
    index match {
      case 0 => this.unionField = value.asInstanceOf[org.apache.avro.scala.test.generated.scala.UnionEmpty.MutableUnionFieldUnionType]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionEmpty = {
    return new UnionEmpty(
      unionField = this.unionField
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
  
  object UnionFieldUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): MutableUnionFieldUnionType = {
      decoder.readIndex() match {
        
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  
  
  abstract class MutableUnionFieldUnionType
      extends UnionFieldUnionType
      with org.apache.avro.scala.Decodable
}

}  // package org.apache.avro.scala.test.generated.scala