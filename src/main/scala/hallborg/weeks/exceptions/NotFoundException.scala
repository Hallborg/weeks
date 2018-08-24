package hallborg.weeks.exceptions

import akka.http.scaladsl.model.StatusCodes.NotFound

case class NotFoundException(override val message: String)
    extends WeeksException(message, NotFound)
