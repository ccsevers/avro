/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.avro.scala

import java.io.InputStream
import java.io.ByteArrayOutputStream

import org.apache.avro.AvroRuntimeException
import org.apache.avro.io.Decoder
import org.apache.avro.io.Encoder
import org.apache.avro.io.DecoderFactory
import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.avro.specific.SpecificRecord

class NotImplemented(msg: String) extends AvroRuntimeException(msg)
class NotAvailable(msg: String) extends AvroRuntimeException(msg)

/** Interface for objects that can be encoded into an Avro encoder. */
trait Encodable {
  /**
   * Serializes this object into the specified Avro encoder.
   *
   * @param encoder the Avro encoder to serialize to.
   */
  def encode(encoder: Encoder): Unit
}

/** Interface for objects that can be decoded from an Avro decoder. */
trait Decodable {
  /**
   * Deserializes this object from the specified Avro decoder.
   *
   * @param decoder the Avro decoder to deserialize from.
   */
  def decode(decoder: Decoder): Unit
}

/** Abstract base class for all Scala records. */
abstract class RecordBase
  extends SpecificRecord
  with Encodable {

  override def put(index: Int, value: AnyRef): Unit = {
    throw new org.apache.avro.scala.NotAvailable("Immutable record cannot be modified")
  }

  override def toString(): String = {
    return Records.toJson(this)
  }
}

/** Abstract base class for mutable Scala records. */
abstract class MutableRecordBase
  extends RecordBase
  with Decodable {

}

/** Helpers to work with records. */
object Records {

  /**
   * Serializes the specified record into an array of bytes, using the SpecificDatumWriter.
   *
   * @param record The record to serialize.
   * @return The binary representation of the record as an array of bytes.
   */
  def serialize(record: SpecificRecord): Array[Byte] = {
    val output = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get.directBinaryEncoder(output, null)
    val writer = new SpecificDatumWriter[SpecificRecord](record.getSchema)
    writer.write(record, encoder)
    // encoder.flush() // not necessary with direct binary encoder
    return output.toByteArray
  }

  /**
   * Deserializes an array of bytes into the specified record.
   *
   * @param record Deserializes into this (mutable) record.
   * @param input Byte input stream to deserialize from.
   * @return The deserialized record.
   */
  def deserialize[T <: SpecificRecord](record: T, input: InputStream): T = {
    val decoder = DecoderFactory.get.directBinaryDecoder(input, null)
    val reader = new SpecificDatumReader[SpecificRecord](record.getSchema)
    reader.read(record, decoder)
    return record
  }

  /**
   * Serializes the specified record into a JSON string.
   *
   * @param record The record to serialize.
   * @return A string with the JSON representation of the specified record.
   */
  def toJson(record: SpecificRecord): String = {
    val output = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get.jsonEncoder(record.getSchema, output)
    val writer = new SpecificDatumWriter[SpecificRecord](record.getSchema)
    writer.write(record, encoder)
    encoder.flush()
    return new String(output.toByteArray)
  }

  /**
   * Encodes a record into an array of bytes.
   *
   * @param encodable Encodable record.
   * @return Binary encoded representation of the record, as an array of bytes.
   */
  def encode(encodable: Encodable): Array[Byte] = {
    val output = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get.directBinaryEncoder(output, null)
    encodable.encode(encoder)
    return output.toByteArray
  }

  /**
   * Decodes a record from a stream of bytes.
   *
   * @param decodable Mutable record to decode into.
   * @param input Stream of bytes.
   * @return The given mutable record, with the fields from the decoded binary stream merged in.
   */
  def decode[T <: Decodable](decodable: T, input: InputStream): T = {
    val decoder = DecoderFactory.get.directBinaryDecoder(input, null)
    decodable.decode(decoder)
    return decodable
  }
}

/** Base interface for union types (except the one compiled to Option[X]). */
trait UnionData
  extends Encodable {

  /** @return A generic reference to the object stored held by this union class. */
  def getData(): Any

  override def encode(encoder: Encoder)
}
