package hallborg.weeks.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.Specs2RouteTest
import hallborg.weeks.Handlers
import hallborg.weeks.entities.Week
import hallborg.weeks.exceptions.ErrorResponse
import hallborg.weeks.logic.WeeksLogic
import hallborg.weeks.mocks.MockDate
import org.specs2.mutable._

final class WeeksRouteTest
    extends Specification
    with Specs2RouteTest
    with JsonMarshallingSupport {

  implicit def rejectionHandle = Handlers.rejectionHandle
  implicit def exceptionHandle = Handlers.exceptionHandler

  private val weeksRoute = Route.seal(new WeeksRoute().route)
  private val weeksLogic = new WeeksLogic()

  "Asking for the week number of a date" should {
    "return a json representation of the" >> {

      "current week when no date is given" in {
        Get("/week") ~> weeksRoute ~> check {
          status shouldEqual StatusCodes.OK
          val week = responseAs[Week]
          week.`week-number` shouldEqual weeksLogic.currentWeek.`week-number`
        }
      }
      "week the given date is in" in {
        val mockDate = MockDate(date = "2018-08-19", weekNumber = 33)

        Get(s"/week/${mockDate.date}") ~> weeksRoute ~> check {
          status shouldEqual StatusCodes.OK
          val week = responseAs[Week]
          week.`week-number` shouldEqual mockDate.weekNumber
        }
      }
      "bad request, when the date is formatted wrongly" in {
        val mockDate = MockDate(date = "2018-05-243", weekNumber = 0)

        Get(s"/week/${mockDate.date}") ~> weeksRoute ~> check {
          status shouldEqual StatusCodes.BadRequest

          val error = responseAs[ErrorResponse]
          error.detail must contain("Wrong format on date")

        }
      }
    }

  }

  "Querying a path that does not exists" should {
    "return a json error object" in {

      Get("/weeks") ~> weeksRoute ~> check {
        status shouldEqual StatusCodes.NotFound
        val error = responseAs[ErrorResponse]
        error.detail shouldEqual "Path does not exist"
      }
    }
  }
  "A 'Method not allowed'" should {
    "be returned on POST" in {
      Post("/week") ~> weeksRoute ~> check {
        status shouldEqual StatusCodes.MethodNotAllowed
      }
    }
    "be returned on PATCH" in {
      Patch("/week") ~> weeksRoute ~> check {
        status shouldEqual StatusCodes.MethodNotAllowed
      }
    }
  }
  "Any program written by hallborg" should {
    "always be able to give you a unicorn" in {

      val unicorn = """
                    /
               ,.. /
             ,'   ';
  ,,.__    _,' /';  .
 :','  ~~~~    '. '~
:' (   )         )::,
'. '. .=----=..-~  .;'
 '  ;'  ::   ':.  '"
   (:   ':    ;)
    \\   '"  ./
     '"      '""""

      Get("/unicorn") ~> weeksRoute ~> check {
        status shouldEqual StatusCodes.OK

        responseAs[String] shouldEqual unicorn
      }
    }
  }
}
