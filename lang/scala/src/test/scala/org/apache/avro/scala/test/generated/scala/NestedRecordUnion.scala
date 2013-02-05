// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class UnionContained(
    var data : Int,
    var mapField : Map[String, String]
) extends org.apache.avro.scala.Record {

  def this() = this(0, Map[String, String]().asInstanceOf[Map[String, String]])

  override def getSchema(): org.apache.avro.Schema = UnionContained.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(data).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(mapField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.data = value.asInstanceOf[Int]
      case 1 => this.mapField = value.asInstanceOf[Map[String, String]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeInt(this.data)
    encoder.writeMapStart()
    encoder.setItemCount(this.mapField.size)
    for ((mapKey, mapValue) <- this.mapField) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeString(mapValue)
    }
    encoder.writeMapEnd()
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.data = decoder.readInt()
    this.mapField = {
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
  }
}

object UnionContained extends org.apache.avro.scala.RecordType[UnionContained] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionContained",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "data",
          |    "type" : "int"
          |  }, {
          |    "name" : "map_field",
          |    "type" : {
          |      "type" : "map",
          |      "values" : "string"
          |    }
          |  } ]
          |}
      """
      .stripMargin)
}

}

// This file is machine-generated.

package org.apache.avro.scala.test.generated {

import _root_.scala.collection.JavaConverters._

case class UnionContainer(
    var containedOrNullUnion : Option[org.apache.avro.scala.test.generated.UnionContained],
    var containedOrStringUnion : org.apache.avro.scala.test.generated.UnionContainer.ContainedOrStringUnionUnionType
) extends org.apache.avro.scala.Record {

  def this() = this(null, null)

  override def getSchema(): org.apache.avro.Schema = UnionContainer.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(containedOrNullUnion.getOrElse(null)).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(containedOrStringUnion.getData).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.containedOrNullUnion = Option(value).map(value => value.asInstanceOf[org.apache.avro.scala.test.generated.UnionContained])
      case 1 => this.containedOrStringUnion = org.apache.avro.scala.test.generated.UnionContainer.ContainedOrStringUnionUnionType(value)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.containedOrNullUnion match {
      case None => {
        encoder.writeIndex(0)
        encoder.writeNull()
      }
      case Some(optionalValue) => {
        encoder.writeIndex(1)
        optionalValue.encode(encoder)
      }
    }
    this.containedOrStringUnion.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.containedOrNullUnion = decoder.readIndex() match {
      case 0 => { decoder.readNull(); None }
      case 1 => Some({ val record = new org.apache.avro.scala.test.generated.UnionContained(); record.decode(decoder); record })
    }
    this.containedOrStringUnion = org.apache.avro.scala.test.generated.UnionContainer.ContainedOrStringUnionUnionType.decode(decoder)
  }
}

object UnionContainer extends org.apache.avro.scala.RecordType[UnionContainer] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "UnionContainer",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "contained_or_null_union",
          |    "type" : [ "null", {
          |      "type" : "record",
          |      "name" : "UnionContained",
          |      "fields" : [ {
          |        "name" : "data",
          |        "type" : "int"
          |      }, {
          |        "name" : "map_field",
          |        "type" : {
          |          "type" : "map",
          |          "values" : "string"
          |        }
          |      } ]
          |    } ]
          |  }, {
          |    "name" : "contained_or_string_union",
          |    "type" : [ "string", "UnionContained" ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class ContainedOrNullUnionUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  object ContainedOrNullUnionUnionType {
    def apply(data: Any): ContainedOrNullUnionUnionType = data match {
      case null => ContainedOrNullUnionUnionNull(null)
      case data: org.apache.avro.scala.test.generated.UnionContained => ContainedOrNullUnionUnionUnionContained(data)
      case _ => throw new java.io.IOException("Unexpected union data of type " + data.getClass.getName + ": " + data)
    }
  
    def decode(decoder: org.apache.avro.io.Decoder): ContainedOrNullUnionUnionType = {
      decoder.readIndex() match {
        case 0 => return ContainedOrNullUnionUnionNull(data = {decoder.readNull(); null})
        case 1 => return ContainedOrNullUnionUnionUnionContained(data = { val record = new org.apache.avro.scala.test.generated.UnionContained(); record.decode(decoder); record })
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class ContainedOrNullUnionUnionNull(data: Null) extends ContainedOrNullUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class ContainedOrNullUnionUnionUnionContained(data: org.apache.avro.scala.test.generated.UnionContained) extends ContainedOrNullUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      data.encode(encoder)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  abstract class ContainedOrStringUnionUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  object ContainedOrStringUnionUnionType {
    def apply(data: Any): ContainedOrStringUnionUnionType = data match {
      case data: CharSequence => ContainedOrStringUnionUnionString(data.toString)
      case data: org.apache.avro.scala.test.generated.UnionContained => ContainedOrStringUnionUnionUnionContained(data)
      case _ => throw new java.io.IOException("Unexpected union data of type " + data.getClass.getName + ": " + data)
    }
  
    def decode(decoder: org.apache.avro.io.Decoder): ContainedOrStringUnionUnionType = {
      decoder.readIndex() match {
        case 0 => return ContainedOrStringUnionUnionString(data = decoder.readString())
        case 1 => return ContainedOrStringUnionUnionUnionContained(data = { val record = new org.apache.avro.scala.test.generated.UnionContained(); record.decode(decoder); record })
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class ContainedOrStringUnionUnionString(data: String) extends ContainedOrStringUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeString(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
  
  case class ContainedOrStringUnionUnionUnionContained(data: org.apache.avro.scala.test.generated.UnionContained) extends ContainedOrStringUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      data.encode(encoder)
    }
    override def hashCode(): Int = { return data.hashCode() }
  }
}

}