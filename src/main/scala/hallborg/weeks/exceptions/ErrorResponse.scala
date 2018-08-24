package hallborg.weeks.exceptions

case class ErrorResponse(status: String, code: String, detail: String)
    extends Exception
