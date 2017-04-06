import java.util.*;

public class Brain {

    // Static constants
    public static final String POS_SYS_IP = "192.168.43.169";
    public static final String CLAW_IP = "192.168.43.224";
    public static final String LCD_IP = "192.168.43.112";
    public static final String CAMERA_IP = "1.1.1.1";
    public static final int POS_SYS_PORT = 2070;
    public static final int CLAW_PORT = 2071;
    public static final int LCD_PORT = 5005;
    public static final int CAMERA_PORT = 3000;

    // Instance variables
    private Server server;
    private EngineUCI engine;
    private GUI gui;

    // Constructor
    public Brain(Server s, EngineUCI e, GUI g) {
        server = s;
        engine = e;
        gui = g;
    }

    // Game start up procedure
    public void startGame() {
        //moveToIdlePosition(); // Send components to starting position
        calibrateCamera(); // Calibrate camera
        playerSetupBoard(); // Tell player to setup pieces on board
        gameLoop(); // Enter main game loop
    }

    // Main game loop
    private void gameLoop() {
        while(!isGameWon()) {
            takeBeforePicture(); // Instruct camera to take before picture
            playerTakesTurn(); // Tell player its their turn
            String playerMove = takeAfterPicture(); // Instruct camera to take after picture
            //verifyLegalMove(playerMove); // Verify move is legal
            updateGUI(playerMove); // Update GUI of player's move
            String computerMove = getComputerMove(playerMove); // Get computer's move
            updateGUI(computerMove); // Update gui of computer's move
            sendPosSysPickup(computerMove); // Send positioning system pickup coordinates
            clawPickup(); // Instruct claw to pickup
            sendPosSysDropOff(computerMove); // Send positioning system drop off coordinates
            clawDropOff(); // Instruct claw to drop off
            moveToIdlePosition(); // Tell positioning system to go to idle position
        }
    }

    public void moveToIdlePosition() {
        // Send "idle" message to positioning system
        // Function blocks until position system has finished moving
        server.sendMessage(POS_SYS_IP, POS_SYS_PORT, "idle");
    }

    public void sendPosSysPickup(String move) {
        // Send to first coordinate of computer's move
        // Function blocks until position system has finished moving
        // Make sure there are 4 characters in the move
        if(!(move.length() == 4)) {
            System.out.println("ERROR in sendPosSysPickup: move is not 4 characters");
        }
        String coord = move.substring(0,2); // Get first coordinate of move
        String index = Integer.toString(translateIndex(coord));
        server.sendMessage(POS_SYS_IP, POS_SYS_PORT, index); // Send pickup coord to positioning system
    }

    public void sendPosSysDropOff(String move) {
        // Send to second coordinate of computer's move
        // Function blocks until position system has finished moving
        // Make sure there are 4 characters in the move
        if(!(move.length() == 4)) {
            System.out.println("ERROR in sendPosSysDropOff: move is not 4 characters");
        }
        String coord = move.substring(2,4); // Get second coordinate of move
        String index = Integer.toString(translateIndex(coord));
        server.sendMessage(POS_SYS_IP, POS_SYS_PORT, index); // Send drop off coord to positioning system
    }

    public void clawPickup() {
        // Send 'pickup' message to claw
        // Function blocks until the claw has picked up
        server.sendMessage(CLAW_IP, CLAW_PORT, "lower");
        server.sendMessage(CLAW_IP, CLAW_PORT, "close");
        server.sendMessage(CLAW_IP, CLAW_PORT, "raise");
    }

    public void clawDropOff() {
        // Send 'drop' message to claw
        // Function blocks until the claw has dropped of
        server.sendMessage(CLAW_IP, CLAW_PORT, "lower");
        server.sendMessage(CLAW_IP, CLAW_PORT, "open");
        server.sendMessage(CLAW_IP, CLAW_PORT, "raise");
    }

    public void playerSetupBoard() {
        // Send 'setup' message to LCD screen
        // Function blocks until player pushes button after setting up board
        server.sendMessage(LCD_IP, LCD_PORT, "setup");
    }

    public void playerTakesTurn() {
        // Send 'playerturn' message to LCD screen
        // Function blocks until player pushes button after making their move
        server.sendMessage(LCD_IP, LCD_PORT, "playerturn");
    }

    public void calibrateCamera() {
        // Send 'calibrate' message to Android camera
        server.sendMessage(CAMERA_IP, CAMERA_PORT, "calibrate");
    }

    public void takeBeforePicture() {
        // Send 'before' message to Android camera
        server.sendMessage(CAMERA_IP, CAMERA_PORT, "before");
    }

    public String takeAfterPicture() {
        // Send 'after' message to Android camera
        // Return player's move
        return server.sendMessage(CAMERA_IP, CAMERA_PORT, "after");
    }

    public String getComputerMove(String playerMove) {
        engine.enterMove(playerMove); // Send player's move to game engine
        return engine.getComputerMove(); // Return computer's move
    }

    public boolean verifyLegalMove(String move) {
        // Verify if player's move is legal
        // Verify move is 4 characters
        if(!(move.length() == 4)) {
            System.out.println("ERROR in verifyLegalMove: move is not 4 characters");
        }

        // Break move down into first and second coordinates
        String first = move.substring(0,2);
        String second = move.substring(2,4);

        // Find delta x and delta y
        int dx = second.charAt(0) - first.charAt(0);
        int dy = second.charAt(1) - first.charAt(1);

        // Get board layout from gui
        ArrayList<Integer> board = gui.getBoard();

        // Determine piece being moved
        int piece = board.get(translateIndex(first));

        // Logic for each piece
        if(piece == PieceIcon.WHITE_PAWN) {
            if(Math.abs(dx) <= 1 && dy == 1) return true;
            if(Math.abs(dx) <= 1 && dy == 2 && first.substring(1,2).equals("2")) return true;
        } else if(piece == PieceIcon.WHITE_ROOK) {
            if(dx == 0 || dy == 0) return true;
        } else if(piece == PieceIcon.WHITE_KNIGHT) {
            if(Math.abs(dx) >= 1 && Math.abs(dy) >= 1 && (Math.abs(dx) + Math.abs(dy)) == 3) return true;
        } else if(piece == PieceIcon.WHITE_BISHOP) {
            if(Math.abs(dx) == Math.abs(dy)) return true;
        } else if(piece == PieceIcon.WHITE_QUEEN) {
            if(dx == 0 || dy == 0 || (Math.abs(dx) == Math.abs(dy))) return true;
        } else if(piece == PieceIcon.WHITE_KING) {
            if(Math.abs(dx) <= 1 && Math.abs(dy) <= 1) return true;
        }
        return false;
    }

    public int translateIndex(String coord) {
        // Convert from board notation to array index
        int index = 8 * (8-Integer.parseInt(coord.substring(1,2)));
        if(coord.substring(0,1).equals("a")) { index += 0; }
        else if(coord.substring(0,1).equals("b")) { index += 1; }
        else if(coord.substring(0,1).equals("c")) { index += 2; }
        else if(coord.substring(0,1).equals("d")) { index += 3; }
        else if(coord.substring(0,1).equals("e")) { index += 4; }
        else if(coord.substring(0,1).equals("f")) { index += 5; }
        else if(coord.substring(0,1).equals("g")) { index += 6; }
        else if(coord.substring(0,1).equals("h")) { index += 7; }
        return index;
    }

    public boolean isGameWon() {
        return false;
    }

    public void updateGUI(String move) {
        String first_coord = move.substring(0,2); // Get first coordinate of move
        String sec_coord = move.substring(2,4); // Get second coordinate of move
        gui.update(translateIndex(first_coord), translateIndex(sec_coord));
    }
}
