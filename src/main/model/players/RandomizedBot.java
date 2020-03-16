package model.players;

import model.ship.Ship;
import settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizedBot extends Player {
    @Override
    public boolean inGameMenu(int index) {
        makeAnAttack();
        return false;
    }

    @Override
    public void makeAnAttack() {
        Random rand = new Random();
        int x = rand.nextInt(opponent.getGridSize()) + 1;
        int y = rand.nextInt(opponent.getGridSize()) + 1;
        while (attack(x, y) == -1) {
            x = rand.nextInt(opponent.getGridSize()) + 1;
            y = rand.nextInt(opponent.getGridSize()) + 1;
        }
    }

    @Override
    public void addAllShips(List<Integer> sizes) {
//        System.out.println("Number of ships in bot: " + sizes.size());
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

    @Override
    // EFFECTS: uses random to generate one ship.
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