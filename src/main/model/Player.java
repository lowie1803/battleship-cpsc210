package model;

import javafx.util.Pair;
import persistence.Reader;
import persistence.Saveable;
import settings.Settings;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Player implements Saveable {
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
        addMove(x, y);
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

    // EFFECTS: Returns the latest move of the player
    public Pair<Integer, Integer> latestMove() {
        return moves.get(moves.size() - 1);
    }

    // EFFECTS: Returns the size of the grid
    public int getGridSize() {
        return gridSize;
    }

    public int getPoints() {
        return points;
    }

    public int getMoveCount() {
        return moves.size();
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void addMove(int x, int y) {
        moves.add(new Pair<>(x, y));
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(gridSize);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(points);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(ships.size());
        printWriter.print(Reader.DELIMITER);
        for (Ship ship: ships) {
            ship.save(printWriter);
        }
        printWriter.print(moves.size());
        printWriter.print(Reader.DELIMITER);
        for (Pair i: moves) {
            printWriter.print(i.getKey());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(i.getValue());
            printWriter.print(Reader.DELIMITER);
        }
        printWriter.println();
    }
}
