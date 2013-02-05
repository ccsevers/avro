// This file is machine-generated.

package org.apache.avro.scala.test.generated

import _root_.scala.collection.JavaConverters._

case class UnionOptionalComplex(
    var optionalField : Option[Seq[String]]
) extends org.apache.avro.scala.Record {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = UnionOptionalComplex.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(optionalField.getOrElse(null)).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.optionalField = Option(value).map(value => value.asInstanceOf[scala.collection.mutable.Buffer[String]])
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
        encoder.writeArrayStart()
    encoder.setItemCount(optionalValue.size)
    for (arrayItem <- optionalValue) {
      encoder.startItem()
      encoder.writeString(arrayItem)
    }
    encoder.writeArrayEnd()
      }
    }
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.optionalField = decoder.readIndex() match {
      case 0 => { decoder.readNull(); None }
      case 1 => Some({
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
    })
    }
  }
}

object UnionOptionalComplex extends org.apache.avro.scala.RecordType[UnionOptionalComplex] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionOptionalComplex",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "optional_field",
          |    "type" : [ "null", {
          |      "type" : "array",
          |      "items" : "string"
          |    } ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class OptionalFieldUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  object OptionalFieldUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): OptionalFieldUnionType = {
      decoder.readIndex() match {
        case 0 => return OptionalFieldUnionNull(data = {decoder.readNull(); null})
        case 1 => return OptionalFieldUnionArrayString(data = {
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
        })
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
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class OptionalFieldUnionArrayString(data: Seq[String]) extends OptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeArrayStart()
      encoder.setItemCount(data.size)
      for (arrayItem <- data) {
        encoder.startItem()
        encoder.writeString(arrayItem)
      }
      encoder.writeArrayEnd()
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
}