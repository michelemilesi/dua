package dua
import java.util.Locale

case class DuaStatus(var locale: Locale = Locale.ENGLISH, var status: Status = Status.Menu, 
                     var character: Option[PlayerCharacter] = None) {
  def getCharacterSex: Option[Sex] = character match
    case Some(chr : PlayerCharacter) => chr.sex
    case _ => None
}


