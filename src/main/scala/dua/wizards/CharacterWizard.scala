package dua.wizards

import dua.{Abilities, CharacterClass, DuaMessages, DuaStatus, HitPoint, PlayerCharacter, Printable, Race, Sex}
import dua.Ability.*
import dua.inventory._
import DuaMessages.getMessage
import dua.actions.DiceRoll

import scala.annotation.tailrec
import scala.io.StdIn
import scala.language.postfixOps

class CharacterWizard {
  private val character = PlayerCharacter()

  def execute(using status: DuaStatus): Unit = {
    character.name = selectName()

    val race = selectRace()
    if (race.isDefined) {
      println(getMessage("wiz.character.selected_race", race.get.getName()))
    }
    character.race = race

    val sex = selectSex()
    character.sex = sex

    val characterClass = selectClass()
    if (characterClass.isDefined) {
      println(getMessage("wiz.character.selected_class", characterClass.get.getName()))
    }
    character.characterClass = characterClass

    val abilities = selectAbilities()
    character.abilities = abilities

    val clazz = character.characterClass.get
    character.hp = Some(HitPoint(level = 1, dice = clazz.getHitPointDice,
        modifier = character.abilities.get.mod(CON)))

    character.inventory.coins + clazz.getStartingGold

    character.show
    prompt()
  }

  @tailrec
  private def checkCondition[A](cond: Option[A])(code: => Option[A]): Option[A] = {
    if (cond.isDefined) {
      cond
    } else {
      val cond = code
      checkCondition(cond)(code)
    }
  }

  private def selectRace()(using status: DuaStatus): Option[Race] = {
    val n = checkCondition(Option.empty[Int]) {
      println(getMessage("wiz.character.select_race"))
      for (i <- 1 to Race.RACES.length) {
        println(f"$i) ${Race.RACES(i - 1).getName()}")
      }
      prompt()
      StdIn.readLine match {
        case n if n.toIntOption.isDefined && n.toInt > 0 && n.toInt <= Race.RACES.length => n.toIntOption
        case "x" => Some(-1)
        case _ => None
      }
    }
    n match
      case Some(r) if r > 0 => Some(Race.RACES(r - 1))
      case _ => None
  }

  private def selectClass()(using status: DuaStatus): Option[CharacterClass] = {
    val n = checkCondition(Option.empty[Int]) {
      println(getMessage("wiz.character.select_class"))
      for (i <- 1 to CharacterClass.CLASSES.length) {
        println(f"$i) ${CharacterClass.CLASSES(i - 1).getName()}")
      }
      prompt()
      StdIn.readLine match {
        case n if n.toIntOption.isDefined && n.toInt > 0 && n.toInt <= CharacterClass.CLASSES.length => n.toIntOption
        case "x" => Some(-1)
        case _ => None
      }
    }
    n match
      case Some(c) if c > 0 => Some(CharacterClass.CLASSES(c - 1))
      case _ => None
  }

  private def selectSex()(using status: DuaStatus): Option[Sex] = {
    val s = checkCondition(Option.empty[Char]) {
      println(getMessage("wiz.character.select_sex"))
      println(f"F - ${character.race.get.getName(Some(Sex.F))}")
      println(f"M - ${character.race.get.getName(Some(Sex.M))}")
      prompt()
      StdIn.readLine match {
        case "F" => Some("F")
        case "M" => Some("M")
        case "x" => Some("X")
        case _ => None
      }
    }
    s match
      case Some("F") => Some(Sex.F)
      case Some("M") => Some(Sex.M)
      case _ => None
  }

  def selectAbilities()(using status: DuaStatus) : Option[Abilities] = {
    def pool =  (for (_ <-1 to 6) yield DiceRoll.roll(4, 6).skipWorst()).sortWith((a, b) => a > b).toList

    val raceAbs = character.race.get.getRaceAbilities

    @tailrec
    def selectAbilities(abs: Abilities, pool: List[Int]) : Abilities =
      if (pool.isEmpty) {
        abs
      } else {
        println(DuaMessages.getMessage("wiz.character.abilities.pool", pool.mkString("[", " ", "]")))
        val abs_names = List(STR, DEX, CON, INT, WIS, CHA)
        var pos = 1
        abs_names.foreach(ab => {
          val value = abs.get(ab)
          val opt = if (value == 0) {
            f"$pos"
          } else {
            " "
          }
          pos = pos + 1
          println(f"$opt) ${Abilities.getText(ab).capitalize}%20s: " +
            f"${abs.get(ab)}%2d + ${raceAbs.get(ab)}%2d = ${abs.get(ab) + raceAbs.get(ab)}%2d")
        })

        print(f"${DuaMessages.getMessage("wiz.character.abilities.next_value", pool.head)} > ")

        val (newAbs, newPool) = StdIn.readLine match {
          case "1" if abs.str == 0 => (abs.modify(Abilities(str = pool.head)), pool.tail)
          case "2" if abs.dex == 0 => (abs.modify(Abilities(dex = pool.head)), pool.tail)
          case "3" if abs.con == 0 => (abs.modify(Abilities(con = pool.head)), pool.tail)
          case "4" if abs.int == 0 => (abs.modify(Abilities(int = pool.head)), pool.tail)
          case "5" if abs.wis == 0 => (abs.modify(Abilities(wis = pool.head)), pool.tail)
          case "6" if abs.cha == 0 => (abs.modify(Abilities(cha = pool.head)), pool.tail)
          case _ => (abs, pool)
        }
        selectAbilities(newAbs, newPool)
      }

    Some(selectAbilities(Abilities(), pool).modify(raceAbs))
  }

  private def selectName()(using status: DuaStatus): Option[String] = {
    checkCondition(Option.empty[String]) {
      println(getMessage("wiz.character.select_name"))
      prompt()
      StdIn.readLine match {
        case "" => None
        case name => Some(name)
      }
    }
  }

  private def prompt()(using status: DuaStatus): Unit = print(f"${character.print(variant = character.sex)} > ")
}

object CharacterWizard {
  def execute(using status: DuaStatus): Unit = {
    val wiz = new CharacterWizard()
    wiz.execute
  }
}
