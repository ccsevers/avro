// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class UnionSingleton(
    val unionField : org.apache.avro.scala.test.generated.scala.UnionSingleton.UnionFieldUnionType
) extends org.apache.avro.scala.ImmutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return UnionSingleton.schema
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

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionSingleton] ||
    other.isInstanceOf[MutableUnionSingleton]
}

class MutableUnionSingleton(
    var unionField : org.apache.avro.scala.test.generated.scala.UnionSingleton.MutableUnionFieldUnionType = null
) extends org.apache.avro.scala.MutableRecordBase[UnionSingleton] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return UnionSingleton.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => unionField.getData.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => this.unionField = value.asInstanceOf[org.apache.avro.scala.test.generated.scala.UnionSingleton.MutableUnionFieldUnionType]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionSingleton = {
    return new UnionSingleton(
      unionField = this.unionField
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.unionField = org.apache.avro.scala.test.generated.scala.UnionSingleton.UnionFieldUnionType.decode(decoder)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionSingleton] ||
    other.isInstanceOf[MutableUnionSingleton]

}

object UnionSingleton {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionSingleton",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "union_field",
          |    "type" : [ "int" ]
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
        case 0 => return MutableUnionFieldUnionInt(data = decoder.readInt())
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class UnionFieldUnionInt(data: Int) extends UnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeInt(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  abstract class MutableUnionFieldUnionType
      extends UnionFieldUnionType
      with org.apache.avro.scala.Decodable
  
  case class MutableUnionFieldUnionInt(var data: Int) extends MutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeInt(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readInt()
    }
  }
}

}  // package org.apache.avro.scala.test.generated.scala
