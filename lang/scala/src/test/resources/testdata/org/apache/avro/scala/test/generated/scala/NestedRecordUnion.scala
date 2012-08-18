// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class UnionContained(
    val data : Int,
    val mapField : Map[String, String]
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(data : Int = this.data, mapField : Map[String, String] = this.mapField): UnionContained =
    new UnionContained(
      data = data,
      mapField = mapField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return UnionContained.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => data.asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaCollectionToJava(mapField).asInstanceOf[AnyRef]
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

  def toMutable: MutableUnionContained =
    new MutableUnionContained(
      this.data,
      scala.collection.mutable.Map[String, String]((this.mapField).toSeq: _*)
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionContained] ||
    other.isInstanceOf[MutableUnionContained]
}

class MutableUnionContained(
    var data : Int = 0,
    var mapField : scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map[String, String]().asInstanceOf[scala.collection.mutable.Map[String, String]]
) extends org.apache.avro.scala.MutableRecordBase[UnionContained] {

  def this() = this(0, scala.collection.mutable.Map[String, String]().asInstanceOf[scala.collection.mutable.Map[String, String]])

  override def getSchema(): org.apache.avro.Schema = {
    return UnionContained.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => data.asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaCollectionToJava(mapField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => this.data = value.asInstanceOf[Int]
      case 1 => this.mapField = org.apache.avro.scala.Conversions.javaCollectionToScala(value).asInstanceOf[scala.collection.mutable.Map[String, String]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionContained = {
    return new UnionContained(
      data = this.data,
      mapField = this.mapField.toMap
    )
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
    map
    }
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionContained] ||
    other.isInstanceOf[MutableUnionContained]

}

object UnionContained {
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

}  // package org.apache.avro.scala.test.generated.scala


// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class UnionContainer(
    val containedOrNullUnion : Option[org.apache.avro.scala.test.generated.scala.UnionContained],
    val containedOrStringUnion : org.apache.avro.scala.test.generated.scala.UnionContainer.ImmutableContainedOrStringUnionUnionType
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(containedOrNullUnion : Option[org.apache.avro.scala.test.generated.scala.UnionContained] = this.containedOrNullUnion, containedOrStringUnion : org.apache.avro.scala.test.generated.scala.UnionContainer.ImmutableContainedOrStringUnionUnionType = this.containedOrStringUnion): UnionContainer =
    new UnionContainer(
      containedOrNullUnion = containedOrNullUnion,
      containedOrStringUnion = containedOrStringUnion
    )

  override def getSchema(): org.apache.avro.Schema = {
    return UnionContainer.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => containedOrNullUnion.getOrElse(null).asInstanceOf[AnyRef]
      case 1 => containedOrStringUnion.getData.asInstanceOf[AnyRef]
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

  def toMutable: MutableUnionContainer =
    new MutableUnionContainer(
      this.containedOrNullUnion.map(_.toMutable),
      this.containedOrStringUnion.toMutable
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionContainer] ||
    other.isInstanceOf[MutableUnionContainer]
}

class MutableUnionContainer(
    var containedOrNullUnion : Option[org.apache.avro.scala.test.generated.scala.MutableUnionContained] = null,
    var containedOrStringUnion : org.apache.avro.scala.test.generated.scala.UnionContainer.MutableContainedOrStringUnionUnionType = null
) extends org.apache.avro.scala.MutableRecordBase[UnionContainer] {

  def this() = this(null, null)

  override def getSchema(): org.apache.avro.Schema = {
    return UnionContainer.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => containedOrNullUnion.getOrElse(null).asInstanceOf[AnyRef]
      case 1 => containedOrStringUnion.getData.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => this.containedOrNullUnion = Option(value).map(value => value.asInstanceOf[org.apache.avro.scala.test.generated.scala.MutableUnionContained])
      case 1 => this.containedOrStringUnion = org.apache.avro.scala.test.generated.scala.UnionContainer.MutableContainedOrStringUnionUnionType(value)
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): UnionContainer = {
    return new UnionContainer(
      containedOrNullUnion = this.containedOrNullUnion.map(_.build),
      containedOrStringUnion = this.containedOrStringUnion.toImmutable
    )
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
      case 1 => Some({ val record = new org.apache.avro.scala.test.generated.scala.MutableUnionContained(); record.decode(decoder); record })
    }
    this.containedOrStringUnion = org.apache.avro.scala.test.generated.scala.UnionContainer.ContainedOrStringUnionUnionType.decode(decoder)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionContainer] ||
    other.isInstanceOf[MutableUnionContainer]

}

object UnionContainer {
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
  
  abstract class ImmutableContainedOrNullUnionUnionType extends ContainedOrNullUnionUnionType {
    def toMutable: MutableContainedOrNullUnionUnionType
  }
  
  object ContainedOrNullUnionUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): MutableContainedOrNullUnionUnionType = {
      decoder.readIndex() match {
        case 0 => return MutableContainedOrNullUnionUnionNull(data = {decoder.readNull(); null})
        case 1 => return MutableContainedOrNullUnionUnionUnionContained(data = { val record = new org.apache.avro.scala.test.generated.scala.MutableUnionContained(); record.decode(decoder); record })
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class ContainedOrNullUnionUnionNull(data: Null) extends ImmutableContainedOrNullUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableContainedOrNullUnionUnionNull =
      MutableContainedOrNullUnionUnionNull(this.data)
  }
  
  case class ContainedOrNullUnionUnionUnionContained(data: org.apache.avro.scala.test.generated.scala.UnionContained) extends ImmutableContainedOrNullUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      data.encode(encoder)
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableContainedOrNullUnionUnionUnionContained =
      MutableContainedOrNullUnionUnionUnionContained(this.data.toMutable)
  }
  
  abstract class MutableContainedOrNullUnionUnionType
      extends ContainedOrNullUnionUnionType
      with org.apache.avro.scala.Decodable {
    def toImmutable: ImmutableContainedOrNullUnionUnionType
  }
  
  object MutableContainedOrNullUnionUnionType {
    def apply(data: Any): MutableContainedOrNullUnionUnionType = data match {
      case null => MutableContainedOrNullUnionUnionNull(null)
      case data: org.apache.avro.scala.test.generated.scala.MutableUnionContained => MutableContainedOrNullUnionUnionUnionContained(data)
      case _ => throw new java.io.IOException("Bad union data: " + data)
    }
  }
  
  case class MutableContainedOrNullUnionUnionNull(var data: Null) extends MutableContainedOrNullUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = {decoder.readNull(); null}
    }
    def toImmutable: ContainedOrNullUnionUnionNull =
      ContainedOrNullUnionUnionNull(this.data)
  }
  
  case class MutableContainedOrNullUnionUnionUnionContained(var data: org.apache.avro.scala.test.generated.scala.MutableUnionContained) extends MutableContainedOrNullUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      data.encode(encoder)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = { val record = new org.apache.avro.scala.test.generated.scala.MutableUnionContained(); record.decode(decoder); record }
    }
    def toImmutable: ContainedOrNullUnionUnionUnionContained =
      ContainedOrNullUnionUnionUnionContained(this.data.build)
  }
  abstract class ContainedOrStringUnionUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  abstract class ImmutableContainedOrStringUnionUnionType extends ContainedOrStringUnionUnionType {
    def toMutable: MutableContainedOrStringUnionUnionType
  }
  
  object ContainedOrStringUnionUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): MutableContainedOrStringUnionUnionType = {
      decoder.readIndex() match {
        case 0 => return MutableContainedOrStringUnionUnionString(data = decoder.readString())
        case 1 => return MutableContainedOrStringUnionUnionUnionContained(data = { val record = new org.apache.avro.scala.test.generated.scala.MutableUnionContained(); record.decode(decoder); record })
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class ContainedOrStringUnionUnionString(data: String) extends ImmutableContainedOrStringUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeString(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableContainedOrStringUnionUnionString =
      MutableContainedOrStringUnionUnionString(this.data)
  }
  
  case class ContainedOrStringUnionUnionUnionContained(data: org.apache.avro.scala.test.generated.scala.UnionContained) extends ImmutableContainedOrStringUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      data.encode(encoder)
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableContainedOrStringUnionUnionUnionContained =
      MutableContainedOrStringUnionUnionUnionContained(this.data.toMutable)
  }
  
  abstract class MutableContainedOrStringUnionUnionType
      extends ContainedOrStringUnionUnionType
      with org.apache.avro.scala.Decodable {
    def toImmutable: ImmutableContainedOrStringUnionUnionType
  }
  
  object MutableContainedOrStringUnionUnionType {
    def apply(data: Any): MutableContainedOrStringUnionUnionType = data match {
      case data: CharSequence => MutableContainedOrStringUnionUnionString(data.toString)
      case data: org.apache.avro.scala.test.generated.scala.MutableUnionContained => MutableContainedOrStringUnionUnionUnionContained(data)
      case _ => throw new java.io.IOException("Bad union data: " + data)
    }
  }
  
  case class MutableContainedOrStringUnionUnionString(var data: String) extends MutableContainedOrStringUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeString(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readString()
    }
    def toImmutable: ContainedOrStringUnionUnionString =
      ContainedOrStringUnionUnionString(this.data)
  }
  
  case class MutableContainedOrStringUnionUnionUnionContained(var data: org.apache.avro.scala.test.generated.scala.MutableUnionContained) extends MutableContainedOrStringUnionUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      data.encode(encoder)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = { val record = new org.apache.avro.scala.test.generated.scala.MutableUnionContained(); record.decode(decoder); record }
    }
    def toImmutable: ContainedOrStringUnionUnionUnionContained =
      ContainedOrStringUnionUnionUnionContained(this.data.build)
  }
}

}  // package org.apache.avro.scala.test.generated.scala
