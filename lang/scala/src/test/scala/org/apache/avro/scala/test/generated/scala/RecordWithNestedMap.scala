// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class RecordWithNestedMap(
    var nestedMapField : Map[String, Map[String, Int]]
) extends org.apache.avro.scala.Record {

  def this() = this(Map[String, Map[String, Int]]().asInstanceOf[Map[String, Map[String, Int]]])

  override def getSchema(): org.apache.avro.Schema = RecordWithNestedMap.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(nestedMapField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.nestedMapField = value.asInstanceOf[Map[String, Map[String, Int]]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeMapStart()
    encoder.setItemCount(this.nestedMapField.size)
    for ((mapKey, mapValue) <- this.nestedMapField) {
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
    this.nestedMapField = {
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

object RecordWithNestedMap extends org.apache.avro.scala.RecordType[RecordWithNestedMap] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordWithNestedMap",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "nested_map_field",
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