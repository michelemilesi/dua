import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.wordspec.AnyWordSpecLike
import scala.concurrent.duration._
import java.io.{BufferedReader, PipedReader, StringReader}

class PlayerActorSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A PlayerActor" should {

    "send a welcome message upon creation" in {
      val writerProbe = createTestProbe[SocketWriter.Command]()
      val fakeBufferedReader = new BufferedReader(new StringReader(""))
      spawn(PlayerActor(writerProbe.ref, fakeBufferedReader))

      // SOLUZIONE: Verifichiamo il tipo di messaggio e poi il suo contenuto.
      val received = writerProbe.expectMessageType[SocketWriter.Write]
      received.line should be("Benvenuto! Sei connesso al MUD 'dua'. Digita 'quit' per uscire.")
    }

    "echo back a received message" in {
      val writerProbe = createTestProbe[SocketWriter.Command]()
      val blockingReader = new BufferedReader(new PipedReader())
      val playerActor = spawn(PlayerActor(writerProbe.ref, blockingReader))

      // Ignoriamo il messaggio di benvenuto
      writerProbe.receiveMessage()

      playerActor ! PlayerActor.LineRead("ciao mondo")

      // SOLUZIONE: Verifichiamo il tipo di messaggio e poi il suo contenuto.
      val received = writerProbe.expectMessageType[SocketWriter.Write]
      received.line should be("Hai digitato: 'ciao mondo'")
    }

    "stop processing messages after receiving the 'quit' command" in {
      val writerProbe = createTestProbe[SocketWriter.Command]()
      val blockingReader = new BufferedReader(new PipedReader())
      val playerActor = spawn(PlayerActor(writerProbe.ref, blockingReader))

      writerProbe.receiveMessage() // Ignora il benvenuto

      playerActor ! PlayerActor.LineRead("quit")

      // SOLUZIONE: Verifichiamo il tipo di messaggio e poi il suo contenuto.
      val received = writerProbe.expectMessageType[SocketWriter.Write]
      received.line should be ("Arrivederci!")

      playerActor ! PlayerActor.LineRead("ci sei ancora?")

      writerProbe.expectNoMessage(200.millis)
    }
  }
}