package hallborg.weeks.entities

import java.time.LocalDate

case class Week(`week-number`: Int, date: LocalDate) {
  require(`week-number` > 0, "The week number must be gt 0")
  require(`week-number` <= 53, "The week number must be lte 53")
}

