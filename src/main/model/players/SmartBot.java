package model.players;

import javafx.util.Pair;
import model.ship.Ship;
import settings.Settings;

import java.util.*;

public class SmartBot extends Player {
    Stack<Pair<Integer, Integer>> initialMoves;
    Stack<Pair<Integer, Integer>> hitMoves;
    Stack<Pair<Integer, Integer>> directions;
    Random rand = new Random();

    public SmartBot() {
        super();
        initialMoves = new Stack<>();
        for (int i = 1; i <= getGridSize(); i++) {
            for (int j = 1; j <= getGridSize(); j++) {
                if ((i + j) % 2 == 1) {
                    initialMoves.add(new Pair<>(i, j));
                }
            }
        }
        shuffle(initialMoves);

        directions = new Stack<>();
        directions.add(new Pair<>(0, 1));
        directions.add(new Pair<>(1, 0));
        directions.add(new Pair<>(0, -1));
        directions.add(new Pair<>(-1, 0));
        hitMoves = new Stack<>();
    }

    // MODIFIES: this, opponent
    // EFFECTS: generate an attack by looking at previous moves.
    @Override
    public int makeAnAttack() {
        int points = -1;
        while (points == -1) {
            if (hitMoves.size() > 1) {
                Pair<Integer, Integer> p = bestMoveCaseConsecutiveHit();
                points = attack(p.getKey(), p.getValue());
                if (points > -1) {
                    break;
                }
            }
            if (hitMoves.size() > 0) {
                points = bestMoveCaseOther();
            } else {
                Pair<Integer, Integer> p = bestMoveCaseEmpty();
                points = attack(p.getKey(), p.getValue());
            }
        }
        if (points > 0) {
            hitMoves.push(new Pair<>(latestMove().getXCoordinate(), latestMove().getYCoordinate()));
        }
        return points;
    }

    // EFFECTS: use a shuffled stack of moves to see which moves to take next if haven't hit anything
    Pair<Integer, Integer> bestMoveCaseEmpty() {
        return initialMoves.pop();
    }

    // EFFECTS: see the latest 2 moves to determine the next move (to hit the full ship)
    Pair<Integer, Integer> bestMoveCaseConsecutiveHit() {
        Pair<Integer, Integer> p = hitMoves.pop();
        Pair<Integer, Integer> q = hitMoves.peek();
        Pair<Integer, Integer> r = next(q, p);
        hitMoves.push(p);
        return r;
    }

    // EFFECTS: see the latest move, and then look to 4 cells around and attack all of them.
    int bestMoveCaseOther() {
        int points;
        shuffle(directions);
        for (Pair<Integer, Integer> dir: directions) {
            points = attack(dir.getKey() + hitMoves.peek().getKey(),
                    dir.getValue() + hitMoves.peek().getValue());
            if (points > -1) {
                return points;
            }
        }
        hitMoves.pop();
        return -1;
    }

    // EFFECTS: given 2 cells: p, q, determine the cell that is the mirror of p on q. return null if p and q are not
    // on the same row/column
    Pair<Integer, Integer> next(Pair<Integer, Integer> p, Pair<Integer, Integer> q) {
        if (!p.getKey().equals(q.getKey()) && !p.getValue().equals(q.getValue())) {
            return null;
        }
        if (p.getKey().equals(q.getKey())) {
            return new Pair<>(p.getKey(), 2 * q.getValue() - p.getValue());
        } else {
            return new Pair<>(2 * q.getKey() - p.getKey(), p.getValue());
        }
    }

    //EFFECTS: shuffle a list using 7n+1 swaps.
    void shuffle(List list) {
        int size = list.size();
        for (int i = 0; i < 7 * size + 1; i++) {
            int u = rand.nextInt(size);
            int v = rand.nextInt(size);
            Object temp = list.get(u);
            list.set(u, list.get(v));
            list.set(v, temp);
        }
    }

    // MODIFIES: this
    // EFFECTS: generate a random set of ships before the game.
    @Override
    public void addAllShips(List<Integer> sizes) {
        List<Ship> ships = new ArrayList<>(0);
        for (Integer i: sizes) {
            ships.add(generateOneShip(i));
        }

        while (!tryAddShips(ships)) {
            ships.clear();
            ships = new ArrayList<>(0);
            for (Integer i: sizes) {
                ships.add(generateOneShip(i));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: try to add a list of
    public boolean tryAddShips(List<Ship> ships) {
        for (Ship ship: ships) {
            if (!addShip(ship)) {
                clearShips();
                return false;
            }
        }
        return true;
    }

    // EFFECTS: uses random to generate one ship.
    @Override
    public Ship generateOneShip(int size) {
        Random rand = new Random();
        boolean horizontal = rand.nextBoolean();
        if (horizontal) {
            return new Ship(size, true,
                    rand.nextInt(Settings.GRID_SIZE - size + 1) + 1,
                    rand.nextInt(Settings.GRID_SIZE + 1) + 1);
        } else {
            return new Ship(size, false,
                    rand.nextInt(Settings.GRID_SIZE + 1) + 1,
                    rand.nextInt(Settings.GRID_SIZE - size + 1) + 1);
        }
    }
}
