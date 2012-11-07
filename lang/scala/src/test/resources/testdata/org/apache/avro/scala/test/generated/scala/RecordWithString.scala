// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class RecordWithString(
    val stringField : String
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(stringField : String = this.stringField): RecordWithString =
    new RecordWithString(
      stringField = stringField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithString.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.stringField)
  }

  def toMutable: MutableRecordWithString =
    new MutableRecordWithString(
      this.stringField
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithString] ||
    other.isInstanceOf[MutableRecordWithString]
}

class MutableRecordWithString(
    var stringField : String = null
) extends org.apache.avro.scala.MutableRecordBase[RecordWithString] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithString.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.stringField = value.toString
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithString = {
    return new RecordWithString(
      stringField = this.stringField
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.stringField)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.stringField = decoder.readString()
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithString] ||
    other.isInstanceOf[MutableRecordWithString]

}

object RecordWithString extends org.apache.avro.scala.RecordType {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordWithString",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "string_field",
          |    "type" : "string"
          |  } ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala
