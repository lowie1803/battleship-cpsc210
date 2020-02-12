package model;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private int size;
    private boolean isHorizontal;
    private int startingX;
    private int startingY;

    public Ship(int s, boolean h, int x, int y) {
        size = s;
        isHorizontal = h;
        startingX = x;
        startingY = y;
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

    public boolean conflict(Ship other) {
        List<Pair<Integer, Integer>> otherCells = other.allCells();
        for (Pair<Integer, Integer> p: otherCells) {
            if (hit(p.getKey(), p.getValue())) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public boolean horizontal() {
        return isHorizontal;
    }

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingY;
    }
}
