package hallborg.weeks

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler}
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import hallborg.weeks.routes.TopRoute

object Main extends LazyLogging {
  def main(args: Array[String]) {

    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    implicit def rejectionHandle: RejectionHandler = Handlers.rejectionHandle

    implicit def exceptionHandle: ExceptionHandler = Handlers.exceptionHandler

    val route = new TopRoute().route

    Http().bindAndHandle(route, "0.0.0.0", 8080)
    logger.info("Server running on 0.0.0.0:8080")
  }
}
