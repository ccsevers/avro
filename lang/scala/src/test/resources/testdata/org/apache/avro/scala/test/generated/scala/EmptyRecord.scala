// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class EmptyRecord(
    
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(): EmptyRecord =
    new EmptyRecord(
      
    )

  override def getSchema(): org.apache.avro.Schema = {
    return EmptyRecord.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    
  }

  def toMutable: MutableEmptyRecord =
    new MutableEmptyRecord(
      
    )

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[EmptyRecord] ||
    other.isInstanceOf[MutableEmptyRecord]
}

class MutableEmptyRecord(
    
) extends org.apache.avro.scala.MutableRecordBase[EmptyRecord] {

  

  override def getSchema(): org.apache.avro.Schema = {
    return EmptyRecord.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): EmptyRecord = {
    return new EmptyRecord(
      
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[EmptyRecord] ||
    other.isInstanceOf[MutableEmptyRecord]

}

object EmptyRecord {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "EmptyRecord",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala
