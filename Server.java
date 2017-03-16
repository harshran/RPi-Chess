import java.io.IOException;
import java.net.*;

public class Server {

    // Global variables
    private DatagramSocket sendreceiveSocket;

    // Constuctor for this class
    public Server() {

    }

    // Function to end a message by passing in an ip address as a byte array
    public String sendMessage(String ip, int port, String message)
    throws UnknownHostException, SocketException, IOException {

        // Create a sendreceive socket.
        try {
            sendreceiveSocket = new DatagramSocket(port);
        } catch(SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }

        // Create the packet to send and send it.
        byte[] ipByte = ip.getBytes();
        InetAddress address = InetAddress.getByAddress(ipByte);
        byte[] messageByte = message.getBytes();
        int sendLength = messageByte.length;
        DatagramPacket sendPacket = new DatagramPacket(messageByte, sendLength, address, port);
        sendreceiveSocket.send(sendPacket);
        System.out.println("Packet has been sent to: " + ip);

        // Create the receive packet and wait until it is received.
        byte[] receivedMessage = new byte[100];
        int receivedLength = receivedMessage.length;
        DatagramPacket receivedPacket = new DatagramPacket(receivedMessage, receivedLength);

        try {
            sendreceiveSocket.receive(receivedPacket);
        } catch(IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        System.out.println("Packet has been received from: " + ip);

        // Print out the message from the received packet
        receivedLength = receivedPacket.getLength();
        String contents = new String(receivedMessage, 0, receivedLength);
        System.out.println("Received message: " + contents);

        return contents;
    }
}
