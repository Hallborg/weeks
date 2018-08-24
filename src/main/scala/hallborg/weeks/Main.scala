package hallborg.weeks

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import hallborg.weeks.routes.TopRoute

object Main extends LazyLogging {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit def rejectionHandle = Handlers.rejectionHandle
    implicit def exceptionHandle = Handlers.exceptionHandler
    val route = new TopRoute().route

    Http().bindAndHandle(route, "localhost", 8080)
    logger.info("Server running on localhost:8080")
  }
}
