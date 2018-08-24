package hallborg.weeks.exceptions
import akka.http.scaladsl.model.StatusCode

abstract class WeeksException(msg: String, code: StatusCode) extends Exception {
  val message = msg
  val statusCode = code
}
