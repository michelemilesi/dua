package dua

import dua.time.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import java.util.Locale

import scala.language.postfixOps

class GameTimeTest extends AnyFlatSpec with should.Matchers {
  "In game time" should "handle 1 round" in {
    val gt = 1 rounds;
    gt.second should be (10)
    gt.day should be(0)
    gt.year should be(0)
  }

  it should "handle 2 minutes" in {
    val gt = 2 minutes;
    gt.second should be(120)
    gt.day should be(0)
    gt.year should be(0)
  }

  it should "handle 1 year 2 days 23 hours" in {
    val gt = (1 year) + (2 days) + (23 hours)
    gt.second should be(23 * 3600)
    gt.day should be (2)
    gt.year should be (1)
  }

  it should "leap over 24 hours" in {
    val gt = (24 hours)
    gt.second should be(0)
    gt.day should be(1)
    gt.year should be(0)
  }

  it should "print a date" in {
    GameTime(2023, 123, 19*3600+8*60+7).toString should be ("Day 123 of year 2023 19:08:07")
  }

  it should "print a date (italian)" in {
    given DuaStatus = DuaStatus(locale = Locale.ITALIAN)
    GameTime(1500, 1, 19*3600 + 8*60 + 7).longFormat should be ("Giorno 1 di Hammer, 1500 DR. 8 minuti dopo l'ora 19")

    GameTime(1501, 30 + 12, 1 * 3600 + 1 * 60 + 1).longFormat should be ("Giorno 12 di Alturiak, 1501 DR. 1 minuti dopo l'ora 1")
  }

  /*
  it should "print last day (italian)" in {
    given DuaStatus = DuaStatus(locale = Locale.ITALIAN)

    val gtf = GameTime(1500, 360, 19 * 3600 + 8 * 60 + 7).longFormat
    gtf should be("Giorno 10, 3 decade di Ches, 1500 DR. 8 minuti dopo l'ora 19")
  }
  */
  it should "print a date (english)" in {
    given DuaStatus(locale = Locale.ENGLISH)
    val gtf = GameTime(1501, 30 * 7 + 2 * 10, 1 * 3600 + 10 * 60 + 9).longFormat
    gtf should be ("Day 20 of Elesias, 1501 DR. 10 minutes after hour 1")
  }
}
