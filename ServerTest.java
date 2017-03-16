import java.net.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServerTest {
    @Test
    public void testSendMessage() {
        Server server = new Server();
        try {
            DatagramSocket socket = new DatagramSocket();
        } catch(SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
        String messageToSend = "Hello World!";
        String returnedMessage = server.sendMessage("172.17.93.232", 3000, messageToSend);
        assertEquals(messageToSend, returnedMessage);
    }
}
