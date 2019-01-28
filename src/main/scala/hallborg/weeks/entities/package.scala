package hallborg.weeks

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import spray.json.{JsString, JsValue, RootJsonFormat}

package object entities {

  implicit object test extends RootJsonFormat[LocalDate] {
    private val ISODateTime = DateTimeFormatter.ISO_LOCAL_DATE

    override def read(json: JsValue): LocalDate = json match {
      case JsString(value) => LocalDate.parse(value, ISODateTime)
      case x => throw new RuntimeException(s"Unexpected type ${x.getClass.getName} when trying to parse LocalDate")
    }

    override def write(obj: LocalDate): JsValue = JsString(ISODateTime.format(obj))
  }

}
