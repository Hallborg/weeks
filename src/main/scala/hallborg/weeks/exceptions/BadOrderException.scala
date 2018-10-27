package hallborg.weeks.exceptions

import akka.http.scaladsl.model.StatusCodes.BadRequest

case class BadOrderException(override val message: String)
  extends WeeksException(message, BadRequest)
