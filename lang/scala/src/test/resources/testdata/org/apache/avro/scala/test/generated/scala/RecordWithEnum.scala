// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class RecordWithEnum(
    val enumField : org.apache.avro.scala.test.generated.scala.ColorEnum.Value
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(enumField : org.apache.avro.scala.test.generated.scala.ColorEnum.Value = this.enumField): RecordWithEnum =
    new RecordWithEnum(
      enumField = enumField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithEnum.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(enumField /* TODO Not Implemented */).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeEnum(0/* this.enumField */) // TODO: Not Implemented
  }

  def toMutable: MutableRecordWithEnum =
    new MutableRecordWithEnum(
      this.enumField
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithEnum] ||
    other.isInstanceOf[MutableRecordWithEnum]
}

class MutableRecordWithEnum(
    var enumField : org.apache.avro.scala.test.generated.scala.ColorEnum.Value = null
) extends org.apache.avro.scala.MutableRecordBase[RecordWithEnum] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithEnum.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(enumField /* TODO Not Implemented */).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.enumField = org.apache.avro.scala.test.generated.scala.ColorEnum.withName(value.asInstanceOf[org.apache.avro.generic.GenericData.EnumSymbol].toString)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithEnum = {
    return new RecordWithEnum(
      enumField = this.enumField
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeEnum(0/* this.enumField */) // TODO: Not Implemented
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.enumField = org.apache.avro.scala.test.generated.scala.ColorEnum(decoder.readEnum())
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithEnum] ||
    other.isInstanceOf[MutableRecordWithEnum]

}

object RecordWithEnum {
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

}  // package org.apache.avro.scala.test.generated.scala
