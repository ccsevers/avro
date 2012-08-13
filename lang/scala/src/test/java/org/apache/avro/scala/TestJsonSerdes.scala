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

}

