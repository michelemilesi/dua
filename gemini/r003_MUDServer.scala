import java.net.{ServerSocket, Socket}
import java.io.{PrintWriter, BufferedReader, InputStreamReader}
import scala.util.{Try, Success, Failure}
import scala.io.AnsiColor

// L'object che contiene la nostra logica principale
object MUDServer {

  /**
   * Helper per la gestione sicura delle risorse (Loan Pattern).
   * Prende una risorsa che può essere chiusa (qualsiasi cosa di tipo AutoCloseable)
   * e un blocco di codice che la usa.
   * Garantisce che resource.close() venga chiamato, sempre e comunque.
   * @param resource La risorsa da gestire (es. un Socket, un file).
   * @param block Il blocco di codice che utilizza la risorsa.
   */
  def using[R <: AutoCloseable, A](resource: R)(block: R => A): Try[A] = {
    Try {
      block(resource)
    } finally {
      // Questo viene eseguito dopo il blocco Try, garantendo la chiusura.
      // Chiudiamo la risorsa anche se il suo utilizzo ha generato un'eccezione.
      resource.close()
    }
  }

  def main(args: Array[String]): Unit = {
    val port = 4000
    
    // Usiamo il nostro nuovo helper 'using' per gestire il ServerSocket.
    // La logica del server è ora tutta contenuta nel blocco di codice.
    // Non dobbiamo più preoccuparci di chiamare serverSocket.close().
    val serverExecution = using(new ServerSocket(port)) { serverSocket =>
      println(s"${AnsiColor.GREEN}[*] Il server MUD è in ascolto sulla porta $port${AnsiColor.RESET}")
      
      // Ciclo infinito per accettare connessioni
      while (true) {
        // Accetto la connessione. Try avvolgerà eventuali errori qui.
        val clientSocket = serverSocket.accept()
        println(s"[*] Nuova connessione da: ${clientSocket.getInetAddress}")
        
        // Gestiamo ogni client.
        // In futuro, questo avverrà in un thread o attore separato.
        handleClient(clientSocket)
      }
    }

    // Il nostro server non dovrebbe mai finire, ma se lo facesse
    // (es. per un errore nell'apertura del socket), lo vedremmo qui.
    serverExecution match {
      case Failure(exception) => 
        println(s"${AnsiColor.RED}[!] Errore critico del server: ${exception.getMessage}${AnsiColor.RESET}")
      case _ => // Non dovrebbe accadere in un ciclo infinito
    }
  }

  // Anche qui usiamo 'using' per il socket del client.
  // La funzione ora ritorna un Try[Unit] che indica successo o fallimento.
  def handleClient(socket: Socket): Unit = {
    val clientHandling = using(socket) { client =>
      val out = new PrintWriter(client.getOutputStream, true)
      val in = new BufferedReader(new InputStreamReader(client.getInputStream))

      out.println("Benvenuto nel nostro MUD in Scala! Grandi avventure ti attendono!")
      // In futuro qui ci sarà un ciclo per leggere i comandi del giocatore
    }
    
    clientHandling match {
      case Success(_) => 
        println(s"[*] Connessione con ${socket.getInetAddress} chiusa regolarmente.")
      case Failure(exception) => 
        println(s"${AnsiColor.YELLOW}[!] Connessione con ${socket.getInetAddress} terminata con errore: ${exception.getMessage}${AnsiColor.RESET}")
    }
  }
}