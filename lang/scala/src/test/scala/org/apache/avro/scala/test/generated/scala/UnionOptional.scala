// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class UnionOptional(
    var optionalField : Option[String]
) extends org.apache.avro.scala.Record {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = UnionOptional.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(optionalField.getOrElse(null)).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.optionalField = Option(value).map(value => value.toString)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.optionalField match {
      case None => {
        encoder.writeIndex(0)
        encoder.writeNull()
      }
      case Some(optionalValue) => {
        encoder.writeIndex(1)
        encoder.writeString(optionalValue)
      }
    }
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.optionalField = decoder.readIndex() match {
      case 0 => { decoder.readNull(); None }
      case 1 => Some(decoder.readString())
    }
  }
}

object UnionOptional extends org.apache.avro.scala.RecordType[UnionOptional] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionOptional",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "optional_field",
          |    "type" : [ "null", "string" ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class OptionalFieldUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  object OptionalFieldUnionType {
    def apply(data: Any): OptionalFieldUnionType = data match {
      case null => OptionalFieldUnionNull(null)
      case data: CharSequence => OptionalFieldUnionString(data.toString)
      case _ => throw new java.io.IOException(s"Unexpected union data of type ${data.getClass.getName}: ${data}")
    }
  
    def decode(decoder: org.apache.avro.io.Decoder): OptionalFieldUnionType = {
      decoder.readIndex() match {
        case 0 => return OptionalFieldUnionNull(data = {decoder.readNull(); null})
        case 1 => return OptionalFieldUnionString(data = decoder.readString())
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class OptionalFieldUnionNull(data: Null) extends OptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class OptionalFieldUnionString(data: String) extends OptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeString(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
}

}