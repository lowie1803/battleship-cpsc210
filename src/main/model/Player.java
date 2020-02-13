package model;

import javafx.util.Pair;
import settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int gridSize;
    private List<Ship> ships;
    private int points;
    private List<Pair<Integer, Integer>> moves;
//    private boolean lost;

    public Player() {
        gridSize = Settings.DEFAULT_GRID_SIZE;
        ships = new ArrayList<>(0);
        points = 0;
        moves = new ArrayList<>(0);
    }

    // MODIFIES: this
    // EFFECTS: add ship to the ship set
    public boolean addShip(Ship ship) {
        List<Pair<Integer, Integer>> cells = ship.allCells();
        for (Pair<Integer, Integer> p: cells) {
            if (p.getKey() < 1 || p.getKey() > gridSize || p.getValue() < 1 || p.getValue() > gridSize) {
                return false;
            }
        }
        for (Ship s: ships) {
            if (ship.conflict(s)) {
                return false;
            }
        }
        ships.add(ship);
        return true;
    }

    // MODIFIES: this
    // REQUIRES: x in [1, gridSize], y in [1, gridSize]
    // EFFECTS: attack, destroy, and gain point if this has destroy a ship from other player.
    //          return -1 if this move is ineligible, >=0 as the number of point gained.
    public int attack(Player other, int x, int y) {
        if ((x < 1) || (x > gridSize) || (y < 1) || (y > gridSize)) {
            return -1;
        }
        for (Pair<Integer, Integer> p: moves) {
            if ((p.getKey() == x) && (p.getValue() == y)) {
                return -1;
            }
        }

        int ret = other.concede(x, y);
        points += ret;
        moves.add(new Pair<>(x, y));
        return ret;
    }

    // MODIFIES: this
    // REQUIRES: x in [1, gridSize], y in [1, gridSize]
    // EFFECTS: returns number of points that this player concede to other team if they attack cell (x, y)
    //          the number of point concede = gridSize - shipSize + bonus
    public int concede(int x, int y) {
        int ret = 0;
        for (Ship s: ships) {
            if (s.hit(x, y) && !s.isDestroyed()) {
                ret += gridSize - s.getSize() + Settings.DEFAULT_BONUS_FOR_EACH_DESTROYED_SHIP;
                s.setDestroyed(true);
            }
        }
        return ret;
    }

    //EFFECTS: return 1 if all ship got destroyed.
    public boolean lostGame() {
        for (Ship s: ships) {
            if (!s.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: output how the board for this player looks like
    public void printBoard() {
        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                int s = 0;
                for (Ship ship: ships) {
                    if (ship.hit(j, i)) {
                        s = ship.getSize();
                    }
                }
                System.out.print(s);
            }
            System.out.println();
        }
        System.out.println();
    }

    public Pair<Integer, Integer> latestMove() {
        return moves.get(moves.size() - 1);
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getPoints() {
        return points;
    }
}
