package hallborg.weeks.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler, Route}
import akka.http.scaladsl.testkit.Specs2RouteTest
import hallborg.weeks.entities.Week
import hallborg.weeks.exceptions.ErrorResponse
import hallborg.weeks.logic.WeeksLogic
import hallborg.weeks.mocks.MockDate
import org.specs2.mutable._

final class WeeksRouteTest
  extends Specification
    with Specs2RouteTest
    with JsonMarshallingSupport {

  implicit def rejectionHandle: RejectionHandler = Handlers.rejectionHandle

  implicit def exceptionHandle: ExceptionHandler = Handlers.exceptionHandler

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
  "Asking for the week(s) between dates" should {
    "return one week number if two dates are in the same week" in {

      Get("/week?dates=2018-10-27,2018-10-28") ~> weeksRoute ~> check {
        status shouldEqual StatusCodes.OK
        val week = responseAs[Set[Week]]
        week.size shouldEqual 1
      }
    }
    "return two week numbers if two dates are not in the same week" in {
      Get("/week?dates=2018-10-27,2018-11-28") ~> weeksRoute ~> check {
        status shouldEqual StatusCodes.OK
        val week = responseAs[Set[Week]]
        week.size shouldEqual 2
      }
    }
    "return three week numbers if three dates are not in the same week" in {
      Get("/week?dates=2018-12-27,2018-11-28,2015-06-11") ~> weeksRoute ~> check {
        status shouldEqual StatusCodes.OK
        val week = responseAs[Set[Week]]
        week.size shouldEqual 3
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

      val unicorn =
        """
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
