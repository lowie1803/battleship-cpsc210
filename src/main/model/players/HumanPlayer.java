package model.players;

import model.ship.Ship;

import java.util.*;

public class HumanPlayer extends Player {
    @Override
    public int makeAnAttack() {
        return 0;
    }

    @Override
    public void addAllShips(List<Integer> sizes) {
    }

    @Override
    public Ship generateOneShip(int size) {
        return null;
    }
}
