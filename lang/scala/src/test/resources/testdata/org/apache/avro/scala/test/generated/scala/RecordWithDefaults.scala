// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class RecordWithDefaults(
    val stringField : String = "default string"
) extends org.apache.avro.scala.ImmutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithDefaults.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => stringField
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.stringField)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithDefaults] ||
    other.isInstanceOf[MutableRecordWithDefaults]
}

class MutableRecordWithDefaults(
    var stringField : String = "default string"
) extends org.apache.avro.scala.MutableRecordBase[RecordWithDefaults] {

  def this() = this("default string")

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithDefaults.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => stringField
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => this.stringField = value.toString
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithDefaults = {
    return new RecordWithDefaults(
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
    other.isInstanceOf[RecordWithDefaults] ||
    other.isInstanceOf[MutableRecordWithDefaults]

}

object RecordWithDefaults {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordWithDefaults",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "string_field",
          |    "type" : "string",
          |    "default" : "default string"
          |  } ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala