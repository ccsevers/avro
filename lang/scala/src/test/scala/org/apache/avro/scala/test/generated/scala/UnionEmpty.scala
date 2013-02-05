// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class UnionEmpty(
    var unionField : org.apache.avro.scala.test.generated.UnionEmpty.UnionFieldUnionType
) extends org.apache.avro.scala.Record {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = UnionEmpty.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(unionField.getData).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.unionField = org.apache.avro.scala.test.generated.UnionEmpty.UnionFieldUnionType(value)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.unionField = org.apache.avro.scala.test.generated.UnionEmpty.UnionFieldUnionType.decode(decoder)
  }
}

object UnionEmpty extends org.apache.avro.scala.RecordType[UnionEmpty] {
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
    def apply(data: Any): UnionFieldUnionType = data match {
      
      case _ => throw new java.io.IOException(s"Unexpected union data of type ${data.getClass.getName}: ${data}")
    }
  
    def decode(decoder: org.apache.avro.io.Decoder): UnionFieldUnionType = {
      decoder.readIndex() match {
        
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
}

}