package hallborg.weeks.exceptions

import akka.http.scaladsl.model.StatusCodes.BadRequest

case class BadFormatException(override val message: String)
    extends WeeksException(message, BadRequest)
