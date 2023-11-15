package dua

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import dua.Ability.*

import java.util.Locale

class AbilitiesTest extends AnyFlatSpec with should.Matchers {
  implicit val status : DuaStatus = DuaStatus()

  "Abilities" should "holds statistics" in {
    val abilities = Abilities(1, 2, 3, 4, 5, 6)
    abilities.str should be (1)
    abilities.dex should be (2)
    abilities.con should be (3)
    abilities.int should be (4)
    abilities.wis should be (5)
    abilities.cha should be (6)
  }

  it should "get statistics" in {
    val abilities = Abilities(1, 2, 3, 4, 5, 6)
    abilities.get(STR) should be(1)
    abilities.get(DEX) should be(2)
    abilities.get(CON) should be(3)
    abilities.get(INT) should be(4)
    abilities.get(WIS) should be(5)
    abilities.get(CHA) should be(6)
  }

  it should "get italian statistics name" in {
    status.locale = Locale.ITALIAN
    Abilities.getText(STR) should be("forza")
    Abilities.getText(DEX) should be("destrezza")
    Abilities.getText(CON) should be("costituzione")
    Abilities.getText(INT) should be("intelligenza")
    Abilities.getText(WIS) should be("saggezza")
    Abilities.getText(CHA) should be("carisma")
  }

  it should "get statistic modifiers" in {
    var abilities = Abilities(1, 2, 3, 4, 5, 6)
    abilities.mod(STR) should be(-5)
    abilities.mod(DEX) should be(-4)
    abilities.mod(CON) should be(-4)
    abilities.mod(INT) should be(-3)
    abilities.mod(WIS) should be(-3)
    abilities.mod(CHA) should be(-2)

    abilities = Abilities(7, 8, 9, 10, 11, 12)
    abilities.mod(STR) should be(-2)
    abilities.mod(DEX) should be(-1)
    abilities.mod(CON) should be(-1)
    abilities.mod(INT) should be(0)
    abilities.mod(WIS) should be(0)
    abilities.mod(CHA) should be(1)

    abilities = Abilities(13, 14, 15, 16, 17, 18)
    abilities.mod(STR) should be(1)
    abilities.mod(DEX) should be(2)
    abilities.mod(CON) should be(2)
    abilities.mod(INT) should be(3)
    abilities.mod(WIS) should be(3)
    abilities.mod(CHA) should be(4)

    abilities = Abilities(19, 20, 21, 22, 23, 24)
    abilities.mod(STR) should be(4)
    abilities.mod(DEX) should be(5)
    abilities.mod(CON) should be(5)
    abilities.mod(INT) should be(6)
    abilities.mod(WIS) should be(6)
    abilities.mod(CHA) should be(7)

    abilities = Abilities(25, 26, 27, 28, 29, 30)
    abilities.mod(STR) should be(7)
    abilities.mod(DEX) should be(8)
    abilities.mod(CON) should be(8)
    abilities.mod(INT) should be(9)
    abilities.mod(WIS) should be(9)
    abilities.mod(CHA) should be(10)
  }
}
