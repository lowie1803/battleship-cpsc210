package model;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import settings.Settings;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player[] player = {new HumanPlayer(), new RandomizedBot()};

    @BeforeEach
    void runBefore() {
        assertTrue(player[0].addShip(new Ship(8, true, 1, 1)));
        assertTrue(player[0].addShip(new Ship(2, true, 4, 2)));
        assertTrue(player[0].addShip(new Ship(3, false, 4, 3)));

        assertTrue(player[1].addShip(new Ship(8, false, 8, 1)));
        assertTrue(player[1].addShip(new Ship(2, true, 2, 2)));
        assertTrue(player[1].addShip(new Ship(3, false, 4, 6)));
    }

    @Test
    void testInitialize() {
        player[0] = new HumanPlayer();
        player[1] = new RandomizedBot();

        assertTrue(player[0].addShip(new Ship(8, true, 1, 1)));
        assertTrue(player[0].addShip(new Ship(2, true, 4, 2)));
        assertTrue(player[0].addShip(new Ship(3, false, 4, 3)));

        assertTrue(player[1].addShip(new Ship(8, false, 8, 1)));
        assertTrue(player[1].addShip(new Ship(2, true, 2, 2)));
        assertTrue(player[1].addShip(new Ship(3, false, 4, 6)));
    }

    @Test
    void testAddShip() {
        //out of bound
        assertFalse(player[0].addShip(new Ship(2, true, 8, 8)));
        //endpoint conflict with 1st ship
        assertFalse(player[0].addShip(new Ship(2, false, 8, 1)));
        //check if addShip adds ship if it can't be added
        assertTrue(player[0].addShip(new Ship(2, false, 8, 2)));
    }

    @Test
    void testConcede() {
        //ship size 8, point = 8 - 8 + 3 = 3
        assertEquals(player[0].concede(3, 1), 3);
        //missed
        assertEquals(player[0].concede(8, 8), 0);
        //ship size 2, point 8 - 2 + 3 = 9
        assertEquals(player[0].concede(5, 2), 9);

        //this ship is destroyed already
        assertEquals(player[0].concede(8, 1), 0);
        assertEquals(player[0].concede(3, 1), 0);
    }

    @Test
    void testAttack() {
        //0 hits 1's size-8 ship, earn 3 points
        assertEquals(player[0].attack(player[1], 8, 6), 3);
        //1 hits 0's size-3 ship, earn 8 points
        assertEquals(player[1].attack(player[0], 4, 5), 8);

        //0 misses
        assertEquals(player[0].attack(player[1], 5, 5), 0);
        //1 hits the corpse of 0's size 3 ship, earn 0 points
        assertEquals(player[1].attack(player[0], 4, 8), 0);

        //0 hits 1's size-2 ship, earn 9 points
        assertEquals(player[0].attack(player[1], 3, 2), 9);
        //1 make an illegal move
        assertEquals(player[1].attack(player[0], 9, 3), -1);
        //1 make another illegal move
        assertEquals(player[1].attack(player[0], 0, 6), -1);
        //1 make a move that he did before (which hit)
        assertEquals(player[1].attack(player[0], 4, 5), -1);
        //1 misses
        assertEquals(player[1].attack(player[0], 4, 6), 0);

        //0 make a move that he did before (which missed)
        assertEquals(player[0].attack(player[1], 5, 5), -1);

        //points check
        assertEquals(player[0].getPoints(), 12);
        assertEquals(player[1].getPoints(), 8);

        //finalize the game
        assertFalse(player[1].lostGame());
        assertEquals(player[0].attack(player[1], 4, 6), 8);
        assertTrue(player[1].lostGame());

        //misc
        assertEquals(player[0].latestMove(), new Pair<>(4, 6));
        assertEquals(player[0].getGridSize(), 8);
        assertEquals(player[0].getMoveCount(), 4);
//        assertEquals(player[0]);
        player[0].setPoints(3);
        assertEquals(player[0].getPoints(), 3);
        assertEquals(player[0].getGridSize(), Settings.DEFAULT_GRID_SIZE);
    }
}
