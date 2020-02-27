package model;

import sun.security.krb5.internal.KDCOptions;

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
}
