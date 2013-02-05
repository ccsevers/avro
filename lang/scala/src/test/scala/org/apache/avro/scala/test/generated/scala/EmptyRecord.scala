// This file is machine-generated.

package org.apache.avro.scala.test.generated

import _root_.scala.collection.JavaConverters._

case class EmptyRecord(
    
) extends org.apache.avro.scala.Record {

  

  override def getSchema(): org.apache.avro.Schema = EmptyRecord.schema

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

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    
  }
}

object EmptyRecord extends org.apache.avro.scala.RecordType[EmptyRecord] {
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