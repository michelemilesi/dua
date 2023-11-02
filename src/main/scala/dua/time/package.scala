package dua

package object time {
  extension (t: Int) {
    def round: GameTime = GameTime(second = t * 10)
    def rounds: GameTime = round
    def second: GameTime = GameTime(second = t)
    def seconds: GameTime = second
    def minute: GameTime = GameTime(second = t * 60)
    def minutes: GameTime = minute
    def hour: GameTime = GameTime(second = t * 3600)
    def hours: GameTime = hour
    def day: GameTime = GameTime(day = t)
    def days: GameTime = day
    def month: GameTime = GameTime(day = t * 30)
    def months: GameTime = month
    def year: GameTime = GameTime(year = t)
    def years: GameTime = year
  }
}
