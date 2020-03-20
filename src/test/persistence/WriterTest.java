//package persistence;
//
//import javafx.util.Pair;
//import model.players.HumanPlayer;
//import model.players.Player;
//import model.players.RandomizedBot;
//import model.ship.Ship;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class WriterTest {
//    private static final String TEST_FILE = "./data/savedtestwrite.txt";
//    private Writer testWriter;
//    private Player[] players;
//
//    @BeforeEach
//    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
//        testWriter = new Writer(new File(TEST_FILE));
//        players = new Player[]{new HumanPlayer(), new RandomizedBot()};
//        assertTrue(players[0].addShip(new Ship(8, true, 1, 1)));
//        assertTrue(players[0].addShip(new Ship(3, false, 5, 5)));
//        assertTrue(players[0].addShip(new Ship(2, true, 6, 6)));
//
//        assertTrue(players[1].addShip(new Ship(8, false, 8, 1)));
//        assertTrue(players[1].addShip(new Ship(3, true, 1, 1)));
//        assertTrue(players[1].addShip(new Ship(2, false, 7, 1)));
////        players[0].addMove(1, 3);
////        players[0].addMove(8, 8);
//
////        players[1].addMove(4, 2);
////        players[1].addMove(2,8);
//    }
//
//    @Test
//    void testWrite() {
//        testWriter.write(players[0]);
//        testWriter.write(players[1]);
//        testWriter.close();
//
//        try {
//            Player[] players = Reader.readPlayers(new File("./data/savedtestwrite.txt"));
//
//            assertEquals(players[0].getPoints(), 3);
//            assertEquals(players[1].getPoints(), 0);
//            assertEquals(players[0].getGridSize(), 8);
//            assertEquals(players[1].getGridSize(), 8);
//            assertEquals(players[0].latestMove(), new Pair<>(8, 8));
//            assertEquals(players[1].latestMove(), new Pair<>(2, 8));
//
//            List<Ship> ships0 = players[0].getAllShips();
//            List<Ship> ships1 = players[1].getAllShips();
//
//            assertEquals(ships0.size(), 3);
//            assertEquals(ships1.size(), 3);
//
//            assertEquals(ships0.get(0).getSize(), 8);
//            assertTrue(ships0.get(0).horizontal());
//            List<Pair<Integer, Integer>> allCells0 = ships0.get(1).allCells();
//            assertEquals(allCells0.get(0), new Pair<>(5, 5));
//
//            List<Pair<Integer, Integer>> allCells1 = ships1.get(0).allCells();
//            assertEquals(allCells1.get(2), new Pair<>(8, 3));
//
//        } catch (IOException e) {
//            fail("Should not have exception!");
//        }
//    }
//}
