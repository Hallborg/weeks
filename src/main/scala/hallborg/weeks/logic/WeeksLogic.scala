package hallborg.weeks.logic

import java.time.LocalDate
import java.time.temporal.{TemporalField, WeekFields}

import hallborg.weeks.entities.Week
import hallborg.weeks.exceptions.BadFormatException

import scala.util.{Failure, Success, Try}

class WeeksLogic {

  def currentWeek: Week = calculateWeek(LocalDate.now)

  def getWeeksFromDates(dates: Seq[String]): Set[Week] = {
    dates.map(getWeekFromDateString).toSet
  }

  def getWeekFromDateString(date: String): Week = {
    val localDate = tryParseDate(date)
    getWeekFromDate(localDate)
  }

  private def tryParseDate(date: String): LocalDate = {
    Try(LocalDate.parse(date)) match {
      case Success(parsedDate) => parsedDate
      case Failure(_) =>
        throw BadFormatException(s"Wrong format on date: $date")
    }
  }

  def getWeekFromDate(date: LocalDate): Week = calculateWeek(date)

  private def calculateWeek(date: LocalDate) =
    Week(date.get(getWeeksFields))

  private def getWeeksFields: TemporalField =
    WeekFields.ISO.weekOfWeekBasedYear()
}