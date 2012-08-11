// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class RecordWithString(
    val stringField: String
) extends org.apache.avro.scala.ImmutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithString.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => stringField
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.stringField)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithString] ||
    other.isInstanceOf[MutableRecordWithString]
}

class MutableRecordWithString(
    var stringField: String = null
) extends org.apache.avro.scala.MutableRecordBase[RecordWithString] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithString.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => stringField
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {println("%s PUT %d %s" format (getClass.getSimpleName, index, value))
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

object RecordWithString {
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