import java.io.IOException;
import java.net.*;

public class Server {

    // Global variables
    private DatagramSocket sendreceiveSocket;

    // Constuctor for this class
    public Server() {

    }

    // Function to send a message by passing in an ip, a port number and a message.
    public String sendMessage(String ip, int port, String message) {

        // Create a sendreceive socket
        try {
            sendreceiveSocket = new DatagramSocket(port);
        } catch(SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }

        // Initialize the ip address
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
        } catch(UnknownHostException uhe) {
            uhe.printStackTrace();
        }

        // Create the send packet
        byte[] messageByte = message.getBytes();
        int sendLength = messageByte.length;
        DatagramPacket sendPacket = new DatagramPacket(messageByte, sendLength, address, port);
        // Send the packet
        try {
            sendreceiveSocket.send(sendPacket);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        // Print some information for the user to know what's going on
        System.out.println("messageByte: " + messageByte);
        System.out.println("sendLength: " + sendLength);
        System.out.println("address: " + address);
        System.out.println("port: " + port);
        System.out.println("Packet has been sent to: " + ip);

        // Create the receive packet
        byte[] receivedMessage = new byte[100];
        int receivedLength = receivedMessage.length;
        DatagramPacket receivedPacket = new DatagramPacket(receivedMessage, receivedLength);
        // Wait until a packet has been received
        try {
            sendreceiveSocket.receive(receivedPacket);
        } catch(IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }

        System.out.println("Packet has been received from: " + ip);

        // Print out the received message
        receivedLength = receivedPacket.getLength();
        String contents = new String(receivedMessage, 0, receivedLength);
        System.out.println("Received message: " + contents);

        return contents;
    }
}
