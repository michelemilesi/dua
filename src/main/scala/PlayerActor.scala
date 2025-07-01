import akka.actor.typed.{ActorRef, Behavior, PostStop}
import akka.actor.typed.scaladsl.Behaviors
import java.net.Socket
import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import scala.concurrent.Future
import scala.util.control.NonFatal

object PlayerActor {
  // --- Messaggi: L'API pubblica dell'attore ---
  // Rimuoviamo 'private' per renderli accessibili dall'esterno (es. dai test)
  sealed trait Command
  final case class LineRead(text: String) extends Command
  case object ClientDisconnected extends Command // Anche questo deve essere pubblico

  def apply(writer: ActorRef[SocketWriter.Command], in: java.io.BufferedReader): Behavior[Command] =
    Behaviors.setup { context =>
      writer ! SocketWriter.Write("Benvenuto! Sei connesso al MUD 'dua'. Digita 'quit' per uscire.", context.system.ignoreRef)
      context.log.info("PlayerActor avviato e interattivo.")

      implicit val ec = context.system.executionContext
      val self = context.self

      Future {
        try {
          var line = ""
          while ({line = in.readLine(); line != null}) { self ! LineRead(line) }
          self ! ClientDisconnected
        } catch { case NonFatal(_) => self ! ClientDisconnected }
      }

      Behaviors.receiveMessage[Command] {
        case LineRead(text) =>
          context.log.info(s"Ricevuto dal client: '$text'")
          if (text.toLowerCase.trim == "quit") {
            writer ! SocketWriter.Write("Arrivederci!", context.system.ignoreRef)
            Behaviors.stopped
          } else {
            writer ! SocketWriter.Write(s"Hai digitato: '$text'", context.system.ignoreRef)
            Behaviors.same
          }

        case ClientDisconnected =>
          context.log.info("Il client si è disconnesso. L'attore si fermerà.")
          Behaviors.stopped
      }
    }
}