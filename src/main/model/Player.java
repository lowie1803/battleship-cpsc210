package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int gridSize;
    private List<Ship> ships;
    private int points;
    private List<Pair<Integer, Integer>> moves;

    public Player() {
        gridSize = Settings.DEFAULT_GRID_SIZE;
        ships = new ArrayList<>(0);
        points = 0;
        moves = new ArrayList<>(0);
    }

    // MODIFIES: this
    // EFFECTS: add 1 ship to the ship set
    public boolean addShip(Ship ship) {
        List<Pair<Integer, Integer>> cells = ship.allCells();
        for (Pair<Integer, Integer> p: cells) {
            if (p.getKey() < 1 || p.getKey() > gridSize || p.getValue() < 1 || p.getValue() > gridSize) {
                return false;
            }
        }
        ships.add(ship);
        return true;
    }

    // MODIFIES: this
    // REQUIRES: x in [1, gridSize], y in [1, gridSize]
    // EFFECTS: returns number of points that
    public int concede(int x, int y) {
        return 0;
    }
}
