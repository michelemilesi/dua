import akka.actor.typed.{ActorRef, Behavior, PostStop}
import akka.actor.typed.scaladsl.Behaviors
import java.io.PrintWriter

object SocketWriter {
  // Il messaggio di conferma che l'attore invierà
  sealed trait Ack
  case object Ack extends Ack

  sealed trait Command
  // Modifichiamo Write per includere a chi rispondere
  final case class Write(line: String, replyTo: ActorRef[Ack]) extends Command

  def apply(writer: PrintWriter): Behavior[Command] = {
    Behaviors.setup { context =>
      context.log.info("SocketWriter avviato.")

      Behaviors.receiveMessage[Command] {
          case Write(line, replyTo) =>
            // 1. Fa il suo lavoro
            writer.println(line)
            // 2. Invia la conferma
            replyTo ! Ack
            Behaviors.same
        }
        .receiveSignal {
          case (ctx, PostStop) =>
            ctx.log.info("SocketWriter in arresto, chiusura del writer.")
            writer.close()
            Behaviors.same
        }
    }
  }
}