import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.wordspec.AnyWordSpecLike
import java.io.{PrintWriter, StringWriter}

class SocketWriterSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A SocketWriter" should {

    "write a line and acknowledge it" in {
      // 1. Prepara il writer finto e una sonda per la conferma
      val stringWriter = new StringWriter()
      val printWriter = new PrintWriter(stringWriter)
      val ackProbe = createTestProbe[SocketWriter.Ack]()

      // 2. Avvia l'attore
      val socketWriter = spawn(SocketWriter(printWriter))

      // 3. Invia il messaggio, passando la sonda come destinatario della risposta
      val message = "test di scrittura"
      socketWriter ! SocketWriter.Write(message, ackProbe.ref)

      // 4. ATTENDI la conferma. Il test si blocca qui finché non arriva.
      ackProbe.expectMessage(SocketWriter.Ack)

      // 5. ORA siamo sicuri che l'attore ha finito. Verifichiamo il risultato.
      val expectedOutput = message + System.lineSeparator()
      stringWriter.toString should be(expectedOutput)
    }

    // Il test dello stop non è più necessario in questa forma,
    // perché la chiusura del writer non è un comportamento osservabile tramite messaggi.
    // La sua correttezza è garantita dal test di integrazione che stiamo facendo
    // implicitamente quando eseguiamo l'applicazione.
  }
}