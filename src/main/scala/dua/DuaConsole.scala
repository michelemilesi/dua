package dua

import scala.io.StdIn

object DuaConsole {
  def run() : Unit =
    var run = true
    while run do
      run = DuaInterpreter.parse(StdIn.readLine)
    
}
