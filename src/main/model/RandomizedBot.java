package model;

import settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizedBot extends Player {
    @Override
    public void makeAnAttack(Player opponent) {
        Random rand = new Random();
        int x = rand.nextInt(opponent.getGridSize()) + 1;
        int y = rand.nextInt(opponent.getGridSize()) + 1;
        while (attack(opponent, x, y) == -1) {
            x = rand.nextInt(opponent.getGridSize()) + 1;
            y = rand.nextInt(opponent.getGridSize()) + 1;
        }
    }

    @Override
    public void generateAllShips(List<Integer> sizes) {
        System.out.println("Number of ships in bot: " + sizes.size());
        List<Ship> ships = new ArrayList<>(0);
        for (Integer i: sizes) {
            ships.add(randomShip(i));
        }

        while (!tryAddShips(ships)) {
            ships.clear();
            ships = new ArrayList<>(0);
            for (Integer i: sizes) {
                ships.add(randomShip(i));
            }
        }
    }

    private boolean tryAddShips(List<Ship> ships) {
        for (Ship ship: ships) {
            if (!addShip(ship)) {
                clearShips();
                return false;
            }
        }
        return true;
    }

    private Ship randomShip(int size) {
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
