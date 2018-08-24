package hallborg.weeks.routes

import akka.http.scaladsl.model.StatusCodes
import hallborg.weeks.Handlers
import hallborg.weeks.exceptions.{ErrorResponse, NotFoundException}
import org.specs2.mutable._

final class HandlersTest extends Specification {
  "HandlersTest" should {
    "create an errorResponse" in {
      val testErrorResponse =
        ErrorResponse(StatusCodes.NotFound.intValue.toString,
                      NotFoundException.getClass.getSimpleName
                        .dropRight(1),
                      "test not found")

      val errorObject =
        Handlers.createErrorResponse(NotFoundException("test not found"))

      errorObject shouldEqual testErrorResponse
    }

  }
}
