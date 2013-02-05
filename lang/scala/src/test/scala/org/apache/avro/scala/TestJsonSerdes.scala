package org.apache.avro.scala

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import scala.collection.mutable

@RunWith(classOf[JUnitRunner])
class TestJsonSerdes extends FunSuite {
  import org.apache.avro.scala.test.generated._

  def jsonSerdesIsIdentity[R <: Record](label: String, record: => R) {
    test(label) {
      val record2 = Records.fromJson[R](record.getSchema, Records.toJson(record))
      assert(record === record2)
      assert(record.getClass === record2.getClass)
    }
  }

  jsonSerdesIsIdentity[EmptyRecord]("empty record", EmptyRecord())

  jsonSerdesIsIdentity[RecordWithString]("record with string", RecordWithString("a"))

  jsonSerdesIsIdentity[RecordWithAllTypes]("RecordWithAllTypes", RecordWithAllTypes(
    nullField  = null,
    booleanField = false,
    intField = 1,
    longField = 1L,
    floatField = 1.0f,
    doubleField = 1.0,
    stringField = "string",
    bytesField = List(1, 2, 3),
    fixedField = Seq.range(0, 16),
    intArrayField = List(1, 2, 3),
    intMapField = Map("x" -> 1, "y" -> 2),
    intArrayArrayField = List(List(1, 2), List(3, 4)),
    intMapMapField = Map("a" -> Map("x" -> 1), "b" -> Map("y" -> 2))
  ))

  jsonSerdesIsIdentity[Container]("Container", Container(Contained(1)))

  jsonSerdesIsIdentity[UnionContainer]("UnionContainer None/SomeString", UnionContainer(None, UnionContainer.ContainedOrStringUnionUnionString("a")))
  jsonSerdesIsIdentity[UnionContainer]("UnionContainer None/SomeRecord", UnionContainer(None, UnionContainer.ContainedOrStringUnionUnionUnionContained((UnionContained(2, Map("c" -> "d"))))))
  jsonSerdesIsIdentity[UnionContainer]("UnionContainer Some/SomeRecord", UnionContainer(Some(UnionContained(1, Map("a" -> "b"))), UnionContainer.ContainedOrStringUnionUnionUnionContained((UnionContained(2, Map("c" -> "d"))))))
  jsonSerdesIsIdentity[UnionContainer]("UnionContainer Some/SomeString", UnionContainer(Some(UnionContained(1, Map("a" -> "b"))), UnionContainer.ContainedOrStringUnionUnionString("z")))

  jsonSerdesIsIdentity[Animal]("animal record", Animal("a", "b"))

  // jsonSerdesIsIdentity[UnionEmpty]("UnionEmpty", UnionEmpty(UnionEmpty.UnionFieldUnionInt())) // TODO: doesn't work

  jsonSerdesIsIdentity[UnionSingleton]("UnionSingleton", UnionSingleton(UnionSingleton.UnionFieldUnionInt(1)))

  jsonSerdesIsIdentity[UnionOptional]("union optional Some", UnionOptional(Some("a")))
  jsonSerdesIsIdentity[UnionOptional]("union optional None", UnionOptional(None))

  jsonSerdesIsIdentity[UnionOptionalComplex]("union optional complex None", UnionOptionalComplex(None))
  jsonSerdesIsIdentity[UnionOptionalComplex]("union optional complex Some", UnionOptionalComplex(Some(List("a", "b"))))

  jsonSerdesIsIdentity[UnionMany]("UnionMany int", UnionMany(UnionMany.UnionFieldUnionInt(123)))
  jsonSerdesIsIdentity[UnionMany]("UnionMany double", UnionMany(UnionMany.UnionFieldUnionDouble(123.0)))
  jsonSerdesIsIdentity[UnionMany]("UnionMany array<int>", UnionMany(UnionMany.UnionFieldUnionArrayInt(List(1, 2, 3))))
  jsonSerdesIsIdentity[UnionMany]("UnionMany map<string>", UnionMany(UnionMany.UnionFieldUnionMapString(Map("a" -> "b", "c" -> "d"))))

  jsonSerdesIsIdentity[RecordWithNestedMap]("RecordWithNestedMap", RecordWithNestedMap(Map("a" -> Map("b" -> 1))))

  jsonSerdesIsIdentity[RecordWithEnum]("RecordWithEnum Blue", RecordWithEnum(ColorEnum.Blue))
  jsonSerdesIsIdentity[RecordWithEnum]("RecordWithEnum Red", RecordWithEnum(ColorEnum.Red))

  jsonSerdesIsIdentity[RecordWithDefaults]("RecordWithDefaults", RecordWithDefaults(recordFieldDefault = RecordDefaultField(a = "a")))

}
