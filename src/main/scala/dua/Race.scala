package dua

trait Race {
  def getName(sex: Option[Sex] = None)(using DuaStatus): String
  def getPluralName(sex: Option[Sex] = None)(using DuaStatus): String
  def getRaceAbilities: Abilities = Abilities()
  def getRaceAbilitiesOnChoice: List[Int] = List.empty[Int]
  def getRaceSkills: Int = 0
  def getRaceLanguages: List[String] = List("Common")
  def getSize: String = "M"
  def getSpeed: Int = 9
  def getImmunities: List[String] = List.empty[String]
  def getAdvantages: List[String] = List.empty[String]
  def getHandicaps: List[String] = List.empty[String]
  def getDarkVisionRange: Int = 0
}

abstract class AbstractRace(name: String) extends Race, Printable {
  protected val TEXT_SINGULAR = "singular"
  protected val TEXT_PLURAL = "plural"
  protected val TEXT_SINGULAR_MALE = "singular.male"
  protected val TEXT_SINGULAR_FEMALE = "singular.female"
  protected val TEXT_PLURAL_MALE = "plural.male"
  protected val TEXT_PLURAL_FEMALE = "plural.female"

  override def getName(sex: Option[Sex] = None)(using DuaStatus): String = sex match {
    case Some(Sex.M) => DuaMessages.getMessage(f"${name}.${TEXT_SINGULAR_MALE}")
    case Some(Sex.F) => DuaMessages.getMessage(f"${name}.${TEXT_SINGULAR_FEMALE}")
    case _ => DuaMessages.getMessage(f"${name}.${TEXT_SINGULAR}")
  }


  override def getPluralName(sex: Option[Sex] = None)(using DuaStatus): String = sex match {
    case Some(Sex.M) => DuaMessages.getMessage(f"${name}.${TEXT_PLURAL_MALE}")
    case Some(Sex.F) => DuaMessages.getMessage(f"${name}.${TEXT_PLURAL_FEMALE}")
    case _ => DuaMessages.getMessage(f"${name}.${TEXT_PLURAL}")
  }


  override def print(variant: Option[Any] = None)(using status:DuaStatus): String = variant match {
    case Some(Sex.M) => getName(sex = Some(Sex.M))
    case Some(Sex.F) => getName(sex = Some(Sex.F))
    case _ => getName()
  }
}

class Human extends AbstractRace("human") {
  override def getRaceAbilities: Abilities = Abilities(1, 1, 1, 1, 1, 1)
  override def getRaceLanguages: List[String] = List("Common", "Choice")
}

class HalfElf extends AbstractRace("half-elf") {
  override def getRaceAbilities: Abilities = Abilities(cha = 2)
  override def getRaceAbilitiesOnChoice: List[Int] = List(1, 1)
  override def getRaceSkills: Int = 2
  override def getRaceLanguages: List[String] = List("Common", "Elvish", "Choice")
  override def getDarkVisionRange: Int = 18
  override def getImmunities: List[String] = List("sleep")
  override def getAdvantages: List[String] = List("charme")
}

object Race {
  val HUMAN = new Human()
  val HALF_ELF = new HalfElf()
  val RACES: List[Race] = List(HUMAN, HALF_ELF)
}