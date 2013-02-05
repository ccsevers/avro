// This file is machine-generated.

package org.apache.avro.scala.test.generated

import _root_.scala.collection.JavaConverters._

case class UnionMany(
    var unionField : org.apache.avro.scala.test.generated.UnionMany.UnionFieldUnionType
) extends org.apache.avro.scala.Record {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = UnionMany.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(unionField.getData).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.unionField = org.apache.avro.scala.test.generated.UnionMany.UnionFieldUnionType(value)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.unionField = org.apache.avro.scala.test.generated.UnionMany.UnionFieldUnionType.decode(decoder)
  }
}

object UnionMany extends org.apache.avro.scala.RecordType[UnionMany] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionMany",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "union_field",
          |    "type" : [ "int", "double", {
          |      "type" : "array",
          |      "items" : "int"
          |    }, "string", {
          |      "type" : "map",
          |      "values" : "string"
          |    } ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class UnionFieldUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  object UnionFieldUnionType {
    def apply(data: Any): UnionFieldUnionType = data match {
      case data: Int => UnionFieldUnionInt(data)
      case data: Double => UnionFieldUnionDouble(data)
      case data: Seq[Int] => UnionFieldUnionArrayInt(data)
      case data: CharSequence => UnionFieldUnionString(data.toString)
      case data: Map[String, String] => UnionFieldUnionMapString(data)
      case _ => throw new java.io.IOException(s"Unexpected union data of type ${data.getClass.getName}: ${data}")
    }
  
    def decode(decoder: org.apache.avro.io.Decoder): UnionFieldUnionType = {
      decoder.readIndex() match {
        case 0 => return UnionFieldUnionInt(data = decoder.readInt())
        case 1 => return UnionFieldUnionDouble(data = decoder.readDouble())
        case 2 => return UnionFieldUnionArrayInt(data = {
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
          array
        })
        case 3 => return UnionFieldUnionString(data = decoder.readString())
        case 4 => return UnionFieldUnionMapString(data = {
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
        })
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class UnionFieldUnionInt(data: Int) extends UnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeInt(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class UnionFieldUnionDouble(data: Double) extends UnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeDouble(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class UnionFieldUnionArrayInt(data: Seq[Int]) extends UnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(2)
      encoder.writeArrayStart()
      encoder.setItemCount(data.size)
      for (arrayItem <- data) {
        encoder.startItem()
        encoder.writeInt(arrayItem)
      }
      encoder.writeArrayEnd()
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class UnionFieldUnionString(data: String) extends UnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(3)
      encoder.writeString(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class UnionFieldUnionMapString(data: Map[String, String]) extends UnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(4)
      encoder.writeMapStart()
      encoder.setItemCount(data.size)
      for ((mapKey, mapValue) <- data) {
        encoder.startItem()
        encoder.writeString(mapKey)
        encoder.writeString(mapValue)
      }
      encoder.writeMapEnd()
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
}