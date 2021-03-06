package org.apache.avro.scala

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.apache.avro.scala.test.generated.scala._
import scala.collection.mutable

@RunWith(classOf[JUnitRunner])
class TestJsonSerdes
  extends FunSuite {

  def jsonSerdesIsIdentity[I <: ImmutableRecordBase, M <: MutableRecordBase[I]](label: String, record: => I, mutableRecord: => M) {
    test("%s (mutable)" format label) {
      val recordJson = Records.toJson(mutableRecord)
      val mutableRecord2 = Records.mutableFromJson[M](mutableRecord, recordJson)
      assert(mutableRecord === mutableRecord2)
    }

    test("%s (immutable)" format label) {
      val record2 = Records.fromJson[I](record.getSchema, Records.toJson(record))
      assert(record === record2)
      assert(record.getClass === record2.getClass)
    }
  }

  jsonSerdesIsIdentity[EmptyRecord, MutableEmptyRecord]("empty record",
    new EmptyRecord,
    new MutableEmptyRecord)

  jsonSerdesIsIdentity[Container, MutableContainer]("nested record",
    new Container(contained = new Contained(data = 1)),
    new MutableContainer(contained = new MutableContained(data = 1)))

  jsonSerdesIsIdentity[UnionOptional, MutableUnionOptional]("record with optional union (set)",
    new UnionOptional(optionalField = Some("a")),
    new MutableUnionOptional(optionalField = Some("a")))

  jsonSerdesIsIdentity[UnionOptional, MutableUnionOptional]("record with optional union (unset)",
    new UnionOptional(optionalField = None),
    new MutableUnionOptional(optionalField = None))

  jsonSerdesIsIdentity[UnionMany, MutableUnionMany]("record with many types (string)",
    new UnionMany(unionField = UnionMany.UnionFieldUnionString("a")),
    new MutableUnionMany(unionField = UnionMany.MutableUnionFieldUnionString("a")))

  jsonSerdesIsIdentity[UnionMany, MutableUnionMany]("record with many types (map<string>)",
    new UnionMany(
      unionField = UnionMany.UnionFieldUnionMapString(Map("a" -> "b"))),
    new MutableUnionMany(
      unionField = UnionMany.MutableUnionFieldUnionMapString(collection.mutable.Map("a" -> "b"))))

  jsonSerdesIsIdentity[UnionContainer, MutableUnionContainer]("nested record with union",
    new UnionContainer(
      containedOrNullUnion = Some(new UnionContained(
        data = 1, mapField = Map[String, String]("a" -> "1"))),
      containedOrStringUnion = UnionContainer.ContainedOrStringUnionUnionString("a")),
    new MutableUnionContainer(
      containedOrNullUnion = Some(new MutableUnionContained(
        data = 1, mapField = collection.mutable.Map[String, String]("a" -> "1"))),
      containedOrStringUnion = UnionContainer.MutableContainedOrStringUnionUnionString("a")))

  jsonSerdesIsIdentity[RecordWithString, MutableRecordWithString]("record with string",
    new RecordWithString("a"),
    new MutableRecordWithString("a"))

  jsonSerdesIsIdentity[RecordWithNestedMap, MutableRecordWithNestedMap]("record with nested map",
    new RecordWithNestedMap(Map("a" -> Map("b" -> 1))),
    new MutableRecordWithNestedMap(collection.mutable.Map("a" -> collection.mutable.Map("b" -> 1))))

  jsonSerdesIsIdentity[RecordWithAllTypes, MutableRecordWithAllTypes]("record with all types",
    Fixtures.recordWithAllTypes(),
    Fixtures.mutableRecordWithAllTypes()
  )

  jsonSerdesIsIdentity[RecordWithEnum, MutableRecordWithEnum]("record with enum",
    new RecordWithEnum(enumField = ColorEnum.Blue),
    new MutableRecordWithEnum(enumField = ColorEnum.Red)
  )

}

