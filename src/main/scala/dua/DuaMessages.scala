package dua

import org.jmotor.i18n.Messages
import java.util.Locale

object DuaMessages {
  def getMessage(key: String, args: Any*)(using status: DuaStatus): String = {
    val messages = Messages()
    messages.format(key, args:_*)(status.locale)
  }
}
