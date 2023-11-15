package dua

import org.scalatest.*
import flatspec.*
import matchers.*
import dua.Sex.*

import java.util.Locale

class HumanTest extends AnyFlatSpec with should.Matchers {
  private val human = Race.HUMAN
  implicit val status : DuaStatus = DuaStatus()

  "A human race" should "have Italian compiled names" in {
    status.locale = Locale.ITALIAN
    human.getName() should be ("umano")
    human.getPluralName() should be ("umani")
    human.getName(sex = Some(F)) should be("donna")
    human.getPluralName(sex = Some(F)) should be("donne")
    human.getName(sex = Some(M)) should be("uomo")
    human.getPluralName(sex = Some(M)) should be("uomini")
  }

  it should "have English compiled names" in {
    status.locale = Locale.ENGLISH
    human.getName() should be("human")
    human.getPluralName() should be("humans")
    human.getName(sex = Some(F)) should be("woman")
    human.getPluralName(sex = Some(F)) should be("women")
    human.getName(sex = Some(M)) should be("man")
    human.getPluralName(sex = Some(M)) should be("men")
  }
}
