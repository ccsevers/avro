// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import _root_.scala.collection.JavaConverters._

class RecordWithTrait(
    
) extends org.apache.avro.scala.ImmutableRecordBase with org.apache.avro.scala.SampleTrait {

  def copy(): RecordWithTrait =
    new RecordWithTrait(
      
    )

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithTrait.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    
  }

  def toMutable: MutableRecordWithTrait =
    new MutableRecordWithTrait(
      
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithTrait] ||
    other.isInstanceOf[MutableRecordWithTrait]
}

class MutableRecordWithTrait(
    
) extends org.apache.avro.scala.MutableRecordBase[RecordWithTrait] {

  

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithTrait.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, javaValue: AnyRef): Unit = {
    val value = org.apache.avro.scala.Conversions.javaToScala(javaValue)
    index match {
      
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithTrait = {
    return new RecordWithTrait(
      
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithTrait] ||
    other.isInstanceOf[MutableRecordWithTrait]

}

object RecordWithTrait extends org.apache.avro.scala.RecordType[RecordWithTrait, MutableRecordWithTrait] {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordWithTrait",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ ],
          |  "scalaTrait" : "org.apache.avro.scala.SampleTrait"
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala
