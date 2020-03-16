package model.ship;


import javafx.util.Pair;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Saveable {
    // TODO: Add new types of ships so that the game becomes interesting
    private int size;
    private boolean isHorizontal;
    private int startingX;
    private int startingY;
    private int destroyed;

    public Ship(int s, boolean h, int x, int y) {
        size = s;
        isHorizontal = h;
        startingX = x;
        startingY = y;
        destroyed = 0;
    }

    // EFFECTS: returns true if a bullet fire at (x, y) would damage this ship
    public boolean hit(int x, int y) {
        if (isHorizontal) {
            if (y != startingY) {
                return false;
            } else {
                return ((startingX <= x) && (x < startingX + size));
            }
        } else {
            if (x != startingX) {
                return false;
            } else {
                return ((startingY <= y) && (y < startingY + size));
            }
        }
    }

    // EFFECTS: return all cells that this ship covers
    public List<Pair<Integer, Integer>> allCells() {
        List<Pair<Integer, Integer>> ret = new ArrayList<Pair<Integer, Integer>>(0);
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                ret.add(new Pair<Integer, Integer>(startingX + i, startingY));
            }
        } else {
            for (int i = 0; i < size; i++) {
                ret.add(new Pair<Integer, Integer>(startingX, startingY + i));
            }
        }
        return ret;
    }

    // EFFECTS: return conflict between this and the other ship
    public boolean conflict(Ship other) {
        List<Pair<Integer, Integer>> otherCells = other.allCells();
        for (Pair<Integer, Integer> p: otherCells) {
            if (hit(p.getKey(), p.getValue())) {
                return true;
            }
        }
        return false;
    }

    public void setDestroyed() {
        destroyed++;
    }

    public boolean isDestroyed() {
        return destroyed >= size;
    }

    public int getSize() {
        return size;
    }

    public boolean horizontal() {
        return isHorizontal;
    }

    public int pointEarnedFromShip() {
        return (100 * destroyed / size);
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(size);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(isHorizontal);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(startingX);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(startingY);
        printWriter.print(Reader.DELIMITER);

        printWriter.print(destroyed);
        printWriter.print(Reader.DELIMITER);
    }
}
