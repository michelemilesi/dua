import java.net.{ServerSocket, Socket}
import java.io.{PrintWriter, BufferedReader, InputStreamReader}
import scala.util.{Try, Success, Failure}
import scala.io.AnsiColor

object MUDServer {

  def using[R <: AutoCloseable, A](resource: R)(block: R => A): Try[A] = {
    val result = Try(block(resource))

    Try(resource.close()) match {
      case Failure(exception) =>
        println(s"${AnsiColor.YELLOW}[LOG] Attenzione: la risorsa non è stata chiusa correttamente. Errore: ${exception.getMessage}${AnsiColor.RESET}")
      case Success(_) =>
      // Chiusura avvenuta con successo.
    }

    result
  }

  def main(args: Array[String]): Unit = {
    val port = 4000

    val serverExecution = using(new ServerSocket(port)) { serverSocket =>
      println(s"${AnsiColor.GREEN}[*] Il server MUD 'dua' è in ascolto sulla porta $port${AnsiColor.RESET}")

      while (true) {
        val clientSocket = serverSocket.accept()
        println(s"[*] Nuova connessione da: ${clientSocket.getInetAddress}")
        handleClient(clientSocket)
      }
    }

    serverExecution match {
      case Failure(exception) =>
        println(s"${AnsiColor.RED}[!] Errore critico del server: ${exception.getMessage}${AnsiColor.RESET}")
      case Success(_) =>
    }
  }

  def handleClient(socket: Socket): Unit = {
    val clientHandling = using(socket) { client =>
      val out = new PrintWriter(client.getOutputStream, true)
      val in = new BufferedReader(new InputStreamReader(client.getInputStream))

      out.println("Benvenuto nel nostro MUD in Scala! Grandi avventure ti attendono!")
    }

    clientHandling match {
      case Success(_) =>
        println(s"${AnsiColor.GREEN}[*] Connessione con ${socket.getInetAddress} chiusa regolarmente.${AnsiColor.RESET}")
      case Failure(exception) =>
        println(s"${AnsiColor.YELLOW}[!] Connessione con ${socket.getInetAddress} terminata con errore: ${exception.getMessage}${AnsiColor.RESET}")
    }
  }
}