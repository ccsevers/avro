// This file is machine-generated.

package org.apache.avro.scala.test.generated

import _root_.scala.collection.JavaConverters._

case class Animal(
    var species : String,
    var favoriteFood : String
) extends org.apache.avro.scala.Record {

  def this() = this(null, null)

  override def getSchema(): org.apache.avro.Schema = Animal.schema

  override def get(index: Int): AnyRef = {
    index match {
        case 0 => org.apache.avro.scala.Conversions.scalaToJava(species).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(favoriteFood).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      case 0 => this.species = value.toString
      case 1 => this.favoriteFood = value.toString
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.species)
    encoder.writeString(this.favoriteFood)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.species = decoder.readString()
    this.favoriteFood = decoder.readString()
  }
}

object Animal extends org.apache.avro.scala.RecordType[Animal] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "Animal",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "species",
          |    "type" : "string"
          |  }, {
          |    "name" : "favoriteFood",
          |    "type" : "string"
          |  } ]
          |}
      """
      .stripMargin)
}