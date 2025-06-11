import akka.actor.typed.ActorSystem
import java.net.{ServerSocket, Socket}
import scala.util.{Try, Failure}
import scala.io.AnsiColor
import scala.concurrent.{Await, ExecutionContext} // Aggiunti
import scala.concurrent.duration._ // Aggiunto

object MUDServer {

  def using[R <: AutoCloseable, A](resource: R)(block: R => A): Try[A] = {
    val result = Try(block(resource))
    Try(resource.close()).failed.foreach { exception =>
      println(s"${AnsiColor.YELLOW}[LOG] Attenzione: risorsa non chiusa. Errore: ${exception.getMessage}${AnsiColor.RESET}")
    }
    result
  }

  def main(args: Array[String]): Unit = {
    println("Avvio del sistema MUD 'dua'...")
    val system: ActorSystem[ServerActor.Command] = ActorSystem(ServerActor(), "dua-mud-system")

    // Otteniamo l'ExecutionContext dal sistema, necessario per Await
    implicit val ec: ExecutionContext = system.executionContext

    try {
      // Questa parte è la novità principale
      println(s"${AnsiColor.CYAN}>>> Server avviato. Premi INVIO per terminare. <<<${AnsiColor.RESET}")

      // 1. Blocca il thread main finché non viene premuto INVIO sulla console
      scala.io.StdIn.readLine()

      println("Avvio procedura di spegnimento...")

      // 2. Avvia lo spegnimento gentile dell'ActorSystem
      system.terminate()

      // 3. Attendi che il sistema sia completamente terminato
      //    Questo assicura che tutti gli attori abbiano finito il loro lavoro prima di uscire.
      //    Diamo un tempo massimo di attesa (es. 30 secondi).
      Await.ready(system.whenTerminated, 30.seconds)

      println("Sistema MUD terminato.")

    } catch {
      case e: Exception =>
        println(s"${AnsiColor.RED}Errore imprevisto nel main: ${e.getMessage}${AnsiColor.RESET}")
        system.terminate()
        Await.ready(system.whenTerminated, 30.seconds)
    }
  }
}