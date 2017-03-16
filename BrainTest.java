import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class BrainTest {

    @Test
    public void testtranslateIndex1() {
        // Top left corner of board, a8
        Brain b = new Brain(new Server(), new EngineUCI(), new GUI());
        int index = b.translateIndex("a8");
        assertEquals(0, index);
    }

    @Test
    public void testtranslateIndex2() {
        // Bottom right corner of board, h1
        Brain b = new Brain(new Server(), new EngineUCI(), new GUI());
        int index = b.translateIndex("h1");
        assertEquals(63, index);
    }

    @Test
    public void testtranslateIndex3() {
        // Non boarder square, e2
        Brain b = new Brain(new Server(), new EngineUCI(), new GUI());
        int index = b.translateIndex("e2");
        assertEquals(52, index);
    }

    @Test
    public void testtranslateIndex4() {
        // Negative test
        Brain b = new Brain(new Server(), new EngineUCI(), new GUI());
        int index = b.translateIndex("e2");
        assertNotSame(25, index);
    }


    @Test
    public void testverifyLegalMove1() {
        // Move pawn two steps forward, e2e4
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertTrue(b.verifyLegalMove("e2e4"));
    }

    @Test
    public void testverifyLegalMove2() {
        // Move pawn one steps forward, b2b3
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertTrue(b.verifyLegalMove("b2b3"));
    }

    @Test
    public void testverifyLegalMove3() {
        // Negative test for pawn movement
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertFalse(b.verifyLegalMove("e2e5"));
    }

    @Test
    public void testverifyLegalMove4() {
        // Rook movement, h1h5
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertTrue(b.verifyLegalMove("h1h5"));
    }

    @Test
    public void testverifyLegalMove5() {
        // Negative test, rook movement, h1g3
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertFalse(b.verifyLegalMove("h1g3"));
    }

    @Test
    public void testverifyLegalMove6() {
        // Knight movement
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertTrue(b.verifyLegalMove("g1f3"));
    }

    @Test
    public void testverifyLegalMove7() {
        // Negative Knight movement
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertFalse(b.verifyLegalMove("g1f4"));
    }

    @Test
    public void testverifyLegalMove8() {
        // Bishop movment
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertTrue(b.verifyLegalMove("f1c4"));
    }

    @Test
    public void testverifyLegalMove9() {
        // Negative Bishop movment
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertFalse(b.verifyLegalMove("f1c7"));
    }

    @Test
    public void testverifyLegalMove10() {
        // Queen movement
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertTrue(b.verifyLegalMove("d1d6"));
    }

    @Test
    public void testverifyLegalMove11() {
        // Negative Queen movement
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertFalse(b.verifyLegalMove("d1e4"));
    }

    @Test
    public void testverifyLegalMove12() {
        // King movement
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertTrue(b.verifyLegalMove("e1d2"));
    }

    @Test
    public void testverifyLegalMove14() {
        // Negative King movement
        Brain b = new Brain (new Server(), new EngineUCI(), new StubGUI());
        assertFalse(b.verifyLegalMove("e1f3"));
    }


    // Stub gui class
    class StubGUI extends GUI {
        public ArrayList<Integer> getBoard() {
            ArrayList<Integer> list = new ArrayList<Integer> (
                    Arrays.asList(8,9,10,11,12,10,9,8,
                        7,7,7,7,7,7,7,7,
                        0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,
                        1,1,1,1,1,1,1,1,
                        2,3,4,5,6,4,3,2));
            
            return list;
        }
    }
}

