package dua

import scala.io.StdIn
import java.util.Locale

object DuaConsole {
  def run() : Unit =
    given DuaStatus(Locale.ITALIAN)
    var run = true
    while run do
      showPrompt
      val line = StdIn.readLine
      if (!"".equals(line))
        run = DuaInterpreter.parse(line)

  def showPrompt(using status: DuaStatus) : Unit =
    status.status match
      case Status.Menu => showMenuPrompt
      case _ => showMenuPrompt

  def showMenuPrompt : Unit =
    print("> ")
}
