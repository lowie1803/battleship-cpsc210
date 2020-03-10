package model;

import model.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {
    private Ship ship;
    private Ship other;
    private int normalSize = 4;
    @BeforeEach
    void runBefore() {
    }

    @Test
    void testHittingHorizontal() {
        ship = new Ship(normalSize, true, 3, 4);

        assertTrue(ship.hit(4, 4));
        assertTrue(ship.hit(3, 4));
        assertTrue(ship.hit(6, 4));
        assertFalse(ship.hit(7, 4));
        assertFalse(ship.hit(2, 4));
        assertFalse(ship.hit(3, 5));
    }

    @Test
    void testHittingVertical() {
        ship = new Ship(normalSize, false, 3, 4);

        assertTrue(ship.hit(3, 5));
        assertTrue(ship.hit(3, 4));
        assertTrue(ship.hit(3, 7));
        assertFalse(ship.hit(3, 8));
        assertFalse(ship.hit(3, 3));
        assertFalse(ship.hit(4, 4));
    }

    @Test
    void testHVConflict() {
        ship = new Ship(normalSize, true, 3, 4);
        other = new Ship(normalSize - 1, false, 4, 2);
        assertTrue(ship.conflict(other));

        other = new Ship(normalSize - 2, false, 4, 1);
        assertFalse(ship.conflict(other));

        other = new Ship(normalSize - 2, false, 3, 1);
        assertFalse(ship.conflict(other));
    }

    @Test
    void testHHConflict() {
        ship = new Ship(normalSize, true, 3, 4);
        other = new Ship(normalSize - 1, true, 2, 4);
        assertTrue(ship.conflict(other));

        other = new Ship(normalSize - 1, true, 6, 4);
        assertTrue(ship.conflict(other));

        other = new Ship(normalSize - 2, true, 7, 4);
        assertFalse(ship.conflict(other));

        other = new Ship(normalSize - 2, true, 1, 4);
        assertFalse(ship.conflict(other));
    }

    @Test
    void testVVConflict() {
        ship = new Ship(normalSize, false, 3, 4);
        other = new Ship(normalSize - 1, false, 3, 6);
        assertTrue(ship.conflict(other));

        other = new Ship(normalSize - 1, false, 3, 7);
        assertTrue(ship.conflict(other));

        other = new Ship(normalSize - 2, false, 3, 8);
        assertFalse(ship.conflict(other));

        other = new Ship(normalSize - 2, false, 1, 4);
        assertFalse(ship.conflict(other));
    }

    @Test
    void testDestroyShip() {
        ship = new Ship(normalSize, true, 3, 4);
        assertFalse(ship.isDestroyed());
        ship.setDestroyed(true);
        assertTrue(ship.isDestroyed());
    }

    @Test
    void testGetterFunc() {
        ship = new Ship(normalSize, true, 3, 4);
        assertEquals(ship.getSize(), normalSize);
        assertTrue(ship.horizontal());

    }
}
