// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class Animal(
    val species : String,
    val favoriteFood : String
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(species : String = this.species, favoriteFood : String = this.favoriteFood): Animal =
    new Animal(
      species = species,
      favoriteFood = favoriteFood
    )

  override def getSchema(): org.apache.avro.Schema = {
    return Animal.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => org.apache.avro.scala.Conversions.scalaToJava(species).asInstanceOf[AnyRef]
      case 1 => org.apache.avro.scala.Conversions.scalaToJava(favoriteFood).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.species)
    encoder.writeString(this.favoriteFood)
  }

  def toMutable: MutableAnimal =
    new MutableAnimal(
      this.species,
      this.favoriteFood
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Animal] ||
    other.isInstanceOf[MutableAnimal]
}

class MutableAnimal(
    var species : String = null,
    var favoriteFood : String = null
) extends org.apache.avro.scala.MutableRecordBase[Animal] {

  def this() = this(null, null)

  override def getSchema(): org.apache.avro.Schema = {
    return Animal.schema
  }

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

  def build(): Animal = {
    return new Animal(
      species = this.species,
      favoriteFood = this.favoriteFood
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.species)
    encoder.writeString(this.favoriteFood)
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    this.species = decoder.readString()
    this.favoriteFood = decoder.readString()
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Animal] ||
    other.isInstanceOf[MutableAnimal]

}

object Animal extends org.apache.avro.scala.RecordType {
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

}  // package org.apache.avro.scala.test.generated.scala
