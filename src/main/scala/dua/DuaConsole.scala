package dua

import scala.io.StdIn
import java.util.Locale

object DuaConsole {
  def run() : Unit =
    given DuaStatus(Locale.ITALIAN)
    var run = true
    while run do
      val line = StdIn.readLine
      if (!"".equals(line))
        run = DuaInterpreter.parse(line)
    
}
