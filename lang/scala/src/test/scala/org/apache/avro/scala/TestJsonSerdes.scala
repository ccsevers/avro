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

  jsonSerdesIsIdentity[Animal]("animal record", Animal("a", "b"))

  jsonSerdesIsIdentity[UnionSingleton]("UnionSingleton", UnionSingleton(UnionSingleton.UnionFieldUnionInt(1)))

  jsonSerdesIsIdentity[UnionOptional]("union optional Some", UnionOptional(Some("a")))
  jsonSerdesIsIdentity[UnionOptional]("union optional None", UnionOptional(None))

  jsonSerdesIsIdentity[UnionOptionalComplex]("union optional complex None", UnionOptionalComplex(None))
  jsonSerdesIsIdentity[UnionOptionalComplex]("union optional complex Some", UnionOptionalComplex(Some(List("a", "b"))))

  jsonSerdesIsIdentity[UnionMany]("UnionMany int", UnionMany(UnionMany.UnionFieldUnionInt(123)))
  jsonSerdesIsIdentity[UnionMany]("UnionMany double", UnionMany(UnionMany.UnionFieldUnionDouble(123.0)))
  jsonSerdesIsIdentity[UnionMany]("UnionMany array<int>", UnionMany(UnionMany.UnionFieldUnionArrayInt(List(1, 2, 3))))
  jsonSerdesIsIdentity[UnionMany]("UnionMany map<string>", UnionMany(UnionMany.UnionFieldUnionMapString(Map("a" -> "b", "c" -> "d"))))
}
