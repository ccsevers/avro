// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class Animal(
    val species : String,
    val favoriteFood : String
) extends org.apache.avro.scala.ImmutableRecordBase {

  override def getSchema(): org.apache.avro.Schema = {
    return Animal.schema
  }

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => species
      case 1 => favoriteFood
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeString(this.species)
    encoder.writeString(this.favoriteFood)
  }

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

  override def get(index: Int): AnyRef = {println("%s GET %d" format (getClass.getSimpleName, index))
    index match {
      case 0 => species
      case 1 => favoriteFood
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {println("%s PUT %d %s" format (getClass.getSimpleName, index, value))
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

object Animal {
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