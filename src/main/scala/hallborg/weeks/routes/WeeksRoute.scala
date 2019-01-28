package hallborg.weeks.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.PredefinedFromStringUnmarshallers
import hallborg.weeks.entities.JsonMarshallingSupport
import hallborg.weeks.logic.{Unicorn, WeeksLogic}

class WeeksRoute extends JsonMarshallingSupport with PredefinedFromStringUnmarshallers {

  val logic = new WeeksLogic
  val route: Route =
    get {
      path("week") {
        parameter("dates".as(CsvSeq[String])) { dates =>
          complete(logic.getWeeksFromDates(dates))
        }
      } ~ path("week" / RemainingPath) { date =>
        complete(logic.getWeekFromDateString(date.toString()))
      } ~ path("week") {
        complete(logic.currentWeek)
      } ~ path("unicorn") {
        complete(Unicorn.render)
      }
    }
}