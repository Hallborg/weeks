package hallborg.weeks.logic

import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.time.temporal.WeekFields

import hallborg.weeks.entities.Week
import hallborg.weeks.exceptions.{BadFormatException, BadOrderException}

import scala.util.{Failure, Success, Try}

class WeeksLogic {

  def currentWeek: Week = calculateWeek(LocalDate.now)

  def getWeekFromDateString(date: String): Week = {
    val localDate = tryParseDate(date)
    getWeekFromDate(localDate)
  }

  private def tryParseDate(date: String) = {
    Try(LocalDate.parse(date)) match {
      case Success(parsedDate) => parsedDate
      case Failure(_: DateTimeParseException) =>
        throw BadFormatException(s"Wrong format on date: $date")
    }
  }

  def getWeekFromDate(date: LocalDate): Week = calculateWeek(date)

  private def calculateWeek(date: LocalDate) =
    Week(date.get(getWeeksFields))

  private def getWeeksFields =
    WeekFields.ISO.weekOfWeekBasedYear()

  def getWeeksFromDateStrings(from: String, to: String): Set[Week] = {
    val fromDate = tryParseDate(from)
    val toDate = tryParseDate(to)
    validateCronicalOrder(fromDate, toDate)
    Set(getWeekFromDate(fromDate), getWeekFromDate(toDate))
  }

  private def validateCronicalOrder(from: LocalDate, to: LocalDate): Unit = {
    if(from.isAfter(to)) throw BadOrderException("'from' must be before 'to'")
  }
}