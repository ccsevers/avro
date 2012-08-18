// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class RecordWithDefaults(
    val stringField : String = "default string",
    val mapFieldEmptyDefault : Map[String, Int] = Map[String, Int](),
    val mapFieldNonemptyDefault : Map[String, String] = Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb")
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(stringField : String = this.stringField, mapFieldEmptyDefault : Map[String, Int] = this.mapFieldEmptyDefault, mapFieldNonemptyDefault : Map[String, String] = this.mapFieldNonemptyDefault): RecordWithDefaults =
    new RecordWithDefaults(
      stringField = stringField,
      mapFieldEmptyDefault = mapFieldEmptyDefault,
      mapFieldNonemptyDefault = mapFieldNonemptyDefault
    )

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithDefaults.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldEmptyDefault).asInstanceOf[AnyRef]
      case 2 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldNonemptyDefault).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.stringField)
    encoder.writeMapStart()
    encoder.setItemCount(this.mapFieldEmptyDefault.size)
    for ((mapKey, mapValue) <- this.mapFieldEmptyDefault) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeInt(mapValue)
    }
    encoder.writeMapEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.mapFieldNonemptyDefault.size)
    for ((mapKey, mapValue) <- this.mapFieldNonemptyDefault) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeString(mapValue)
    }
    encoder.writeMapEnd()
  }

  def toMutable: MutableRecordWithDefaults =
    new MutableRecordWithDefaults(
      this.stringField,
      scala.collection.mutable.Map[String, Int]((this.mapFieldEmptyDefault).toSeq: _*),
      scala.collection.mutable.Map[String, String]((this.mapFieldNonemptyDefault).toSeq: _*)
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithDefaults] ||
    other.isInstanceOf[MutableRecordWithDefaults]
}

class MutableRecordWithDefaults(
    var stringField : String = "default string",
    var mapFieldEmptyDefault : scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String, Int](),
    var mapFieldNonemptyDefault : scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb")
) extends org.apache.avro.scala.MutableRecordBase[RecordWithDefaults] {

  def this() = this("default string", scala.collection.mutable.Map[String, Int](), scala.collection.mutable.Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb"))

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithDefaults.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldEmptyDefault).asInstanceOf[AnyRef]
      case 2 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldNonemptyDefault).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.stringField = value.toString
      case 1 => this.mapFieldEmptyDefault = value.asInstanceOf[scala.collection.mutable.Map[String, Int]]
      case 2 => this.mapFieldNonemptyDefault = value.asInstanceOf[scala.collection.mutable.Map[String, String]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithDefaults = {
    return new RecordWithDefaults(
      stringField = this.stringField,
      mapFieldEmptyDefault = this.mapFieldEmptyDefault.toMap,
      mapFieldNonemptyDefault = this.mapFieldNonemptyDefault.toMap
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.stringField)
    encoder.writeMapStart()
    encoder.setItemCount(this.mapFieldEmptyDefault.size)
    for ((mapKey, mapValue) <- this.mapFieldEmptyDefault) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeInt(mapValue)
    }
    encoder.writeMapEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.mapFieldNonemptyDefault.size)
    for ((mapKey, mapValue) <- this.mapFieldNonemptyDefault) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeString(mapValue)
    }
    encoder.writeMapEnd()
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.stringField = decoder.readString()
    this.mapFieldEmptyDefault = {
      val map = scala.collection.mutable.Map[String, Int]()
      var blockSize: Long = decoder.readMapStart()
      while (blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val key: String = decoder.readString()
          val value = (
            decoder.readInt())
          map += (key -> value)
        }
        blockSize = decoder.mapNext()
      }
    map
    }
    this.mapFieldNonemptyDefault = {
      val map = scala.collection.mutable.Map[String, String]()
      var blockSize: Long = decoder.readMapStart()
      while (blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val key: String = decoder.readString()
          val value = (
            decoder.readString())
          map += (key -> value)
        }
        blockSize = decoder.mapNext()
      }
    map
    }
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
          |  }, {
          |    "name" : "map_field_empty_default",
          |    "type" : {
          |      "type" : "map",
          |      "values" : "int"
          |    },
          |    "default" : {
          |    }
          |  }, {
          |    "name" : "map_field_nonempty_default",
          |    "type" : {
          |      "type" : "map",
          |      "values" : "string"
          |    },
          |    "default" : {
          |      "a" : "aa",
          |      "b\"b" : "bb\"bb"
          |    }
          |  } ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala
