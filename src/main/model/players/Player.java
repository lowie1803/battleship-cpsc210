package model.players;

import javafx.util.Pair;
import model.Move;
import model.ship.Ship;
import persistence.Reader;
import persistence.Saveable;
import settings.Settings;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Saveable {
    Player opponent;
    private int gridSize;
    private List<Ship> ships;
    private List<Move> moves;

    public Player() {
        gridSize = Settings.GRID_SIZE;
        ships = new ArrayList<>(0);
        moves = new ArrayList<>(0);
    }

    // MODIFIES: this
    // EFFECTS: set the opposing Player for this.
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public abstract int makeAnAttack();

    public abstract void addAllShips(List<Integer> sizes);

    public abstract Ship generateOneShip(int size);

    // EFFECTS: check a new ship if it's able to be added to ships without conflicts
    public boolean ableToAddShip(Ship ship) {
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
        return true;
    }

    // MODIFIES: this
    // EFFECTS: add ship to the ship set. Return true and add the ship if it's able.
    public boolean addShip(Ship ship) {
        if (ableToAddShip(ship)) {
            ships.add(ship);
            return true;
        } else {
            return false;
        }
    }



    // MODIFIES: this
    // REQUIRES: x in [1, gridSize], y in [1, gridSize]
    // EFFECTS: attack, destroy, and gain point if this has destroy a ship from other player.
    //          return -1 if this move is ineligible, >=0 as the number of point gained.
    public int attack(int x, int y) {
        if ((x < 1) || (x > gridSize) || (y < 1) || (y > gridSize)) {
            return -1;
        }
        for (Move p: moves) {
            if ((p.getXCoordinate() == x) && (p.getYCoordinate() == y)) {
                return -1;
            }
        }

        int lastPoint = getPoints();
        Move.Status status = opponent.concede(x, y);
        moves.add(new Move(x, y, status));
        if (status != Move.Status.MISS) {
            return getPoints() - lastPoint;
        }
        return 0;
    }

    // MODIFIES: this
    // REQUIRES: x in [1, gridSize], y in [1, gridSize]
    // EFFECTS: returns if the attack hit or miss
    public Move.Status concede(int x, int y) {
//        int ret = 0;
        for (Ship s: ships) {
            if (s.hit(x, y)) {
                s.setDestroyed();
                return Move.Status.HIT;
            }
        }
        return Move.Status.MISS;
    }

    //EFFECTS: return true if all ship got destroyed.
    public boolean lostGame() {
        for (Ship s: ships) {
            if (!s.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: Returns the latest move of the player
    public Move latestMove() {
        return moves.get(moves.size() - 1);
    }

    // EFFECTS: Returns the size of the grid
    public int getGridSize() {
        return gridSize;
    }

    // EFFECTS: returns the number of points that this gained
    public int getPoints() {
        int ret = 0;
        for (Ship s: opponent.ships) {
            ret += s.pointEarnedFromShip();
        }
        return ret;
    }

    // EFFECTS: returns the number of moves that this made
    public int getMoveCount() {
        return moves.size();
    }

    // EFFECTS: clear all the ships
    public void clearShips() {
        ships.clear();
    }


    // EFFECTS: modifiers uses for testing only
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    // EFFECTS: modifiers uses for testing only
    public List<Ship> getAllShips() {
        return new ArrayList<>(this.ships);
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public List<Move> getMoves() {
        return moves;
    }

    // EFFECTS: save data of this player to file by the following format:
    // - A number n denotes the number of ships, followed by n groups of number, each has information for one ship.
    // (see model/ship/Ship.java for more details)
    // - Followed by a number m denotes the number of moves already made, followed by n groups of number, each has
    // information for one move. (see model/Move.java for more details)
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(ships.size());
        printWriter.print(Reader.DELIMITER);
        for (Ship ship: ships) {
            ship.save(printWriter);
        }
        printWriter.print(moves.size());
        printWriter.print(Reader.DELIMITER);
        for (Move i: moves) {
            i.save(printWriter);
        }
    }


}
