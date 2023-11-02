package dua

import org.scalatest._
import flatspec._
import matchers._

class HumanTest extends AnyFlatSpec with should.Matchers {
  private val human = Race.HUMAN
  implicit val status : DuaStatus = DuaStatus()

  "A human race" should "have Italian compiled names" in {
    human.getName should be ("umano")
    human.getPluralName should be ("umani")
    human.getFemaleName should be("donna")
    human.getPluralFemaleName should be("donne")
    human.getMaleName should be("uomo")
    human.getPluralMaleName should be("uomini")
  }
}
