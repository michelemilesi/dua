package dua.actions

import dua.{DuaMessages, DuaStatus}

import scala.io.StdIn

object Console {
  def quit()(using status: DuaStatus): Boolean = {
    val message = DuaMessages.getMessage("console.quit.check")
    println(f"${message} [Y|n]")
    val response = StdIn.readLine()
    val retValue = ! "n".equals(response.toLowerCase)
    if (retValue) println(DuaMessages.getMessage("console.quit.bye"))
    retValue
  }
}
