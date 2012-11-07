// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class UnionMany(
    val unionField : org.apache.avro.scala.test.generated.scala.UnionMany.ImmutableUnionFieldUnionType
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(unionField : org.apache.avro.scala.test.generated.scala.UnionMany.ImmutableUnionFieldUnionType = this.unionField): UnionMany =
    new UnionMany(
      unionField = unionField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return UnionMany.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(unionField.getData).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def toMutable: MutableUnionMany =
    new MutableUnionMany(
      this.unionField.toMutable
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionMany] ||
    other.isInstanceOf[MutableUnionMany]
}

class MutableUnionMany(
    var unionField : org.apache.avro.scala.test.generated.scala.UnionMany.MutableUnionFieldUnionType = null
) extends org.apache.avro.scala.MutableRecordBase[UnionMany] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return UnionMany.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(unionField.getData).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.unionField = org.apache.avro.scala.test.generated.scala.UnionMany.MutableUnionFieldUnionType(value)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionMany = {
    return new UnionMany(
      unionField = this.unionField.toImmutable
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.unionField.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.unionField = org.apache.avro.scala.test.generated.scala.UnionMany.UnionFieldUnionType.decode(decoder)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionMany] ||
    other.isInstanceOf[MutableUnionMany]

}

object UnionMany extends org.apache.avro.scala.RecordType {
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
  
  abstract class ImmutableUnionFieldUnionType extends UnionFieldUnionType {
    def toMutable: MutableUnionFieldUnionType
  }
  
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
        case 3 => return MutableUnionFieldUnionString(data = decoder.readString())
        case 4 => return MutableUnionFieldUnionMapString(data = {
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
        })
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class UnionFieldUnionInt(data: Int) extends ImmutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeInt(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableUnionFieldUnionInt =
      MutableUnionFieldUnionInt(this.data)
  }
  
  case class UnionFieldUnionDouble(data: Double) extends ImmutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeDouble(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableUnionFieldUnionDouble =
      MutableUnionFieldUnionDouble(this.data)
  }
  
  case class UnionFieldUnionArrayInt(data: Seq[Int]) extends ImmutableUnionFieldUnionType {
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
    def toMutable: MutableUnionFieldUnionArrayInt =
      MutableUnionFieldUnionArrayInt(scala.collection.mutable.ArrayBuffer[Int]((this.data): _*))
  }
  
  case class UnionFieldUnionString(data: String) extends ImmutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(3)
      encoder.writeString(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableUnionFieldUnionString =
      MutableUnionFieldUnionString(this.data)
  }
  
  case class UnionFieldUnionMapString(data: Map[String, String]) extends ImmutableUnionFieldUnionType {
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
    def toMutable: MutableUnionFieldUnionMapString =
      MutableUnionFieldUnionMapString(scala.collection.mutable.Map[String, String]((this.data).toSeq: _*))
  }
  
  abstract class MutableUnionFieldUnionType
      extends UnionFieldUnionType
      with org.apache.avro.scala.Decodable {
    def toImmutable: ImmutableUnionFieldUnionType
  }
  
  object MutableUnionFieldUnionType {
    def apply(data: Any): MutableUnionFieldUnionType = data match {
      case data: Int => MutableUnionFieldUnionInt(data)
      case data: Double => MutableUnionFieldUnionDouble(data)
      case data: scala.collection.mutable.Buffer[Int] => MutableUnionFieldUnionArrayInt(data)
      case data: CharSequence => MutableUnionFieldUnionString(data.toString)
      case data: scala.collection.mutable.Map[String, String] => MutableUnionFieldUnionMapString(data)
      case _ => throw new java.io.IOException("Bad union data: " + data)
    }
  }
  
  case class MutableUnionFieldUnionInt(var data: Int) extends MutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeInt(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readInt()
    }
    def toImmutable: UnionFieldUnionInt =
      UnionFieldUnionInt(this.data)
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
    def toImmutable: UnionFieldUnionDouble =
      UnionFieldUnionDouble(this.data)
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
    def toImmutable: UnionFieldUnionArrayInt =
      UnionFieldUnionArrayInt(this.data.toList)
  }
  
  case class MutableUnionFieldUnionString(var data: String) extends MutableUnionFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(3)
      encoder.writeString(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readString()
    }
    def toImmutable: UnionFieldUnionString =
      UnionFieldUnionString(this.data)
  }
  
  case class MutableUnionFieldUnionMapString(var data: scala.collection.mutable.Map[String, String]) extends MutableUnionFieldUnionType {
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
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = {
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
    def toImmutable: UnionFieldUnionMapString =
      UnionFieldUnionMapString(this.data.toMap)
  }
}

}  // package org.apache.avro.scala.test.generated.scala
