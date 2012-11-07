// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class RecordWithDefaults(
    val stringField : String = "default string",
    val mapFieldEmptyDefault : Map[String, Int] = Map[String, Int](),
    val mapFieldNonemptyDefault : Map[String, String] = Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb"),
    val arrayFieldEmptyDefault : Seq[String] = List[String]()
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(stringField : String = this.stringField, mapFieldEmptyDefault : Map[String, Int] = this.mapFieldEmptyDefault, mapFieldNonemptyDefault : Map[String, String] = this.mapFieldNonemptyDefault, arrayFieldEmptyDefault : Seq[String] = this.arrayFieldEmptyDefault): RecordWithDefaults =
    new RecordWithDefaults(
      stringField = stringField,
      mapFieldEmptyDefault = mapFieldEmptyDefault,
      mapFieldNonemptyDefault = mapFieldNonemptyDefault,
      arrayFieldEmptyDefault = arrayFieldEmptyDefault
    )

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithDefaults.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldEmptyDefault).asInstanceOf[AnyRef]
      case 2 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldNonemptyDefault).asInstanceOf[AnyRef]
      case 3 => org.apache.avro.scala.Conversions.scalaToJava(arrayFieldEmptyDefault).asInstanceOf[AnyRef]
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
    encoder.writeArrayStart()
    encoder.setItemCount(this.arrayFieldEmptyDefault.size)
    for (arrayItem <- this.arrayFieldEmptyDefault) {
      encoder.startItem()
      encoder.writeString(arrayItem)
    }
    encoder.writeArrayEnd()
  }

  def toMutable: MutableRecordWithDefaults =
    new MutableRecordWithDefaults(
      this.stringField,
      scala.collection.mutable.Map[String, Int]((this.mapFieldEmptyDefault).toSeq: _*),
      scala.collection.mutable.Map[String, String]((this.mapFieldNonemptyDefault).toSeq: _*),
      scala.collection.mutable.ArrayBuffer[String]((this.arrayFieldEmptyDefault): _*)
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithDefaults] ||
    other.isInstanceOf[MutableRecordWithDefaults]
}

class MutableRecordWithDefaults(
    var stringField : String = "default string",
    var mapFieldEmptyDefault : scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String, Int](),
    var mapFieldNonemptyDefault : scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb"),
    var arrayFieldEmptyDefault : scala.collection.mutable.Buffer[String] = scala.collection.mutable.ArrayBuffer[String]()
) extends org.apache.avro.scala.MutableRecordBase[RecordWithDefaults] {

  def this() = this("default string", scala.collection.mutable.Map[String, Int](), scala.collection.mutable.Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb"), scala.collection.mutable.ArrayBuffer[String]())

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithDefaults.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldEmptyDefault).asInstanceOf[AnyRef]
      case 2 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldNonemptyDefault).asInstanceOf[AnyRef]
      case 3 => org.apache.avro.scala.Conversions.scalaToJava(arrayFieldEmptyDefault).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.stringField = value.toString
      case 1 => this.mapFieldEmptyDefault = value.asInstanceOf[scala.collection.mutable.Map[String, Int]]
      case 2 => this.mapFieldNonemptyDefault = value.asInstanceOf[scala.collection.mutable.Map[String, String]]
      case 3 => this.arrayFieldEmptyDefault = value.asInstanceOf[scala.collection.mutable.Buffer[String]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithDefaults = {
    return new RecordWithDefaults(
      stringField = this.stringField,
      mapFieldEmptyDefault = this.mapFieldEmptyDefault.toMap,
      mapFieldNonemptyDefault = this.mapFieldNonemptyDefault.toMap,
      arrayFieldEmptyDefault = this.arrayFieldEmptyDefault.toList
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
    encoder.writeArrayStart()
    encoder.setItemCount(this.arrayFieldEmptyDefault.size)
    for (arrayItem <- this.arrayFieldEmptyDefault) {
      encoder.startItem()
      encoder.writeString(arrayItem)
    }
    encoder.writeArrayEnd()
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
    this.arrayFieldEmptyDefault = {
      val array = scala.collection.mutable.ArrayBuffer[String]()
      var blockSize: Long = decoder.readArrayStart()
      while(blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val arrayItem = (
              decoder.readString())
          array.append(arrayItem)
        }
        blockSize = decoder.arrayNext()
      }
      array
    }
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithDefaults] ||
    other.isInstanceOf[MutableRecordWithDefaults]

}

object RecordWithDefaults extends org.apache.avro.scala.RecordType[RecordWithDefaults, MutableRecordWithDefaults] {
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
          |  }, {
          |    "name" : "array_field_empty_default",
          |    "type" : {
          |      "type" : "array",
          |      "items" : "string"
          |    },
          |    "default" : [ ]
          |  } ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala
