import java.io.IOException;
import java.net.*;

public class Server {

    // Global variables
    private DatagramSocket sendreceiveSocket;

    // Constuctor for this class
    public Server() {

    }

    // Function to end a message by passing in an ip address as a byte array
    public String sendMessage(String ip, int port, String message) {

        // Create a sendreceive socket.
        try {
            sendreceiveSocket = new DatagramSocket(port);
        } catch(SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }

        // Create the packet to send and send it.
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
            // address = InetAddress.getLocalHost();
        } catch(UnknownHostException uhe) {
            uhe.printStackTrace();
        }
        byte[] messageByte = message.getBytes();
        int sendLength = messageByte.length;
        DatagramPacket sendPacket = new DatagramPacket(messageByte, sendLength, address, port);
        System.out.println("messageByte: " + messageByte);
        System.out.println("sendLength: " + sendLength);
        System.out.println("address: " + address);
        System.out.println("port: " + port);
        try {
            sendreceiveSocket.send(sendPacket);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
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
