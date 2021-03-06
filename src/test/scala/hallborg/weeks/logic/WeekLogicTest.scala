package hallborg.weeks.logic

import java.time.LocalDate
import java.time.temporal.WeekFields

import hallborg.weeks.entities.Week
import hallborg.weeks.exceptions.BadFormatException
import hallborg.weeks.mocks.MockDate
import org.specs2.mutable._

final class WeekLogicTest extends Specification {
  private val weeksLogic = new WeeksLogic()

  private def confirmCalculatedWeek(mockWeek: MockDate) = {
    val calculatedWeek = weeksLogic.getWeekFromDateString(mockWeek.date)

    calculatedWeek.`week-number` shouldEqual mockWeek.`week-number`
  }

  "A week" can {
    "have a number between 1 and 53" in {
      val date = LocalDate.now()
      def week = Week(_:Int, date)
      week.apply(1) must not(throwA[IllegalArgumentException])
      week.apply(53) must not(throwA[IllegalArgumentException])

      week.apply(0) must throwA[IllegalArgumentException]
      week.apply(54) must throwA[IllegalArgumentException]

    }
  }
  "A week" should {
    "end on a sunday" in {
      val sundayDate = MockDate(date = "2018-08-19", `week-number` = 33)
      confirmCalculatedWeek(sundayDate)
    }
    "start on a monday" in {
      val mondayDate = MockDate(date = "2018-08-20", `week-number` = 34)
      confirmCalculatedWeek(mondayDate)
    }
  }

  "Calculating the current week" should {
    "always be viable" in {
      val currentDate = LocalDate.now
      val weekField = WeekFields.ISO.weekOfWeekBasedYear()
      currentDate.get(weekField) must_== weeksLogic.currentWeek.`week-number`
    }
  }

  "Calculating the week" should {
    "work amazing" >> {
      "from dates" in {
        val dateInWeek32 = LocalDate.of(2018, 8, 7)
        weeksLogic.getWeekFromDate(dateInWeek32) shouldEqual Week(32, dateInWeek32)
      }
      "from strings" in {
        val mockDate = MockDate(date = "2018-08-20", `week-number` = 34)
        val week = weeksLogic.getWeekFromDateString(date = mockDate.date)
        week.`week-number` shouldEqual mockDate.`week-number`
      }
    }
    "fail brutally" >> {
      "for strings that can't be parsed to a date" in {
        val randomString = "noSo-random.String"
        val badFormatDate = "2018-13-13"

        weeksLogic.getWeekFromDateString(randomString) must throwA[
          BadFormatException]

        weeksLogic.getWeekFromDateString(badFormatDate) must throwA[
          BadFormatException]
      }
    }

  }
}
