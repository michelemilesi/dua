package dua.time

import dua.{DuaMessages, DuaStatus}

import scala.annotation.targetName

class GameTime(val year: Int = 0, val day: Int = 0, val second: Int = 0) {
  private val SECONDS_PER_DAY: Int = 3600 * 24

  @targetName("add")
  def +(other: GameTime): GameTime = {
    val _s = second + other.second
    val s = _s % SECONDS_PER_DAY
    val _d = day + other.day + _s / SECONDS_PER_DAY
    val d = _d % 360
    val y = year + other.year + _d / 360
    new GameTime(y, d, s)
  }

  override def toString: String = {
    val hh = second / 3600
    val mm = (second / 60) % 60
    val ss = second % 60
    f"Day $day of year $year $hh%02d:$mm%02d:$ss%02d"
  }

  def longFormat(using status: DuaStatus): String = {
    val month = (day - 1) / 30 match
      case 0 => "Hammer"
      case 1 => "Alturiak"
      case 2 => "Ches"
      case 3 => "Tarsakh"
      case 4 => "Mirtul"
      case 5 => "Kythorn"
      case 6 => "Flamerule"
      case 7 => "Elesias"
      case 8 => "Eleint"
      case 9 => "Marpenoth"
      case 10 => "Uktar"
      case 11 => "Nightal"

    val dom = (day - 1) % 30 + 1

    DuaMessages.getMessage("date.long", dom, month, year.toString,
      second % 3600 / 60, second / 3600 )
  }
}

object GameTime {
  def apply(year: Int = 0, day: Int = 0, second: Int = 0): GameTime = new GameTime() + new GameTime(year, day, second)
}
