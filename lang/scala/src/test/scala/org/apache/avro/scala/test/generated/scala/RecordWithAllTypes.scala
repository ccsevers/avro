// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class RecordWithAllTypes(
    var nullField : Null,
    var booleanField : Boolean,
    var intField : Int,
    var longField : Long,
    var floatField : Float,
    var doubleField : Double,
    var stringField : String,
    var bytesField : Seq[Byte],
    var fixedField : Seq[Byte],
    var intArrayField : Seq[Int],
    var intMapField : Map[String, Int],
    var intArrayArrayField : Seq[Seq[Int]],
    var intMapMapField : Map[String, Map[String, Int]]
) extends org.apache.avro.scala.Record {

  def this() = this(null, false, 0, 0, 0, 0, null, Array[Byte](), new Array[Byte](16).toSeq, List[Int]().asInstanceOf[Seq[Int]], Map[String, Int]().asInstanceOf[Map[String, Int]], List[Seq[Int]]().asInstanceOf[Seq[Seq[Int]]], Map[String, Map[String, Int]]().asInstanceOf[Map[String, Map[String, Int]]])

  override def getSchema(): org.apache.avro.Schema = RecordWithAllTypes.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(null).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(booleanField).asInstanceOf[AnyRef]
      case 2 => org.apache.avro.scala.Conversions.scalaToJava(intField).asInstanceOf[AnyRef]
      case 3 => org.apache.avro.scala.Conversions.scalaToJava(longField).asInstanceOf[AnyRef]
      case 4 => org.apache.avro.scala.Conversions.scalaToJava(floatField).asInstanceOf[AnyRef]
      case 5 => org.apache.avro.scala.Conversions.scalaToJava(doubleField).asInstanceOf[AnyRef]
      case 6 => org.apache.avro.scala.Conversions.scalaToJava(stringField).asInstanceOf[AnyRef]
      case 7 => org.apache.avro.scala.Conversions.scalaToJava(java.nio.ByteBuffer.wrap(bytesField.toArray[Byte])).asInstanceOf[AnyRef]
      case 8 => org.apache.avro.scala.Conversions.scalaToJava(new org.apache.avro.generic.GenericData.Fixed(getSchema(), fixedField.toArray[Byte])).asInstanceOf[AnyRef]
      case 9 => org.apache.avro.scala.Conversions.scalaToJava(intArrayField).asInstanceOf[AnyRef]
      case 10 => org.apache.avro.scala.Conversions.scalaToJava(intMapField).asInstanceOf[AnyRef]
      case 11 => org.apache.avro.scala.Conversions.scalaToJava(intArrayArrayField).asInstanceOf[AnyRef]
      case 12 => org.apache.avro.scala.Conversions.scalaToJava(intMapMapField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => ()
      case 1 => this.booleanField = value.asInstanceOf[Boolean]
      case 2 => this.intField = value.asInstanceOf[Int]
      case 3 => this.longField = value.asInstanceOf[Long]
      case 4 => this.floatField = value.asInstanceOf[Float]
      case 5 => this.doubleField = value.asInstanceOf[Double]
      case 6 => this.stringField = value.toString
      case 7 => this.bytesField = value.asInstanceOf[java.nio.ByteBuffer].array()
      case 8 => this.fixedField = value.asInstanceOf[org.apache.avro.generic.GenericData.Fixed].bytes()
      case 9 => this.intArrayField = value.asInstanceOf[Seq[Int]]
      case 10 => this.intMapField = value.asInstanceOf[Map[String, Int]]
      case 11 => this.intArrayArrayField = value.asInstanceOf[Seq[Seq[Int]]]
      case 12 => this.intMapMapField = value.asInstanceOf[Map[String, Map[String, Int]]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeNull()
    encoder.writeBoolean(this.booleanField)
    encoder.writeInt(this.intField)
    encoder.writeLong(this.longField)
    encoder.writeFloat(this.floatField)
    encoder.writeDouble(this.doubleField)
    encoder.writeString(this.stringField)
    encoder.writeBytes(this.bytesField.asInstanceOf[Array[Byte]])
    encoder.writeBytes(this.fixedField.asInstanceOf[Array[Byte]])
    encoder.writeArrayStart()
    encoder.setItemCount(this.intArrayField.size)
    for (arrayItem <- this.intArrayField) {
      encoder.startItem()
      encoder.writeInt(arrayItem)
    }
    encoder.writeArrayEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.intMapField.size)
    for ((mapKey, mapValue) <- this.intMapField) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeInt(mapValue)
    }
    encoder.writeMapEnd()
    encoder.writeArrayStart()
    encoder.setItemCount(this.intArrayArrayField.size)
    for (arrayItem <- this.intArrayArrayField) {
      encoder.startItem()
      encoder.writeArrayStart()
      encoder.setItemCount(arrayItem.size)
      for (arrayItem <- arrayItem) {
        encoder.startItem()
        encoder.writeInt(arrayItem)
      }
      encoder.writeArrayEnd()
    }
    encoder.writeArrayEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.intMapMapField.size)
    for ((mapKey, mapValue) <- this.intMapMapField) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeMapStart()
      encoder.setItemCount(mapValue.size)
      for ((mapKey, mapValue) <- mapValue) {
        encoder.startItem()
        encoder.writeString(mapKey)
        encoder.writeInt(mapValue)
      }
      encoder.writeMapEnd()
    }
    encoder.writeMapEnd()
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    {decoder.readNull(); null}
    this.booleanField = decoder.readBoolean()
    this.intField = decoder.readInt()
    this.longField = decoder.readLong()
    this.floatField = decoder.readFloat()
    this.doubleField = decoder.readDouble()
    this.stringField = decoder.readString()
    this.bytesField = decoder.readBytes(null).array.toBuffer
    this.fixedField = { val bytes = new Array[Byte](16); decoder.readFixed(bytes); bytes }
    this.intArrayField = {
      val array = scala.collection.mutable.ArrayBuffer[Int]()
      var blockSize: Long = decoder.readArrayStart()
      while(blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val arrayItem = (
              decoder.readInt())
          array.append(arrayItem)
        }
        blockSize = decoder.arrayNext()
      }
      array.toList
    }
    this.intMapField = {
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
    this.intArrayArrayField = {
      val array = scala.collection.mutable.ArrayBuffer[Seq[Int]]()
      var blockSize: Long = decoder.readArrayStart()
      while(blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val arrayItem = (
              {
                val array = scala.collection.mutable.ArrayBuffer[Int]()
                var blockSize: Long = decoder.readArrayStart()
                while(blockSize != 0L) {
                  for (_ <- 0L until blockSize) {
                    val arrayItem = (
                        decoder.readInt())
                    array.append(arrayItem)
                  }
                  blockSize = decoder.arrayNext()
                }
                array.toList
              })
          array.append(arrayItem)
        }
        blockSize = decoder.arrayNext()
      }
      array.toList
    }
    this.intMapMapField = {
      val map = scala.collection.mutable.Map[String, Map[String, Int]]()
      var blockSize: Long = decoder.readMapStart()
      while (blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val key: String = decoder.readString()
          val value = (
            {
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
            })
          map += (key -> value)
        }
        blockSize = decoder.mapNext()
      }
    map.toMap
    }
  }
}

object RecordWithAllTypes extends org.apache.avro.scala.RecordType[RecordWithAllTypes] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordWithAllTypes",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "null_field",
          |    "type" : "null"
          |  }, {
          |    "name" : "boolean_field",
          |    "type" : "boolean"
          |  }, {
          |    "name" : "int_field",
          |    "type" : "int"
          |  }, {
          |    "name" : "long_field",
          |    "type" : "long"
          |  }, {
          |    "name" : "float_field",
          |    "type" : "float"
          |  }, {
          |    "name" : "double_field",
          |    "type" : "double"
          |  }, {
          |    "name" : "string_field",
          |    "type" : "string"
          |  }, {
          |    "name" : "bytes_field",
          |    "type" : "bytes"
          |  }, {
          |    "name" : "fixed_field",
          |    "type" : {
          |      "type" : "fixed",
          |      "name" : "anon_fixed_16",
          |      "size" : 16
          |    }
          |  }, {
          |    "name" : "int_array_field",
          |    "type" : {
          |      "type" : "array",
          |      "items" : "int"
          |    }
          |  }, {
          |    "name" : "int_map_field",
          |    "type" : {
          |      "type" : "map",
          |      "values" : "int"
          |    }
          |  }, {
          |    "name" : "int_array_array_field",
          |    "type" : {
          |      "type" : "array",
          |      "items" : {
          |        "type" : "array",
          |        "items" : "int"
          |      }
          |    }
          |  }, {
          |    "name" : "int_map_map_field",
          |    "type" : {
          |      "type" : "map",
          |      "values" : {
          |        "type" : "map",
          |        "values" : "int"
          |      }
          |    }
          |  } ]
          |}
      """
      .stripMargin)
}

}