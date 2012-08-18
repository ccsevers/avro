// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class UnionOptional(
    val optionalField : Option[String]
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(optionalField : Option[String] = this.optionalField): UnionOptional =
    new UnionOptional(
      optionalField = optionalField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return UnionOptional.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(optionalField.getOrElse(null)).asInstanceOf[AnyRef]
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
        encoder.writeString(optionalValue)
      }
    }
  }

  def toMutable: MutableUnionOptional =
    new MutableUnionOptional(
      this.optionalField
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionOptional] ||
    other.isInstanceOf[MutableUnionOptional]
}

class MutableUnionOptional(
    var optionalField : Option[String] = null
) extends org.apache.avro.scala.MutableRecordBase[UnionOptional] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return UnionOptional.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(optionalField.getOrElse(null)).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.optionalField = Option(value).map(value => value.toString)
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
        encoder.writeString(optionalValue)
      }
    }
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.optionalField = decoder.readIndex() match {
      case 0 => { decoder.readNull(); None }
      case 1 => Some(decoder.readString())
    }
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[UnionOptional] ||
    other.isInstanceOf[MutableUnionOptional]

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
          |    "type" : [ "null", "string" ]
          |  } ]
          |}
      """
      .stripMargin)
  abstract class OptionalFieldUnionType
      extends org.apache.avro.scala.UnionData
      with org.apache.avro.scala.Encodable
  
  abstract class ImmutableOptionalFieldUnionType extends OptionalFieldUnionType {
    def toMutable: MutableOptionalFieldUnionType
  }
  
  object OptionalFieldUnionType {
    def decode(decoder: org.apache.avro.io.Decoder): MutableOptionalFieldUnionType = {
      decoder.readIndex() match {
        case 0 => return MutableOptionalFieldUnionNull(data = {decoder.readNull(); null})
        case 1 => return MutableOptionalFieldUnionString(data = decoder.readString())
        case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
      }
    }
  }
  
  case class OptionalFieldUnionNull(data: Null) extends ImmutableOptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableOptionalFieldUnionNull =
      MutableOptionalFieldUnionNull(this.data)
  }
  
  case class OptionalFieldUnionString(data: String) extends ImmutableOptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeString(data)
    }
    override def hashCode(): Int = { return data.hashCode() }
    def toMutable: MutableOptionalFieldUnionString =
      MutableOptionalFieldUnionString(this.data)
  }
  
  abstract class MutableOptionalFieldUnionType
      extends OptionalFieldUnionType
      with org.apache.avro.scala.Decodable {
    def toImmutable: ImmutableOptionalFieldUnionType
  }
  
  object MutableOptionalFieldUnionType {
    def apply(data: Any): MutableOptionalFieldUnionType = data match {
      case null => MutableOptionalFieldUnionNull(null)
      case data: CharSequence => MutableOptionalFieldUnionString(data.toString)
      case _ => throw new java.io.IOException("Bad union data: " + data)
    }
  }
  
  case class MutableOptionalFieldUnionNull(var data: Null) extends MutableOptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(0)
      encoder.writeNull()
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = {decoder.readNull(); null}
    }
    def toImmutable: OptionalFieldUnionNull =
      OptionalFieldUnionNull(this.data)
  }
  
  case class MutableOptionalFieldUnionString(var data: String) extends MutableOptionalFieldUnionType {
    override def getData(): Any = { return data }
    override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
      encoder.writeIndex(1)
      encoder.writeString(data)
    }
    override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
      this.data = decoder.readString()
    }
    def toImmutable: OptionalFieldUnionString =
      OptionalFieldUnionString(this.data)
  }
}

}  // package org.apache.avro.scala.test.generated.scala
