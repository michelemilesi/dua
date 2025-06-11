import akka.actor.typed.{ActorRef, Behavior, PostStop}
import akka.actor.typed.scaladsl.Behaviors
import java.net.Socket
import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import scala.util.control.NonFatal
import scala.concurrent.Future // <-- ECCO L'IMPORT MANCANTE

object PlayerActor {
  // --- Messaggi ---
  sealed trait Command
  private final case class LineRead(text: String) extends Command
  private case object ClientDisconnected extends Command

  def apply(socket: Socket): Behavior[Command] = Behaviors.setup { context =>
    // Prepariamo i canali di input e output che useremo
    val out = new PrintWriter(socket.getOutputStream, true)
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream))

    // Messaggio di benvenuto iniziale
    out.println("Benvenuto! Sei connesso al MUD 'dua'. Digita 'quit' per uscire.")
    context.log.info(s"PlayerActor per ${socket.getInetAddress} avviato e interattivo.")

    // --- Lettura Asincrona dell'Input ---
    implicit val ec = context.system.executionContext
    val self: ActorRef[Command] = context.self

    Future {
      try {
        var line = ""
        // Il ciclo si blocca su readLine() e continua finché la connessione è attiva
        while ({line = in.readLine(); line != null}) {
          self ! LineRead(line)
        }
        // Se usciamo dal ciclo (line == null), il client si è disconnesso
        self ! ClientDisconnected
      } catch {
        case NonFatal(e) =>
          // Se c'è un errore di rete, il client si è probabilmente disconnesso
          self ! ClientDisconnected
      }
    }

    // --- Comportamento dell'Attore ---
    Behaviors.receiveMessage[Command] {
      case LineRead(text) =>
        context.log.info(s"Ricevuto dal client: '$text'")
        if (text.toLowerCase.trim == "quit") {
          out.println("Arrivederci!")
          Behaviors.stopped // Ferma l'attore, che chiuderà la connessione
        } else {
          out.println(s"Hai digitato: '$text'")
          Behaviors.same // Continua ad attendere altri comandi
        }

      case ClientDisconnected =>
        context.log.info("Il client si è disconnesso. L'attore si fermerà.")
        Behaviors.stopped

    }.receiveSignal {
      // Questo blocco viene eseguito quando l'attore sta per fermarsi (es. con Behaviors.stopped)
      // È il posto giusto per chiudere le risorse come il socket.
      case (ctx, PostStop) =>
        ctx.log.info("PlayerActor in arresto, chiusura del socket.")
        socket.close()
        Behaviors.same
    }
  }
}