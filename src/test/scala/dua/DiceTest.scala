package dua

import org.scalatest.*
import flatspec.*
import matchers.*
import actions.*
import actions.DiceRoll.*

import scala.language.postfixOps

class DiceTest extends AnyFlatSpec with should.Matchers {
  "A dice" should "roll a d6" in {
    val result = roll(1 d6)
    result should be >= 1
    result should be <= 6
  }

  it should "roll 2d6" in {
    val result = roll(2 d2)
    result should be >= 2
    result should be <= 4
  }

  it should "take the best of 8d3" in {
    val result = max(8 d3)
    result should be >= 1
    result should be <= 3
  }

  it should "take the worst of 7d4" in {
    val result = min(7 d4)
    result should be >= 1
    result should be <= 4
  }

  it should "roll 2d8+3" in {
    val result = roll(2 d8) +3
    result should be >= 4
    result should be <= 19
  }

  it should "roll 1d20+3" in {
    val result = roll(1 d20) + 3
    result should be >= 4
    result should be <= 23
  }

  it should "roll 1d100" in {
    val result = roll(1 d100)
    result should be >= 1
    result should be <= 100
  }

  it should "skip the worst roll" in {
    val dr = 3 d10
    val total = roll(dr)
    val worst = min(dr)
    val result = skipWorst(dr)
    result should be >= 2
    result should be <= 20
    result should be (total - worst)
  }

  it should "skip the best roll" in {
    val dr = 3 d12
    val total = roll(dr)
    val best = max(dr)
    val result = skipBest(dr)
    result should be >= 2
    result should be <= 24
    result should be(total - best)
  }
}
