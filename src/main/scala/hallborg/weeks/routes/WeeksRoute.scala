package hallborg.weeks.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import hallborg.weeks.logic.{Unicorn, WeeksLogic}

class WeeksRoute extends JsonMarshallingSupport {

  val logic = new WeeksLogic
  val route: Route =
    get {
      path("week") {
        complete(logic.currentWeek)
      }
    } ~ path("week" / RemainingPath) { date =>
      complete(logic.getWeekFromDateString(date.toString()))
    } ~ path("unicorn") {
      complete(Unicorn.render)
    }
}
