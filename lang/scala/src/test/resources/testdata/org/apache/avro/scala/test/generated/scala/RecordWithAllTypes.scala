// This file is machine-generated.

package org.apache.avro.scala.test.generated.scala {

import scala.collection.JavaConverters._

class RecordWithAllTypes(
    val nullField : Null,
    val booleanField : Boolean,
    val intField : Int,
    val longField : Long,
    val floatField : Float,
    val doubleField : Double,
    val stringField : String,
    val bytesField : Seq[Byte],
    val fixedField : Seq[Byte],
    val intArrayField : Seq[Int],
    val intMapField : Map[String, Int],
    val intArrayArrayField : Seq[Seq[Int]],
    val intMapMapField : Map[String, Map[String, Int]]
) extends org.apache.avro.scala.ImmutableRecordBase {

  def copy(nullField : Null = this.nullField, booleanField : Boolean = this.booleanField, intField : Int = this.intField, longField : Long = this.longField, floatField : Float = this.floatField, doubleField : Double = this.doubleField, stringField : String = this.stringField, bytesField : Seq[Byte] = this.bytesField, fixedField : Seq[Byte] = this.fixedField, intArrayField : Seq[Int] = this.intArrayField, intMapField : Map[String, Int] = this.intMapField, intArrayArrayField : Seq[Seq[Int]] = this.intArrayArrayField, intMapMapField : Map[String, Map[String, Int]] = this.intMapMapField): RecordWithAllTypes =
    new RecordWithAllTypes(
      nullField = nullField,
      booleanField = booleanField,
      intField = intField,
      longField = longField,
      floatField = floatField,
      doubleField = doubleField,
      stringField = stringField,
      bytesField = bytesField,
      fixedField = fixedField,
      intArrayField = intArrayField,
      intMapField = intMapField,
      intArrayArrayField = intArrayArrayField,
      intMapMapField = intMapMapField
    )

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithAllTypes.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => null
      case 1 => booleanField.asInstanceOf[AnyRef]
      case 2 => intField.asInstanceOf[AnyRef]
      case 3 => longField.asInstanceOf[AnyRef]
      case 4 => floatField.asInstanceOf[AnyRef]
      case 5 => doubleField.asInstanceOf[AnyRef]
      case 6 => stringField
      case 7 => java.nio.ByteBuffer.wrap(bytesField.toArray[Byte])
      case 8 => new org.apache.avro.generic.GenericData.Fixed(getSchema(), fixedField.toArray[Byte])
      case 9 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intArrayField).asInstanceOf[AnyRef]
      case 10 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intMapField).asInstanceOf[AnyRef]
      case 11 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intArrayArrayField).asInstanceOf[AnyRef]
      case 12 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intMapMapField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeNull()
    encoder.writeBoolean(this.booleanField)
    encoder.writeInt(this.intField)
    encoder.writeLong(this.longField)
    encoder.writeFloat(this.floatField)
    encoder.writeDouble(this.doubleField)
    encoder.writeString(this.stringField)
    encoder.writeBytes(this.bytesField.asInstanceOf[Array[Byte]])
    encoder.writeBytes(this.fixedField.asInstanceOf[Array[Byte]])
    encoder.writeArrayStart()
    encoder.setItemCount(this.intArrayField.size)
    for (arrayItem <- this.intArrayField) {
      encoder.startItem()
      encoder.writeInt(arrayItem)
    }
    encoder.writeArrayEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.intMapField.size)
    for ((mapKey, mapValue) <- this.intMapField) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeInt(mapValue)
    }
    encoder.writeMapEnd()
    encoder.writeArrayStart()
    encoder.setItemCount(this.intArrayArrayField.size)
    for (arrayItem <- this.intArrayArrayField) {
      encoder.startItem()
      encoder.writeArrayStart()
      encoder.setItemCount(arrayItem.size)
      for (arrayItem <- arrayItem) {
        encoder.startItem()
        encoder.writeInt(arrayItem)
      }
      encoder.writeArrayEnd()
    }
    encoder.writeArrayEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.intMapMapField.size)
    for ((mapKey, mapValue) <- this.intMapMapField) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeMapStart()
      encoder.setItemCount(mapValue.size)
      for ((mapKey, mapValue) <- mapValue) {
        encoder.startItem()
        encoder.writeString(mapKey)
        encoder.writeInt(mapValue)
      }
      encoder.writeMapEnd()
    }
    encoder.writeMapEnd()
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithAllTypes] ||
    other.isInstanceOf[MutableRecordWithAllTypes]
}

class MutableRecordWithAllTypes(
    var nullField : Null = null,
    var booleanField : Boolean = false,
    var intField : Int = 0,
    var longField : Long = 0,
    var floatField : Float = 0,
    var doubleField : Double = 0,
    var stringField : String = null,
    var bytesField : scala.collection.mutable.Buffer[Byte] = scala.collection.mutable.Buffer[Byte](),
    var fixedField : Array[Byte] = new Array[Byte](16),
    var intArrayField : scala.collection.mutable.Buffer[Int] = scala.collection.mutable.ArrayBuffer[Int]().asInstanceOf[scala.collection.mutable.Buffer[Int]],
    var intMapField : scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String, Int]().asInstanceOf[scala.collection.mutable.Map[String, Int]],
    var intArrayArrayField : scala.collection.mutable.Buffer[scala.collection.mutable.Buffer[Int]] = scala.collection.mutable.ArrayBuffer[scala.collection.mutable.ArrayBuffer[Int]]().asInstanceOf[scala.collection.mutable.Buffer[scala.collection.mutable.Buffer[Int]]],
    var intMapMapField : scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]] = scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]().asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]]
) extends org.apache.avro.scala.MutableRecordBase[RecordWithAllTypes] {

  def this() = this(null, false, 0, 0, 0, 0, null, scala.collection.mutable.Buffer[Byte](), new Array[Byte](16), scala.collection.mutable.ArrayBuffer[Int]().asInstanceOf[scala.collection.mutable.Buffer[Int]], scala.collection.mutable.Map[String, Int]().asInstanceOf[scala.collection.mutable.Map[String, Int]], scala.collection.mutable.ArrayBuffer[scala.collection.mutable.ArrayBuffer[Int]]().asInstanceOf[scala.collection.mutable.Buffer[scala.collection.mutable.Buffer[Int]]], scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]().asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]])

  override def getSchema(): org.apache.avro.Schema = {
    return RecordWithAllTypes.schema
  }

  override def get(index: Int): AnyRef = {
    index match {
      case 0 => null
      case 1 => booleanField.asInstanceOf[AnyRef]
      case 2 => intField.asInstanceOf[AnyRef]
      case 3 => longField.asInstanceOf[AnyRef]
      case 4 => floatField.asInstanceOf[AnyRef]
      case 5 => doubleField.asInstanceOf[AnyRef]
      case 6 => stringField
      case 7 => java.nio.ByteBuffer.wrap(bytesField.toArray[Byte])
      case 8 => new org.apache.avro.generic.GenericData.Fixed(getSchema(), fixedField.toArray[Byte])
      case 9 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intArrayField).asInstanceOf[AnyRef]
      case 10 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intMapField).asInstanceOf[AnyRef]
      case 11 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intArrayArrayField).asInstanceOf[AnyRef]
      case 12 => org.apache.avro.scala.Conversions.scalaCollectionToJava(intMapMapField).asInstanceOf[AnyRef]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  override def put(index: Int, value: AnyRef): Unit = {
    index match {
      case 0 => ()
      case 1 => this.booleanField = value.asInstanceOf[Boolean]
      case 2 => this.intField = value.asInstanceOf[Int]
      case 3 => this.longField = value.asInstanceOf[Long]
      case 4 => this.floatField = value.asInstanceOf[Float]
      case 5 => this.doubleField = value.asInstanceOf[Double]
      case 6 => this.stringField = value.toString
      case 7 => this.bytesField = collection.mutable.Buffer[Byte]() ++ value.asInstanceOf[java.nio.ByteBuffer].array()
      case 8 => this.fixedField = value.asInstanceOf[org.apache.avro.generic.GenericData.Fixed].bytes()
      case 9 => this.intArrayField = org.apache.avro.scala.Conversions.javaCollectionToScala(value).asInstanceOf[scala.collection.mutable.Buffer[Int]]
      case 10 => this.intMapField = org.apache.avro.scala.Conversions.javaCollectionToScala(value).asInstanceOf[scala.collection.mutable.Map[String, Int]]
      case 11 => this.intArrayArrayField = org.apache.avro.scala.Conversions.javaCollectionToScala(value).asInstanceOf[scala.collection.mutable.Buffer[scala.collection.mutable.Buffer[Int]]]
      case 12 => this.intMapMapField = org.apache.avro.scala.Conversions.javaCollectionToScala(value).asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]]
      case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
    }
  }

  def build(): RecordWithAllTypes = {
    return new RecordWithAllTypes(
      nullField = this.nullField,
      booleanField = this.booleanField,
      intField = this.intField,
      longField = this.longField,
      floatField = this.floatField,
      doubleField = this.doubleField,
      stringField = this.stringField,
      bytesField = this.bytesField,
      fixedField = this.fixedField,
      intArrayField = this.intArrayField.toList,
      intMapField = this.intMapField.toMap,
      intArrayArrayField = this.intArrayArrayField.map { _.toList }.toList,
      intMapMapField = this.intMapMapField.mapValues { _.toMap }.toMap
    )
  }

  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
    encoder.writeNull()
    encoder.writeBoolean(this.booleanField)
    encoder.writeInt(this.intField)
    encoder.writeLong(this.longField)
    encoder.writeFloat(this.floatField)
    encoder.writeDouble(this.doubleField)
    encoder.writeString(this.stringField)
    encoder.writeBytes(this.bytesField.toArray)
    encoder.writeFixed(this.fixedField)
    encoder.writeArrayStart()
    encoder.setItemCount(this.intArrayField.size)
    for (arrayItem <- this.intArrayField) {
      encoder.startItem()
      encoder.writeInt(arrayItem)
    }
    encoder.writeArrayEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.intMapField.size)
    for ((mapKey, mapValue) <- this.intMapField) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeInt(mapValue)
    }
    encoder.writeMapEnd()
    encoder.writeArrayStart()
    encoder.setItemCount(this.intArrayArrayField.size)
    for (arrayItem <- this.intArrayArrayField) {
      encoder.startItem()
      encoder.writeArrayStart()
      encoder.setItemCount(arrayItem.size)
      for (arrayItem <- arrayItem) {
        encoder.startItem()
        encoder.writeInt(arrayItem)
      }
      encoder.writeArrayEnd()
    }
    encoder.writeArrayEnd()
    encoder.writeMapStart()
    encoder.setItemCount(this.intMapMapField.size)
    for ((mapKey, mapValue) <- this.intMapMapField) {
      encoder.startItem()
      encoder.writeString(mapKey)
      encoder.writeMapStart()
      encoder.setItemCount(mapValue.size)
      for ((mapKey, mapValue) <- mapValue) {
        encoder.startItem()
        encoder.writeString(mapKey)
        encoder.writeInt(mapValue)
      }
      encoder.writeMapEnd()
    }
    encoder.writeMapEnd()
  }

  def decode(decoder: org.apache.avro.io.Decoder): Unit = {
    {decoder.readNull(); null}
    this.booleanField = decoder.readBoolean()
    this.intField = decoder.readInt()
    this.longField = decoder.readLong()
    this.floatField = decoder.readFloat()
    this.doubleField = decoder.readDouble()
    this.stringField = decoder.readString()
    this.bytesField = decoder.readBytes(null).array.toBuffer
    this.fixedField = { val bytes = new Array[Byte](16); decoder.readFixed(bytes); bytes }
    this.intArrayField = {
      val array = scala.collection.mutable.ArrayBuffer[Int]()
      var blockSize: Long = decoder.readArrayStart()
      while(blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val arrayItem = (
              decoder.readInt())
          array.append(arrayItem)
        }
        blockSize = decoder.arrayNext()
      }
      array
    }
    this.intMapField = {
      val map = scala.collection.mutable.Map[String, Int]()
      var blockSize: Long = decoder.readMapStart()
      while (blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val key: String = decoder.readString()
          val value = (
            decoder.readInt())
          map += (key -> value)
        }
        blockSize = decoder.mapNext()
      }
    map
    }
    this.intArrayArrayField = {
      val array = scala.collection.mutable.ArrayBuffer[scala.collection.mutable.ArrayBuffer[Int]]()
      var blockSize: Long = decoder.readArrayStart()
      while(blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val arrayItem = (
              {
                val array = scala.collection.mutable.ArrayBuffer[Int]()
                var blockSize: Long = decoder.readArrayStart()
                while(blockSize != 0L) {
                  for (_ <- 0L until blockSize) {
                    val arrayItem = (
                        decoder.readInt())
                    array.append(arrayItem)
                  }
                  blockSize = decoder.arrayNext()
                }
                array
              })
          array.append(arrayItem)
        }
        blockSize = decoder.arrayNext()
      }
      array.asInstanceOf[scala.collection.mutable.Buffer[scala.collection.mutable.Buffer[Int]]]
    }
    this.intMapMapField = {
      val map = scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]()
      var blockSize: Long = decoder.readMapStart()
      while (blockSize != 0L) {
        for (_ <- 0L until blockSize) {
          val key: String = decoder.readString()
          val value = (
            {
              val map = scala.collection.mutable.Map[String, Int]()
              var blockSize: Long = decoder.readMapStart()
              while (blockSize != 0L) {
                for (_ <- 0L until blockSize) {
                  val key: String = decoder.readString()
                  val value = (
                    decoder.readInt())
                  map += (key -> value)
                }
                blockSize = decoder.mapNext()
              }
            map
            })
          map += (key -> value)
        }
        blockSize = decoder.mapNext()
      }
    map.asInstanceOf[scala.collection.mutable.Map[String, scala.collection.mutable.Map[String, Int]]]
    }
  }

  def canEqual(other: Any): Boolean =
    other.isInstanceOf[RecordWithAllTypes] ||
    other.isInstanceOf[MutableRecordWithAllTypes]

}

object RecordWithAllTypes {
  final val schema: org.apache.avro.Schema =
      new org.apache.avro.Schema.Parser().parse("""
          |{
          |  "type" : "record",
          |  "name" : "RecordWithAllTypes",
          |  "namespace" : "org.apache.avro.scala.test.generated",
          |  "fields" : [ {
          |    "name" : "null_field",
          |    "type" : "null"
          |  }, {
          |    "name" : "boolean_field",
          |    "type" : "boolean"
          |  }, {
          |    "name" : "int_field",
          |    "type" : "int"
          |  }, {
          |    "name" : "long_field",
          |    "type" : "long"
          |  }, {
          |    "name" : "float_field",
          |    "type" : "float"
          |  }, {
          |    "name" : "double_field",
          |    "type" : "double"
          |  }, {
          |    "name" : "string_field",
          |    "type" : "string"
          |  }, {
          |    "name" : "bytes_field",
          |    "type" : "bytes"
          |  }, {
          |    "name" : "fixed_field",
          |    "type" : {
          |      "type" : "fixed",
          |      "name" : "anon_fixed_16",
          |      "size" : 16
          |    }
          |  }, {
          |    "name" : "int_array_field",
          |    "type" : {
          |      "type" : "array",
          |      "items" : "int"
          |    }
          |  }, {
          |    "name" : "int_map_field",
          |    "type" : {
          |      "type" : "map",
          |      "values" : "int"
          |    }
          |  }, {
          |    "name" : "int_array_array_field",
          |    "type" : {
          |      "type" : "array",
          |      "items" : {
          |        "type" : "array",
          |        "items" : "int"
          |      }
          |    }
          |  }, {
          |    "name" : "int_map_map_field",
          |    "type" : {
          |      "type" : "map",
          |      "values" : {
          |        "type" : "map",
          |        "values" : "int"
          |      }
          |    }
          |  } ]
          |}
      """
      .stripMargin)
}

}  // package org.apache.avro.scala.test.generated.scala
