package hallborg.weeks

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.RejectionHandler
import hallborg.weeks.exceptions.{
  BadFormatException,
  ErrorResponse,
  NotFoundException,
  WeeksException
}
import hallborg.weeks.routes.JsonMarshallingSupport

object Handlers extends JsonMarshallingSupport {
  implicit def rejectionHandle =
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
