package hallborg.weeks.logic

import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.time.temporal.WeekFields

import hallborg.weeks.entities.Week
import hallborg.weeks.exceptions.BadFormatException

import scala.util.Try

class WeeksLogic {

  def getWeekFromDateString(date: String): Week = {
    val localDate = tryParseDate(date)
    getWeekFromDate(localDate)
  }

  private def tryParseDate(date: String) = {
    Try(LocalDate.parse(date)).recover {
      case _: DateTimeParseException =>
        throw BadFormatException(s"Wrong format on date: $date")
    }.get
  }

  def currentWeek: Week = calculateWeek(LocalDate.now)

  def getWeekFromDate(date: LocalDate): Week = calculateWeek(date)

  private def calculateWeek(date: LocalDate) =
    Week(date.get(getWeeksFields))

  private def getWeeksFields =
    WeekFields.ISO.weekOfWeekBasedYear()

}
