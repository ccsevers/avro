// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class Contained(
    val data : Int
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(data : Int = this.data): Contained =
    new Contained(
      data = data
    )

  override def getSchema(): org.apache.avro.Schema = {
    return Contained.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(data).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeInt(this.data)
  }

  def toMutable: MutableContained =
    new MutableContained(
      this.data
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Contained] ||
    other.isInstanceOf[MutableContained]
}

class MutableContained(
    var data : Int = 0
) extends org.apache.avro.scala.MutableRecordBase[Contained] {

  def this() = this(0)

  override def getSchema(): org.apache.avro.Schema = {
    return Contained.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(data).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.data = value.asInstanceOf[Int]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): Contained = {
    return new Contained(
      data = this.data
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeInt(this.data)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.data = decoder.readInt()
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Contained] ||
    other.isInstanceOf[MutableContained]

}

object Contained extends org.apache.avro.scala.RecordType {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "Contained",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "data",
          |    "type" : "int"
          |  } ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala


// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class Container(
    val contained : org.apache.avro.scala.test.generated.scala.Contained
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(contained : org.apache.avro.scala.test.generated.scala.Contained = this.contained): Container =
    new Container(
      contained = contained
    )

  override def getSchema(): org.apache.avro.Schema = {
    return Container.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(contained /* TODO Not Implemented */).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.contained.encode(encoder)
  }

  def toMutable: MutableContainer =
    new MutableContainer(
      this.contained.toMutable
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Container] ||
    other.isInstanceOf[MutableContainer]
}

class MutableContainer(
    var contained : org.apache.avro.scala.test.generated.scala.MutableContained = null
) extends org.apache.avro.scala.MutableRecordBase[Container] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return Container.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(contained /* TODO Not Implemented */).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.contained = value.asInstanceOf[org.apache.avro.scala.test.generated.scala.MutableContained]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): Container = {
    return new Container(
      contained = this.contained.build
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.contained.encode(encoder)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.contained = { val record = new org.apache.avro.scala.test.generated.scala.MutableContained(); record.decode(decoder); record }
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Container] ||
    other.isInstanceOf[MutableContainer]

}

object Container extends org.apache.avro.scala.RecordType {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "Container",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "contained",
          |    "type" : {
          |      "type" : "record",
          |      "name" : "Contained",
          |      "fields" : [ {
          |        "name" : "data",
          |        "type" : "int"
          |      } ]
          |    }
          |  } ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala
