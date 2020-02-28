package persistence;

import javafx.util.Pair;
import model.players.Player;
import model.Ship;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @Test
    void testParsePlayer() {
        try {
            Player[] players = Reader.readPlayers(new File("./data/savedtestread.txt"));

            assertEquals(players[0].getPoints(), 3);
            assertEquals(players[1].getPoints(), 0);
            assertEquals(players[0].getGridSize(), 8);
            assertEquals(players[1].getGridSize(), 8);
            assertEquals(players[0].latestMove(), new Pair<>(8, 8));
            assertEquals(players[1].latestMove(), new Pair<>(2, 8));

            List<Ship> ships0 = players[0].getAllShips();
            List<Ship> ships1 = players[1].getAllShips();

            assertEquals(ships0.size(), 3);
            assertEquals(ships1.size(), 3);

            assertEquals(ships0.get(0).getSize(), 8);
            assertTrue(ships0.get(0).horizontal());
            List<Pair<Integer, Integer>> allCells0 = ships0.get(1).allCells();
            assertEquals(allCells0.get(0), new Pair<>(5, 5));

            List<Pair<Integer, Integer>> allCells1 = ships1.get(0).allCells();
            assertEquals(allCells1.get(2), new Pair<>(8, 3));

        } catch (IOException e) {
            fail("Should not have exception!");
        }
    }

    @Test
    void testException() {
        try {
            Player[] players = Reader.readPlayers(new File("./data/nonexistentfile.txt"));
        } catch (IOException e) {
            // Good
        }
    }
}
