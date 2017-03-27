 
import java.net.*;
import java.io.*;

public class ClawCli {
    
    protected Brain b;
    protected Server s;
    
   public ClawCli(Brain br, Server se){
   
       b = br;
       s = se;
       
   }
    
   public static void main(String [] args) throws Exception {
       String sentence;
       String modifiedSentence;
       BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

       Socket clientSocket = new Socket("localhost", 3000);
       DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
       BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

       sentence = inFromUser.readLine();
       outToServer.writeBytes(sentence + '\n');
       modifiedSentence = inFromServer.readLine();
       System.out.println(modifiedSentence);
       clientSocket.close();
    }
}