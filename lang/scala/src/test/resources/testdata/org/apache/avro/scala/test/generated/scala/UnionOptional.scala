// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class UnionOptional(
    val optionalField: Option[Int]
) extends org.apache.avro.scala.RecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return UnionOptional.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => return this.optionalField// TODO(taton) Not implemented!!
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.optionalField match {
      case None => {
        encoder.writeIndex(0)
        encoder.writeNull()
      }
      case Some(optionalValue) => {
        encoder.writeIndex(1)
        encoder.writeInt(optionalValue)
      }
    }
  }
}

class MutableUnionOptional(
    var optionalField: Option[Int] = null
) extends org.apache.avro.scala.MutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return UnionOptional.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => return this.optionalField// TODO(taton) Not implemented!!
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => this.optionalField = value.asInstanceOf[Option[Int]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionOptional = {
    return new UnionOptional(
      optionalField = this.optionalField
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.optionalField match {
      case None => {
        encoder.writeIndex(0)
        encoder.writeNull()
      }
      case Some(optionalValue) => {
        encoder.writeIndex(1)
        encoder.writeInt(optionalValue)
      }
    }
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.optionalField = decoder.readIndex() match {
      case 0 => { decoder.readNull(); None }
      case 1 => Some(decoder.readInt())
    }
  }
}

object UnionOptional {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionOptional",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "optional_field",
          |    "type" : [ "null", "int" ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class OptionalFieldUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  object OptionalFieldUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): MutableOptionalFieldUnionType = {
      decoder.readIndex() match {
        case 0 => return MutableOptionalFieldUnionNull(data = { decoder.readNull(); null })
        case 1 => return MutableOptionalFieldUnionInt(data = decoder.readInt())
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class OptionalFieldUnionNull(data: Null) extends OptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
  }
  
  case class OptionalFieldUnionInt(data: Int) extends OptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeInt(data)
    }
  }
  
  abstract class MutableOptionalFieldUnionType
      extends OptionalFieldUnionType
      with org.apache.avro.scala.Decodable
  
  case class MutableOptionalFieldUnionNull(var data: Null) extends MutableOptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = { decoder.readNull(); null }
    }
  }
  
  case class MutableOptionalFieldUnionInt(var data: Int) extends MutableOptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeInt(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readInt()
    }
  }
}

}  // package org.apache.avro.scala.test.generated.scala