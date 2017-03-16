import java.io.*;
public class EngineUCI {
    private static final int WAIT_TIME = 500;

    private Process engineProcess;
    private BufferedReader processReader;
    private OutputStreamWriter processWriter;
    private String pastMoves;

    public EngineUCI(){
    }

    // Constructor
    // Takes path to game engine as parameter
    public EngineUCI(String exePath) {
        try {
            // Start game engine
            engineProcess = Runtime.getRuntime().exec(exePath);

            // Set up communication lines with game engine
            processReader = new BufferedReader(new InputStreamReader(
                        engineProcess.getInputStream()));
            processWriter = new OutputStreamWriter(
                    engineProcess.getOutputStream());

        } catch (Exception e) {
            System.out.println ("FATAL ERROR: Failed to start engine process");
            System.out.println (e);
        }

        System.out.println("sending uci");
        sendCommand("uci");
        System.out.println("sending options");
        sendCommand("setoption name Hash value 128");

        pastMoves = "";
    }

    // Tells chess engine to start a new game
    public void startNewGame() {
        System.out.println ("sending new game");
        sendCommand("ucinewgame");

        pastMoves = "";
    }

    // Keep track of users moves
    public void enterMove(String move) {
        if (pastMoves.equals(""))
            pastMoves = move;
        else 
            pastMoves = pastMoves + " " + move;
    }

    // Get computers move
    public String getComputerMove() {
        // Send previous moves 
        sendCommand("position startpos moves " + pastMoves);

        // Start calculating computer's best move
        sendCommand("go infinite");
        try {
            // Give the engine time to calculate
            Thread.sleep(WAIT_TIME);
        }catch (Exception e) {}
        
        // Tell the engine to stop calculating best move
        sendCommand("stop");

        // Get best move
        String bestMove = getResponse();
        bestMove = bestMove.split("bestmove")[1].split(" ")[1];

        // Add to list of prev moves
        enterMove(bestMove);

        return bestMove;
    }

    // Shut down game engine
    public void stopEngine() {
        try {
            sendCommand ("quit");
            processReader.close();
            processWriter.close();
        } catch (IOException e) {
            System.out.println ("ERROR: Failed to shut down engine");
            System.out.println (e);
        }
    }
    // Sends command to chess engine
    private void sendCommand(String cmd) {
        try {
            processWriter.write(cmd + "\n");
            processWriter.flush();
        } catch (IOException e) {
            System.out.println ("ERROR: Failed to send command to engine");
            System.out.println (e);
        }
    }

    // Gets response from chess engine
    private String getResponse() {
        StringBuffer buffer = new StringBuffer();
        try { 
            String text;
            while (processReader.ready()){
                text = processReader.readLine();
                buffer.append(text + "\n");
            }
        } catch (Exception e) {
            System.out.println ("ERROR: Failed to get response from engine");
            System.out.println(e);
        }
        
        System.out.println("Out of loop");
        return buffer.toString();
    }

}
