// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class RecordWithEnum(
    var enumField : org.apache.avro.scala.test.generated.ColorEnum
) extends org.apache.avro.scala.Record {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = RecordWithEnum.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(enumField /* TODO Not Implemented */).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.enumField = org.apache.avro.scala.test.generated.ColorEnum.valueOf(value.toString)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeEnum(0/* this.enumField */) // TODO: Not Implemented
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.enumField = org.apache.avro.scala.test.generated.ColorEnum.values()(decoder.readEnum())
  }
}

object RecordWithEnum extends org.apache.avro.scala.RecordType[RecordWithEnum] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordWithEnum",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "enum_field",
          |    "type" : {
          |      "type" : "enum",
          |      "name" : "ColorEnum",
          |      "symbols" : [ "Red", "Green", "Blue" ]
          |    }
          |  } ]
          |}
      """
      .stripMargin)
}

}