public class TestEngineWrapper {

    public static void main(String[] args) {

        EngineUCI engine = new EngineUCI("Stockfish/stockfish");
        System.out.println ("Created object");

        engine.startNewGame();
        System.out.println ("Started new game");

        engine.enterMove("e2e4");
        engine.enterMove("e7e5");
        System.out.println ("Entered players move");

        String move = engine.getComputerMove();
        System.out.println ("Got computers move");
        System.out.println (move);

        engine.stopEngine();
        System.out.println ("Shutdown engine");
    }
}
        
