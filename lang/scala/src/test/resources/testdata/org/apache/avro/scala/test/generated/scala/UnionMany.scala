// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class UnionMany(
    val unionField: org.apache.avro.scala.test.generated.scala.UnionMany.UnionFieldUnionType
) extends org.apache.avro.scala.RecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return UnionMany.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => return this.unionField// TODO(taton) Not implemented!!
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }
}

class MutableUnionMany(
    var unionField: org.apache.avro.scala.test.generated.scala.UnionMany.MutableUnionFieldUnionType = null
) extends org.apache.avro.scala.MutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return UnionMany.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => return this.unionField// TODO(taton) Not implemented!!
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => this.unionField = value.asInstanceOf[org.apache.avro.scala.test.generated.scala.UnionMany.MutableUnionFieldUnionType]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionMany = {
    return new UnionMany(
      unionField = this.unionField
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.unionField = org.apache.avro.scala.test.generated.scala.UnionMany.UnionFieldUnionType.decode(decoder)
  }
}

object UnionMany {
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
          |    } ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class UnionFieldUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  object UnionFieldUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): MutableUnionFieldUnionType = {
      decoder.readIndex() match {
        case 0 => return MutableUnionFieldUnionInt(data = decoder.readInt())
        case 1 => return MutableUnionFieldUnionDouble(data = decoder.readDouble())
        case 2 => return MutableUnionFieldUnionArrayInt(data = {
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
  }
  
  case class UnionFieldUnionDouble(data: Double) extends UnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeDouble(data)
    }
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
  }
  
  abstract class MutableUnionFieldUnionType
      extends UnionFieldUnionType
      with org.apache.avro.scala.Decodable
  
  case class MutableUnionFieldUnionInt(var data: Int) extends MutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeInt(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readInt()
    }
  }
  
  case class MutableUnionFieldUnionDouble(var data: Double) extends MutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeDouble(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readDouble()
    }
  }
  
  case class MutableUnionFieldUnionArrayInt(var data: scala.collection.mutable.Buffer[Int]) extends MutableUnionFieldUnionType {
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
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = {
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
      }
    }
  }
}

}  // package org.apache.avro.scala.test.generated.scala