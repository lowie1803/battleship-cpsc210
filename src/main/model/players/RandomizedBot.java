package model.players;

import model.ship.Ship;
import settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizedBot extends Player {
    // MODIFIES: this, opponent
    // EFFECTS: generate a random attack to opponent's board of ship.
    @Override
    public int makeAnAttack() {
        Random rand = new Random();
        int x = rand.nextInt(opponent.getGridSize()) + 1;
        int y = rand.nextInt(opponent.getGridSize()) + 1;
        int points = attack(x, y);
        while (points == -1) {
            x = rand.nextInt(opponent.getGridSize()) + 1;
            y = rand.nextInt(opponent.getGridSize()) + 1;
            points = attack(x, y);
        }
        return points;
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
    // EFFECTS: try to add a list of ship to see if there's any conflicts. Return true if there are no conflicts.
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
