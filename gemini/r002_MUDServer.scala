import java.net.{ServerSocket, Socket}
import java.io.{PrintWriter, BufferedReader, InputStreamReader}

// Un "object" in Scala è come una classe di cui esiste una sola istanza (singleton).
// È perfetto per contenere il punto di ingresso della nostra applicazione, il metodo "main".
object MUDServer {

  def main(args: Array[String]): Unit = {
    
    // --- Configurazione del Server ---
    val port = 4000 // La porta su cui ascoltare

    // 1. Creiamo il ServerSocket.
    //    Il costrutto "try-with-resources" non esiste in Scala, quindi useremo
    //    un blocco try/finally per assicurarci che il socket venga sempre chiuso.
    val serverSocket = new ServerSocket(port)
    println(s"[*] Il server MUD è in ascolto sulla porta $port")

    try {
      // --- Ciclo Principale del Server ---
      // Questo ciclo while(true) accetta le connessioni in arrivo.
      while (true) {
        // 2. Accettiamo una nuova connessione (bloccante).
        //    "accept()" attende finché un client non si connette.
        val clientSocket = serverSocket.accept()
        println(s"[*] Nuova connessione da: ${clientSocket.getInetAddress}:${clientSocket.getPort}")
        
        // Per ora, gestiamo la connessione qui direttamente.
        // In futuro, affideremo ogni client a un "attore" o a un thread separato.
        handleClient(clientSocket)
      }
    } finally {
      // 3. Questo blocco viene eseguito sempre, anche in caso di errore,
      //    per garantire che il socket principale del server venga chiuso.
      serverSocket.close()
      println("[*] Server spento.")
    }
  }

  // Una funzione per gestire la logica di un singolo client
  def handleClient(socket: Socket): Unit = {
    // Usiamo try/finally anche qui per chiudere sempre la connessione del client.
    try {
      // 4. Prepariamo i canali di comunicazione: uno per scrivere (out) e uno per leggere (in).
      val out = new PrintWriter(socket.getOutputStream, true) // "true" per auto-flush
      val in = new BufferedReader(new InputStreamReader(socket.getInputStream))

      // 5. Inviamo il messaggio di benvenuto.
      out.println("Benvenuto nel nostro MUD in Scala! Grandi avventure ti attendono!")

      // In futuro qui leggeremo i comandi del giocatore con in.readLine()
      
    } finally {
      // 6. Chiudiamo la connessione con questo specifico client.
      println(s"[*] Connessione con ${socket.getInetAddress} chiusa.")
      socket.close()
    }
  }
}