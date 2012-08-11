// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class Contained(
    val data: Int
) extends org.apache.avro.scala.ImmutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return Contained.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => data.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeInt(this.data)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Contained] ||
    other.isInstanceOf[MutableContained]
}

class MutableContained(
    var data: Int = 0
) extends org.apache.avro.scala.MutableRecordBase[Contained] {

  def this() = this(0)

  override def getSchema(): org.apache.avro.Schema = {
    return Contained.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => data.asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {println("%s PUT %d %s" format (getClass.getSimpleName, index, value))
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

object Contained {
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

import scala.collection.JavaConverters._

class Container(
    val contained: org.apache.avro.scala.test.generated.scala.Contained
) extends org.apache.avro.scala.ImmutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return Container.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => contained // TODO Not Implemented
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    this.contained.encode(encoder)
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Container] ||
    other.isInstanceOf[MutableContainer]
}

class MutableContainer(
    var contained: org.apache.avro.scala.test.generated.scala.MutableContained = null
) extends org.apache.avro.scala.MutableRecordBase[Container] {

  def this() = this(null)

  override def getSchema(): org.apache.avro.Schema = {
    return Container.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => contained // TODO Not Implemented
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {println("%s PUT %d %s" format (getClass.getSimpleName, index, value))
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

object Container {
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