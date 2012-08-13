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

import java.io.ByteArrayOutputStream
import java.io.InputStream

import org.apache.commons.io.FileUtils
import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.collection.JavaConverters.collectionAsScalaIterableConverter
import scala.collection.mutable

import org.apache.avro.io.Decoder
import org.apache.avro.io.DecoderFactory
import org.apache.avro.io.Encoder
import org.apache.avro.io.EncoderFactory
import org.apache.avro.scala.Text.implicitCamelCaseableFromString
import org.apache.avro.scala.Text.implicitFormatableFromString
import org.apache.avro.scala.Text.implicitIndentableFromString
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.specific.SpecificRecord
import org.apache.avro.{Protocol, AvroRuntimeException, Schema}
import org.codehaus.jackson.JsonNode
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.{FilenameFilter, File, ByteArrayOutputStream, InputStream}
import org.apache.commons.io.FileUtils
import org.codehaus.jackson.io.JsonStringEncoder

/** Flag to tag mutable vs immutable things. */
trait MutableFlag
case object Mutable extends MutableFlag
case object Immutable extends MutableFlag

/** Flag for concrete vs abstract, abstract is a synonym for interface. */
trait ConcreteFlag
case object Concrete extends ConcreteFlag
case object Abstract extends ConcreteFlag

/**
 * Compiles one Avro record schema into a collection of Scala classes.
 *
 * @param schema schema of the Avro record to compile into Scala.
 */
class Compiler(val schema: Schema) {
  val recordPackage = "%s.scala".format(schema.getNamespace)

  /** Immutable record class name (unqualified). */
  val recordClassName = schema.getName.toCamelCase
  val recordFQClassName = "%s.%s".format(recordPackage, recordClassName)

  /** Mutable record class name (unqualified). */
  val mutableRecordClassName = "Mutable%s".format(recordClassName)
  val mutableRecordFQClassName = "%s.%s".format(recordPackage, mutableRecordClassName)

  /** Compile immutable record class definition. */
  def compileRecord(): String = {
    /**
     * Compiles a record field schema into a Scala class constructor parameter.
     *
     * @param field Avro record field
     * @return Scala source code for the given field.
     */
    def compileField(field: Schema.Field): String = {
      var decl = "val %(fieldName) : %(fieldType)".xformat(
        'fieldName -> field.name.toCamelCase,
        'fieldType -> TypeMap(field.schema, Immutable, Abstract, Some(schema, field)))
      if (field.defaultValue != null) {
        decl += " = " + compileDefaultValue(field.schema, field.defaultValue, Immutable)
      }
      return decl
    }

    return """
      |class %(className)(
      |%(constructorParams)
      |) extends org.apache.avro.scala.ImmutableRecordBase {
      |
      |%(getSchema)
      |
      |%(get)
      |
      |%(encode)
      |
      |%(canEqual)
      |}"""
      .stripMargin
      .trim
      .xformat(
        'className -> recordClassName,
        'constructorParams -> schema.getFields.asScala
          .map(compileField(_))
          .mkString(",\n")
          .indent(4),
        'get -> compileRecordGet().indent(2),
        'getSchema -> compileRecordGetSchema().indent(2),
        'encode -> compileRecordEncode().indent(2),
        'canEqual -> compileCanEqual().indent(2))
  }

  /** Compile mutable record class definition. */
  def compileMutableRecord(): String = {
    def compileMutableField(field: Schema.Field): String = {
      return "var %(fieldName) : %(fieldType) = %(default)".xformat(
        'fieldName -> field.name.toCamelCase,
        'fieldType -> TypeMap(field.schema, Mutable, Abstract, Some(schema, field)),
        'default -> compileMutableRecordFieldDefaultValue(field))
    }
    
    val recordFields = schema.getFields.asScala.map(compileMutableField(_))

    return """
      |class %(className)(
      |%(recordFields)
      |) extends org.apache.avro.scala.MutableRecordBase[%(immutableRecordClassName)] {
      |
      |%(noArgConstructor)
      |
      |%(getSchema)
      |
      |%(get)
      |
      |%(put)
      |
      |%(build)
      |
      |%(encode)
      |
      |%(decode)
      |
      |%(canEqual)
      |
      |}"""
      .stripMargin
      .trim
      .xformat(
        'className -> mutableRecordClassName,
        'immutableRecordClassName -> recordClassName,
        'recordFields -> recordFields
          .mkString(",\n")
          .indent(4),
      'noArgConstructor -> compileMutableRecordNoArgsConstructor(schema.getFields.asScala).indent(2),
      'getSchema -> compileRecordGetSchema().indent(2),
        'get -> compileRecordGet().indent(2),
        'put -> compileMutableRecordPut().indent(2),
        'encode -> compileMutableRecordEncode().indent(2),
        'decode -> compileRecordDecode().indent(2),
        'build -> compileRecordBuild().indent(2),
        'canEqual -> compileCanEqual().indent(2))
  }

  def compileObject(): String = {
    return """
      |object %(objectName) {
      |%(fields)
      |}"""
      .stripMargin
      .trim
      .xformat(
        'objectName -> recordClassName,
        'fields -> compileObjectFields().mkString("\n").indent(2))
  }

  def compileEnumDef(): String = {
    return """
        |// This file is machine-generated.
        |
        |package %(package)
        |
        |object %(enumName) extends Enumeration {
        |  val %(symbols) = Value
        |}"""
      .stripMargin
      .trim
      .xformat(
        'package -> recordPackage,
        'enumName -> schema.getName.toCamelCase,
        'symbols -> schema.getEnumSymbols.asScala.mkString(", "))
  }

  /**
   * Compiles this Avro record into a collection of Scala classes.
   * @return Scala source code fitting in an entire .scala file.
   */
  def compile(): String = {
    if (schema.getType == Schema.Type.ENUM)
      return compileEnumDef()

    require(schema.getType == Schema.Type.RECORD, "Unhandled schema type: " + schema.getType)

    return """
      |// This file is machine-generated.
      |
      |package %(package) {
      |
      |import scala.collection.JavaConverters._
      |
      |%(immutableRecordClassDef)
      |
      |%(mutableRecordClassDef)
      |
      |%(objectDef)
      |
      |}  // package %(package)
      """
      .stripMargin
      .trim
      .xformat(
        'package -> recordPackage,
        'immutableRecordClassDef -> compileRecord(),
        'mutableRecordClassDef -> compileMutableRecord(),
        'objectDef -> compileObject()) + "\n"
  }

  /**
   * Compiles an Avro default value into a Scala default value.
   *
   * @param schema Avro schema the default value is for.
   * @param default JSON object describing the default value.
   * @return Scala source representing the default value.
   */
  def compileDefaultValue(schema: Schema, default: JsonNode, mutableFlag: MutableFlag): String = {
    assert(default != null)
    schema.getType match {
      case Schema.Type.NULL => {
        assert(default.getTextValue == null, default.getTextValue)
        return "null"
      }
      case Schema.Type.BOOLEAN => return default.getBooleanValue.toString
      case Schema.Type.INT => return default.getIntValue.toString
      case Schema.Type.LONG => return default.getLongValue.toString
      case Schema.Type.FLOAT
        | Schema.Type.DOUBLE => return default.getDoubleValue.toString
      case Schema.Type.ARRAY => {
        val values = (0 until default.size).map(default.get(_).toString)
        return "List(%s)".format(values.mkString(", "))
      }
      case Schema.Type.MAP => {
        val values = default.getFields.asScala.map { entry =>
          val key = new String(JsonStringEncoder.getInstance.quoteAsString(entry.getKey))
          assert(entry.getValue.isValueNode, "only JSON value nodes are currently supported")
          "\"%s\" -> %s".format(key, entry.getValue.toString)
        }
        return "%(mapConstructor)(%(defaultValue))".xformat(
          'mapConstructor -> TypeMap(schema, mutableFlag, Concrete),
          'defaultValue -> values.mkString(", "))
      }
      case Schema.Type.STRING => return default.toString
      case Schema.Type.ENUM =>
        return "%s.%s".format(TypeMap(schema, Immutable, Concrete), default.getTextValue)
      case Schema.Type.UNION => {
        // Check if null should be converted to None
        val types: Iterable[Schema] = schema.getTypes.asScala
        val isOption = ((types.size == 2) && types.exists(sc => sc.getType == Schema.Type.NULL))
        if (isOption) {
          val elementType = types.filter(_.getType != Schema.Type.NULL).head
          if (types.iterator.next.getType == Schema.Type.NULL) return "None"
          else return "Some(%s)".format(compileDefaultValue(elementType, default, mutableFlag))
        }
        return compileDefaultValue(schema.getTypes.get(0), default, mutableFlag)
      }
    }
    throw new RuntimeException("Unhandled default field value: " + default)
  }

  /** Compile default value for a field in a mutable record. */
  def compileMutableRecordFieldDefaultValue(field: Schema.Field): String = {
    if (field.defaultValue == null) {
      Compiler.typeNewZeroValue(field.schema)
    } else {
      compileDefaultValue(field.schema, field.defaultValue, Mutable)
    }
  }

  def compileRecordGetSchema(): String = {
    return """
       |override def getSchema(): org.apache.avro.Schema = {
       |  return %(objectName).schema
       |}
       |"""
      .stripMargin
      .trim
      .xformat('objectName -> recordClassName)
  }

  def compileMutableRecordNoArgsConstructor(fields: Iterable[Schema.Field]): String = {
    if (fields.isEmpty)  {
      ""
    } else {
      val fieldDefaultValues = fields.map(compileMutableRecordFieldDefaultValue)
      "def this() = this(%s)".format(fieldDefaultValues.mkString(", "))
    }
  }

  def compileRecordGet(): String = {
    def MakeFieldGetterCase(field: Schema.Field): String = {
      val converter = (field.schema.getType match {
        case Schema.Type.NULL => "null"
        case Schema.Type.INT
          | Schema.Type.LONG
          | Schema.Type.FLOAT
          | Schema.Type.DOUBLE
          | Schema.Type.BOOLEAN => {
            "%(field).asInstanceOf[AnyRef]"
          }
        case Schema.Type.ARRAY
          | Schema.Type.MAP => {
            // FIXME(taton) How convert nested composite types!
            "org.apache.avro.scala.Conversions.scalaCollectionToJava(%(field)).asInstanceOf[AnyRef]"
          }
        case Schema.Type.STRING => "%(field)"
        case Schema.Type.BYTES =>
          "java.nio.ByteBuffer.wrap(%(field).toArray[Byte])"
        case Schema.Type.FIXED =>
          "new org.apache.avro.generic.GenericData.Fixed(getSchema(), %(field).toArray[Byte])"
        case Schema.Type.UNION => {
          TypeMap.unionAsOption(field.schema) match {
            case None => "%(field).getData.asInstanceOf[AnyRef]"
            case Some(_) => "%(field).getOrElse(null).asInstanceOf[AnyRef]"
              // This should be .orNull.asInstanceOf[AnyRef] but Scala fails to infer type properly
          }
        }
        case Schema.Type.RECORD => "%(field) // TODO Not Implemented"
        case Schema.Type.ENUM => "%(field) // TODO Not Implemented"
      }).xformat(
      'field -> field.name.toCamelCase,
      'type -> TypeMap(field.schema, Immutable, Abstract, Some(schema, field))
      )
      return "case %d => %s".format(field.pos, converter)
    }
    val fields = schema.getFields.asScala
        .map(MakeFieldGetterCase(_))
    return """
       |override def get(index: Int): AnyRef = {
       |  index match {
       |%(fields)
       |    case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
       |  }
       |}
       |"""
      .stripMargin
      .trim
      .xformat('fields -> fields.mkString("\n").indent(4))
  }

  def compileMutableRecordPut(): String = {
    def MakeFieldPutValue(field: Schema.Field): String = {
      (field.schema.getType match {
        case Schema.Type.STRING => "value.toString"
        case Schema.Type.BYTES => "collection.mutable.Buffer[Byte]() ++ value.asInstanceOf[java.nio.ByteBuffer].array()"
        case Schema.Type.FIXED => "value.asInstanceOf[org.apache.avro.generic.GenericData.Fixed].bytes()"
        case Schema.Type.ARRAY
          | Schema.Type.MAP => {
          "org.apache.avro.scala.Conversions.javaCollectionToScala(value).asInstanceOf[%(type)]"
        }
        case _ => "value.asInstanceOf[%(type)]"
      }).xformat('type -> TypeMap(field.schema, Mutable, Abstract, Some(schema, field)))
    }
    def MakeFieldPutCase(field: Schema.Field): String = {
      if (field.schema.getType == Schema.Type.NULL) {
        return "case %d => ()".format(field.pos)
      }
      return "case %d => this.%s = %s".format(
        field.pos,
        field.name.toCamelCase,
        MakeFieldPutValue(field))
    }
    val fields = schema.getFields.asScala.map(MakeFieldPutCase(_))
    return """
       |override def put(index: Int, value: AnyRef): Unit = {
       |  index match {
       |%(fields)
       |    case _ => throw new org.apache.avro.AvroRuntimeException("Bad index: " + index)
       |  }
       |}"""
      .stripMargin
      .trim
      .xformat('fields -> fields.mkString("\n").indent(4))
  }

  // TODO(taton) Setter following builder pattern

  def compileRecordEncode(): String = {
    def makeFieldEncoder(field: Schema.Field): String = {
      return RecordEncoder(field.schema)
        .xformat('value -> "this.%s".format(field.name.toCamelCase))
    }

    val encoders = schema.getFields.asScala
      .map(makeFieldEncoder(_))
      .map(_.xformat('encoder -> "encoder"))

    return """
        |override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
        |%(encoders)
        |}"""
      .stripMargin
      .trim
      .xformat('encoders -> encoders.mkString("\n").indent(2))
  }

  def compileMutableRecordEncode(): String = {
    def makeFieldEncoder(field: Schema.Field): String = {
      return MutableRecordEncoder(field.schema)
        .xformat('value -> "this.%s".format(field.name.toCamelCase))
    }

    val encoders = schema.getFields.asScala
      .map(makeFieldEncoder(_))
      .map(_.xformat('encoder -> "encoder"))

    return """
        |override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
        |%(encoders)
        |}"""
      .stripMargin
      .trim
      .xformat('encoders -> encoders.mkString("\n").indent(2))
  }

  def compileRecordDecode(): String = {
    def makeFieldDecoder(field: Schema.Field): String = {
      val decoder = DatumDecoder(field.schema, field = Some((schema, field)))
      if (field.schema.getType == Schema.Type.NULL) {
        return decoder
      }
      return "this.%s = %s".format(field.name.toCamelCase, decoder)
    }
    val decoders = schema.getFields.asScala
      .map(makeFieldDecoder(_))
      .map(_.xformat('decoder -> "decoder"))

    return """
        |def decode(decoder: org.apache.avro.io.Decoder): Unit = {
        |%(decoders)
        |}"""
      .stripMargin
      .trim
      .xformat('decoders -> decoders.mkString("\n").indent(2))
  }

  /** Converts mutable form into immutable form. */
  def mutableToImmutable(schema: Schema): String = {
    return schema.getType match {
      case Schema.Type.ARRAY => {
        schema.getElementType.getType match {
          case Schema.Type.ARRAY | Schema.Type.MAP | Schema.Type.RECORD =>
            "%(value).map { %(nested) }.toList"
              .xformat('nested -> mutableToImmutable(schema.getElementType).xformat('value -> "_"))
          case _ => "%(value).toList"
        }
      }
      case Schema.Type.MAP => {
        schema.getValueType.getType match {
          case Schema.Type.ARRAY | Schema.Type.MAP | Schema.Type.RECORD =>
            "%(value).mapValues { %(nested) }.toMap"
              .xformat('nested -> mutableToImmutable(schema.getValueType).xformat('value -> "_"))
          case _ => "%(value).toMap"
        }
      }
      case Schema.Type.RECORD => "%(value).build"
      case _ => "%(value)"
    }
  }

  def compileRecordBuild(): String = {
    def ConvertField(field: Schema.Field): String = {
      return mutableToImmutable(field.schema).xformat('value -> ("this." + field.name.toCamelCase))
    }
    val fields = schema.getFields.asScala
      .map { field => "%s = %s".format(field.name.toCamelCase, ConvertField(field)) }
    return """
        |def build(): %(className) = {
        |  return new %(className)(
        |%(fields)
        |  )
        |}"""
      .stripMargin
      .trim
      .xformat(
        'className -> recordClassName,
        'fields -> fields.mkString(",\n").indent(4))
  }

  def compileCanEqual(): String = {
    """
      |def canEqual(other: Any): Boolean =
      |  other.isInstanceOf[%(recordClassName)] ||
      |  other.isInstanceOf[%(mutableRecordClassName)]"""
      .stripMargin.trim.xformat(
        'recordClassName -> recordClassName,
        'mutableRecordClassName -> mutableRecordClassName)
  }


  private final val TripleQuotes = "\"" * 3

  /**
   * Generates the static fields for this Avro record.
   */
  def compileObjectFields(): Iterable[String] = {
    val schemaSource = {
      val jsonStr = schema.toString(true) // pretty JSON text
      val formatted = Text.prefixLines(jsonStr, "        |")
      """|final val schema: org.apache.avro.Schema =
         |    new org.apache.avro.Schema.Parser().parse(%(triquotes)
         |%(schema)
         |    %(triquotes)
         |    .stripMargin)
         """
        .stripMargin
        .trim
        .xformat(
          'schema -> formatted,
          'triquotes -> TripleQuotes)
    }

    var unions = mutable.ArrayBuffer[String]()
    for (field <- schema.getFields.asScala) {
      if (field.schema.getType == Schema.Type.UNION) {
        val baseUnionClassName = "%sUnionType".format(field.name.toUpperCamelCase)

        def MakeSchemaUnionClassName(schema: Schema): String = {
          return schema.getType match {
            case Schema.Type.NULL => "Null"
            case Schema.Type.BOOLEAN => "Boolean"
            case Schema.Type.INT => "Int"
            case Schema.Type.LONG => "Long"
            case Schema.Type.FLOAT => "Float"
            case Schema.Type.DOUBLE => "Double"
            case Schema.Type.ENUM => schema.getName
            case Schema.Type.STRING => "String"
            case Schema.Type.FIXED => "Fixed"
            case Schema.Type.BYTES => "Bytes"
            case Schema.Type.ARRAY => "Array%s".format(MakeSchemaUnionClassName(schema.getElementType))
            case Schema.Type.MAP => "Map%s".format(MakeSchemaUnionClassName(schema.getValueType))
            case Schema.Type.RECORD => schema.getName
            case Schema.Type.UNION => schema.getName
          }
        }

        def MakeUnionCaseClass(schema: Schema, index: Int): String = {
          return """
              |case class %(name)(data: %(type)) extends %(base) {
              |  override def getData(): Any = { return data }
              |  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
              |    encoder.writeIndex(%(index))
              |%(encoder)
              |  }
              |  override def hashCode(): Int = { return data.hashCode() }
              |}
              """
            .stripMargin
            .trim
            .xformat(
              'base -> baseUnionClassName,
              'name -> "%sUnion%s".format(field.name.toUpperCamelCase, MakeSchemaUnionClassName(schema)),
              'type -> TypeMap(schema, Immutable, Abstract),
              'index -> index,
              'encoder -> RecordEncoder(schema)
                  .xformat('value -> "data", 'encoder -> "encoder")
                  .indent(4))
        }

        def MakeMutableUnionCaseClass(schema: Schema, index: Int): String = {
          return """
              |case class Mutable%(name)(var data: %(type)) extends Mutable%(base) {
              |  override def getData(): Any = { return data }
              |  override def encode(encoder: org.apache.avro.io.Encoder): Unit = {
              |    encoder.writeIndex(%(index))
              |%(encoder)
              |  }
              |  override def decode(decoder: org.apache.avro.io.Decoder): Unit = {
              |%(decoder)
              |  }
              |}
              """
            .stripMargin
            .trim
            .xformat(
              'base -> baseUnionClassName,
              'name -> "%sUnion%s".format(field.name.toUpperCamelCase, MakeSchemaUnionClassName(schema)),
              'type -> TypeMap(schema, Mutable, Abstract),
              'index -> index,
              'encoder -> RecordEncoder(schema)
                  .xformat('value -> "data", 'encoder -> "encoder")
                  .indent(4),
              'decoder -> "this.data = %s"
                  .format(DatumDecoder(schema).xformat('decoder -> "decoder"))
                  .indent(4))
        }

        val decoderCases = field.schema.getTypes.asScala.zipWithIndex
          .map { case (schema, index) =>
            val nestedDecoder = "return %(caseClass)(data = %(value))".xformat(
                'caseClass -> "Mutable%sUnion%s"
                    .format(field.name.toUpperCamelCase, MakeSchemaUnionClassName(schema)),
                'value -> DatumDecoder(schema).xformat('decoder -> "decoder"))

            "case %(index) => %(nestedDecoder)"
                .xformat('index -> index, 'nestedDecoder -> nestedDecoder)
            }

        unions += """
          |abstract class %(name)
          |    extends org.apache.avro.scala.UnionData
          |    with org.apache.avro.scala.Encodable
          |
          |object %(name) {
          |  def decode(decoder: org.apache.avro.io.Decoder): Mutable%(name) = {
          |    decoder.readIndex() match {
          |%(decoderCases)
          |      case badIndex => throw new java.io.IOException("Bad union index: " + badIndex)
          |    }
          |  }
          |}
          |
          |%(caseClasses)
          |
          |abstract class Mutable%(name)
          |    extends %(name)
          |    with org.apache.avro.scala.Decodable
          |
          |%(mutableCaseClasses)
          """
          .stripMargin
          .trim
          .xformat(
            'name -> baseUnionClassName,
            'caseClasses -> field.schema.getTypes.asScala.zipWithIndex
                .map { case (schema, index) => MakeUnionCaseClass(schema, index) }
                .mkString("\n\n"),
            'decoderCases -> decoderCases
                .mkString("\n").indent(6),
            'mutableCaseClasses -> field.schema.getTypes.asScala.zipWithIndex
                .map { case (schema, index) => MakeMutableUnionCaseClass(schema, index) }
                .mkString("\n\n"))
      }
    }
    return List(schemaSource) ++ unions
  }
}

// ------------------------------------------------------------------------------------------------

/** Compiler companion object. */
object Compiler {

  /**
   * How to initialize a field with either its default value, or a non-initialized value.
   *
   * This is used to initialize fields a mutable record.
   *
   * @param schema Schema to compile the initializing expression for.
   * @return Scala expression for the default or non-initialized value of the specified schema.
   */
  def typeNewZeroValue(schema: Schema): String = {
    schema.getType match {
      case Schema.Type.NULL => return "null"
      case Schema.Type.BOOLEAN => return "false"
      case Schema.Type.INT => return "0"
      case Schema.Type.LONG => return "0"
      case Schema.Type.FLOAT => return "0"
      case Schema.Type.DOUBLE => return "0"
      case Schema.Type.STRING => return "null"
      case Schema.Type.FIXED => return "new %s(%d)"
        .format(TypeMap(schema, Mutable, Concrete), schema.getFixedSize)
      case Schema.Type.BYTES => return "%s()"
        .format(TypeMap(schema, Mutable, Concrete))
      case Schema.Type.ENUM => return "null"
      case Schema.Type.ARRAY => return "%s().asInstanceOf[%s]"
        .format(TypeMap(schema, Mutable, Concrete), TypeMap(schema, Mutable, Abstract))
      case Schema.Type.MAP => return "%s().asInstanceOf[%s]"
        .format( TypeMap(schema, Mutable, Concrete), TypeMap(schema, Mutable, Abstract))
      case Schema.Type.RECORD => return "null"
      case Schema.Type.UNION => return "null"
    }
    throw new RuntimeException("Unhandled zero value type: " + schema)
  }

  def compileProtocol(jsonProtocolFile: File): Iterable[(String, String)] = {
    val proto = Protocol.parse(jsonProtocolFile)
    proto.getTypes.asScala.map { schema =>
      (schema.getName, (new Compiler(schema)).compile())
    }
  }

  def compileSchema(jsonSchemaFile: File): String = {
    val schemaJsonSource = FileUtils.readFileToString(jsonSchemaFile)
    compileSchema(schemaJsonSource)
  }

  def compileSchema(schemaJsonSource: String): String = {
    val schema = new Schema.Parser().parse(schemaJsonSource)
    schema.getType match {
      case Schema.Type.UNION => {
        schema.getTypes.asScala.map { schemaType =>
          val compiler = new Compiler(schemaType)
          compiler.compile()
        }.mkString("\n\n")
      }
      case Schema.Type.RECORD | Schema.Type.ENUM => {
        val compiler = new Compiler(schema)
        compiler.compile()
      }
      case _ => throw new Exception("Unhandled top-level schema: " + schema)
    }
  }
}

object CompilerApp extends scala.App {
  if (args.size < 3) {
    printHelp()
    sys.exit(1)
  }

  abstract class InputType(val extension: String)
  case object SchemaInput extends InputType("avsc")
  case object ProtocolInput extends InputType("avpr")
  val inputType = args(0) match {
    case "schema" => SchemaInput
    case "protocol" => ProtocolInput
    case _ => {
      println("Must specify either 'schema' or 'protocol'")
      printHelp()
      sys.exit(1)
    }
  }

  val fileArgs = args.drop(1).map { path =>
    val f = new File(path)
    if (!f.exists) {
      println("CompilerApp: %s: No such file or directory" format f.getPath)
      sys.exit(2)
    }
  }

  val outDir = new File(args(1))
  require(outDir.isDirectory && outDir.exists, outDir)
  val inPaths = args.drop(2)
  val inObjs = inPaths.map(new File(_))
  val inFiles = inObjs.filter(_.isFile)
  val inDirs = inObjs.filter(_.isDirectory)

  compileAndWrite(outDir, inFiles, inputType)
  for (inDir <- inDirs) {
    println(inDir + ":")
    compileAndWrite(outDir, inDir, inputType)
  }

  def compileAndWrite(outDir: File, inDir: File, inputType: InputType) {
    require(inDir.exists, inDir)
    object filter extends FilenameFilter {
      override def accept(dir: File, name: String): Boolean =
        name.endsWith(".%s" format inputType.extension)
    }
    val inFiles = inDir.listFiles(filter)
    compileAndWrite(outDir, inFiles, inputType)
  }

  def compileAndWrite(outDir: File, inFiles: Iterable[File], inputType: InputType) {
    for (inFile <- inFiles) {
      val name = inFile.getName.stripSuffix(".%s" format inputType.extension)
      inputType match {
        case SchemaInput => {
          val scalaFile = new File(outDir, "%s.scala".format(name.toUpperCamelCase))
          println("%s -> %s".format(inFile.getName, scalaFile.getName))
          val scalaSource = Compiler.compileSchema(inFile)
          require(scalaFile.getParentFile.exists || scalaFile.getParentFile.mkdirs())
          FileUtils.writeStringToFile(scalaFile, scalaSource)
        }
        case ProtocolInput => {
          println("%s -> types:".format(inFile.getName))
          Compiler.compileProtocol(inFile).foreach {
            case (name, scalaSource) => {
              val scalaFile = new File(outDir, "%s.scala".format(name.toUpperCamelCase))
              println("   - %s".format(scalaFile.getName))
              require(scalaFile.getParentFile.exists || scalaFile.getParentFile.mkdirs())
              FileUtils.writeStringToFile(scalaFile, scalaSource)
            }
          }
        }
      }
    }
  }

  def printHelp() {
    println("Usage: CompilerApp <schema|protocol> OUTDIR PATH...")
  }
}
