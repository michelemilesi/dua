import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors} // Import ActorContext
import java.net.{ServerSocket, Socket}
import scala.concurrent.Future
import java.io.{BufferedReader, InputStreamReader, PrintWriter}

object ServerActor {
  // --- Messaggi che l'attore può ricevere ---
  sealed trait Command
  private case object ServerStarted extends Command
  final case class NewConnection(socket: Socket) extends Command
  private final case class ServerFailed(error: Throwable) extends Command

  def apply(): Behavior[Command] = Behaviors.setup { context =>
    implicit val ec = context.system.executionContext

    // Catturiamo un riferimento sicuro all'attore per usarlo nel Future
    val self: ActorRef[Command] = context.self

    // Avviamo il server bloccante in un thread separato
    Future {
      MUDServer.using(new ServerSocket(4000)) { serverSocket =>
        // Una volta avviato, invia un messaggio per notificarlo
        self ! ServerStarted
        while (true) {
          val clientSocket = serverSocket.accept()
          self ! NewConnection(clientSocket)
        }
      }
    }.recover {
      // Se c'è un errore nell'avvio, invia un messaggio di fallimento
      case ex => self ! ServerFailed(ex)
    }

    // Comportamento iniziale: l'attore attende che il server sia pronto
    Behaviors.receiveMessage {
      case ServerStarted =>
        context.log.info("Server in ascolto sulla porta 4000")
        // Il server è partito. Passiamo al comportamento operativo.
        runningBehavior(context)

      case ServerFailed(error) =>
        context.log.error("Errore fatale nell'avvio del server. L'attore si fermerà.", error)
        Behaviors.stopped

      case msg =>
        context.log.warn(s"Ricevuto messaggio inatteso [${msg}] durante l'avvio.")
        Behaviors.same
    }
  }

  // Metodo helper che definisce il comportamento dell'attore una volta che il server è attivo
  private def runningBehavior(context: ActorContext[Command]): Behavior[Command] = {
    Behaviors.receiveMessage {
      case NewConnection(socket) =>
        context.log.info(s"Nuova connessione gestita per ${socket.getInetAddress}")

        // Creiamo le risorse dal socket
        val writer = new PrintWriter(socket.getOutputStream, true)
        val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))

        // Creiamo gli attori passando le risorse di cui hanno bisogno
        val socketWriter = context.spawn(SocketWriter(writer), s"writer-${socket.getPort}")
        context.spawn(PlayerActor(socketWriter, reader), s"player-${socket.getPort}")

        Behaviors.same

      case ServerFailed(error) =>
        context.log.error("Errore del server durante l'esecuzione. L'attore si fermerà.", error)
        Behaviors.stopped

      case msg =>
        context.log.warn(s"Ricevuto messaggio inatteso [${msg}] durante l'esecuzione.")
        Behaviors.same
    }
  }
}