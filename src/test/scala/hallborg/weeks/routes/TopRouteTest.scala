package hallborg.weeks.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.Specs2RouteTest
import org.specs2.mutable.Specification

class TopRouteTest extends Specification
  with Specs2RouteTest {
  val topRoute = new TopRoute().route

  "Asking for the week number of a date" should {
    "return a json representation of the" >> {

      "current week when no date is given" in {
        Get("/api/unicorn") ~> topRoute ~> check {
          status shouldEqual StatusCodes.OK
        }
      }
    }
  }
}