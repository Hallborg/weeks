package hallborg.weeks.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler}
import hallborg.weeks.exceptions._

object Handlers extends JsonMarshallingSupport {
  implicit def rejectionHandle: RejectionHandler =
    RejectionHandler.newBuilder
      .handleNotFound(
        complete(NotFound,
                 createErrorResponse(NotFoundException("Path does not exist"))))
      .result()

  implicit def exceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: BadFormatException =>
        complete(StatusCodes.BadRequest, createErrorResponse(e))
    }

  def createErrorResponse[T <: WeeksException](exception: T): ErrorResponse = {
    ErrorResponse(exception.statusCode.intValue().toString,
                  exception.getClass.getSimpleName,
                  exception.message)
  }
}
