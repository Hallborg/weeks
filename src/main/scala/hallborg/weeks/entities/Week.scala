package hallborg.weeks.entities

case class Week(`week-number`: Int) {
  require(`week-number` > 0, "The week number must be gt 0")
  require(`week-number` <= 53, "The week number must be lte 53")
}
