package hallborg.weeks.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

class TopRoute {

  private val weeksRoute = new WeeksRoute
  val route: Route = {
    pathPrefix("api") {
      weeksRoute.route
    }
  }
}
