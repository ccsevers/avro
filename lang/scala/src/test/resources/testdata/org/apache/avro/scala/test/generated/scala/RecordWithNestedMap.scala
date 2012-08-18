// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class RecordWithNestedMap(
    val nestedMapField : Map[String, Map[String, Int]]
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(nestedMapField : Map[String, Map[String, Int]] = this.nestedMapField): RecordWithNestedMap =
    new RecordWithNestedMap(
      nestedMapField = nestedMapField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithNestedMap.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(nestedMapField).asInstanceOf[AnyRef]
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

  def toMutable: MutableRecordWithNestedMap =
    new MutableRecordWithNestedMap(
      scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]((this.nestedMapField.mapValues { v0 => scala.collection.mutable.Map[String, Int]((v0).toSeq: _*) }).toSeq: _*)
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithNestedMap] ||
    other.isInstanceOf[MutableRecordWithNestedMap]
}

class MutableRecordWithNestedMap(
    var nestedMapField : scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]] = scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]().asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]]
) extends org.apache.avro.scala.MutableRecordBase[RecordWithNestedMap] {

  def this() = this(scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]().asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]])

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithNestedMap.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(nestedMapField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.nestedMapField = value.asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithNestedMap = {
    return new RecordWithNestedMap(
      nestedMapField = this.nestedMapField.mapValues { _.toMap }.toMap
    )
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
      val map = scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]()
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
            map
            })
          map += (key -> value)
        }
        blockSize = decoder.mapNext()
      }
    map.asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]]
    }
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithNestedMap] ||
    other.isInstanceOf[MutableRecordWithNestedMap]

}

object RecordWithNestedMap {
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

}  // package org.apache.avro.scala.test.generated.scala
