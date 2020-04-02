package model.players;

import model.ship.Ship;

import java.util.*;

public class HumanPlayer extends Player {
    // EFFECT: this function used to be used in the console app version. Now it's redundant.
    @Override
    public int makeAnAttack() {
        return 0;
    }

    // EFFECT: this function used to be used in the console app version. Now it's redundant.
    @Override
    public void addAllShips(List<Integer> sizes) {
    }

    // EFFECT: this function used to be used in the console app version. Now it's redundant.
    @Override
    public Ship generateOneShip(int size) {
        return null;
    }
}
