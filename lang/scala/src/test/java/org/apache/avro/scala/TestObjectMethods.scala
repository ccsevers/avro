package org.apache.avro.scala

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.apache.avro.scala.test.generated.scala._
import org.apache.avro.scala.test.generated.scala.MutableContainer
import org.apache.avro.scala.test.generated.scala.MutableContained
import org.apache.avro.util.Utf8

@RunWith(classOf[JUnitRunner])
class TestEquals
  extends FunSuite with ShouldMatchers {

  def testEquals[I <: ImmutableRecordBase, M <: MutableRecordBase[I]](
    label: String, record1: => I, record2: => I, mutableRecord1: => M, mutableRecord2: => M) {
    test("%s (mutable != null)" format label) {
      assert(mutableRecord1 != null)
    }

    test("%s (immutable != null)" format label) {
      assert(record1 != null)
    }

    test("%s (mutable == mutable)" format label) {
      assert(mutableRecord1 === mutableRecord1)
    }

    test("%s (immutable == immutable)" format label) {
      assert(record1 === record1)
    }

    if (record2 != null && mutableRecord2 != null) {
      test("%s (mutable != mutable)" format label) {
        assert(mutableRecord1 != mutableRecord2)
      }

      test("%s (immutable != immutable)" format label) {
        assert(record1 != record2)
      }

      test("%s (mutable != immutable)" format label) {
        assert(mutableRecord1 != record2)
      }

      test("%s (immutable != mutable)" format label) {
        assert(record2 != mutableRecord1)
      }
    }
  }

  testEquals[EmptyRecord, MutableEmptyRecord]("empty record",
    new EmptyRecord, null, new MutableEmptyRecord, null)

  testEquals[RecordWithString, MutableRecordWithString]("record with string",
    new RecordWithString("a"), new RecordWithString("b"),
    new MutableRecordWithString("a"), new MutableRecordWithString("b"))

  testEquals[Container, MutableContainer]("nested record",
    new Container(contained = new Contained(data = 1)),
    new Container(contained = new Contained(data = 2)),
    new MutableContainer(contained = new MutableContained(data = 1)),
    new MutableContainer(contained = new MutableContained(data = 2)))

  testEquals[RecordWithNestedMap, MutableRecordWithNestedMap]("record with nested map",
    new RecordWithNestedMap(Map("a" -> Map("b" -> 1))),
    new RecordWithNestedMap(Map("a" -> Map("b" -> 2))),
    new MutableRecordWithNestedMap(collection.mutable.Map("a" -> collection.mutable.Map("b" -> 1))),
    new MutableRecordWithNestedMap(collection.mutable.Map("a" -> collection.mutable.Map("b" -> 2))))

    testEquals[RecordWithAllTypes, MutableRecordWithAllTypes]("record with all types",
    Fixtures.recordWithAllTypes("a"), Fixtures.recordWithAllTypes("b"),
    Fixtures.mutableRecordWithAllTypes("a"), Fixtures.mutableRecordWithAllTypes("b"))
}

@RunWith(classOf[JUnitRunner])
class TestHashCode
  extends FunSuite {

  def testHashCode[I <: ImmutableRecordBase, M <: MutableRecordBase[I]](label: String, record: => I, mutableRecord: => M) {
    test("%s (mutable)" format label) {
      assert(mutableRecord.hashCode() === mutableRecord.hashCode())
    }

    test("%s (immutable)" format label) {
      assert(record.hashCode() === record.hashCode())
    }

    test("%s (mutable == immutable)" format label) {
      assert(mutableRecord.hashCode() === record.hashCode())
    }
  }

  testHashCode[EmptyRecord, MutableEmptyRecord]("empty record",
    new EmptyRecord,
    new MutableEmptyRecord)

  testHashCode[Container, MutableContainer]("nested record",
    new Container(contained = new Contained(data = 1)),
    new MutableContainer(contained = new MutableContained(data = 1)))

  testHashCode[RecordWithAllTypes, MutableRecordWithAllTypes]("record with all types",
    Fixtures.recordWithAllTypes(),
    Fixtures.mutableRecordWithAllTypes())
}


