// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class RecordDefaultField(
    var a : String
) extends org.apache.avro.scala.Record {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = RecordDefaultField.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(a).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.a = value.toString
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.a)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.a = decoder.readString()
  }
}

object RecordDefaultField extends org.apache.avro.scala.RecordType[RecordDefaultField] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordDefaultField",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "a",
          |    "type" : "string"
          |  } ]
          |}
      """
      .stripMargin)
}

}

// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class RecordWithDefaults(
    var stringField : String = "default string",
    var mapFieldEmptyDefault : Map[String, Int] = Map[String, Int](),
    var mapFieldNonemptyDefault : Map[String, String] = Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb"),
    var arrayFieldEmptyDefault : Seq[String] = List[String](),
    var recordFieldDefault : org.apache.avro.scala.test.generated.RecordDefaultField = null
) extends org.apache.avro.scala.Record {

  def this() = this("default string", Map[String, Int](), Map[String, String]("a" -> "aa", "b\"b" -> "bb\"bb"), List[String](), null)

  override def getSchema(): org.apache.avro.Schema = RecordWithDefaults.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldEmptyDefault).asInstanceOf[AnyRef]
      case 2 => org.apache.avro.scala.Conversions.scalaToJava(mapFieldNonemptyDefault).asInstanceOf[AnyRef]
      case 3 => org.apache.avro.scala.Conversions.scalaToJava(arrayFieldEmptyDefault).asInstanceOf[AnyRef]
      case 4 => org.apache.avro.scala.Conversions.scalaToJava(recordFieldDefault /* TODO Not Implemented */).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.stringField = value.toString
      case 1 => this.mapFieldEmptyDefault = value.asInstanceOf[Map[String, Int]]
      case 2 => this.mapFieldNonemptyDefault = value.asInstanceOf[Map[String, String]]
      case 3 => this.arrayFieldEmptyDefault = value.asInstanceOf[Seq[String]]
      case 4 => this.recordFieldDefault = value.asInstanceOf[org.apache.avro.scala.test.generated.RecordDefaultField]
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
    this.recordFieldDefault.encode(encoder)
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
    map.toMap
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
    map.toMap
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
      array.toList
    }
    this.recordFieldDefault = { val record = new org.apache.avro.scala.test.generated.RecordDefaultField(); record.decode(decoder); record }
  }
}

object RecordWithDefaults extends org.apache.avro.scala.RecordType[RecordWithDefaults] {
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
          |  }, {
          |    "name" : "record_field_default",
          |    "type" : {
          |      "type" : "record",
          |      "name" : "RecordDefaultField",
          |      "fields" : [ {
          |        "name" : "a",
          |        "type" : "string"
          |      } ]
          |    },
          |    "default" : {
          |      "a" : "aa"
          |    }
          |  } ]
          |}
      """
      .stripMargin)
}

}