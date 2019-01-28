package hallborg.weeks.entities

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import hallborg.weeks.exceptions.{ErrorResponse, NotFoundException}
import spray.json.DefaultJsonProtocol

trait JsonMarshallingSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val errorResponseFormat = jsonFormat3(ErrorResponse)
  implicit val notFoundFormat = jsonFormat1(NotFoundException)
  implicit val weekFormat = jsonFormat2(Week)
}
