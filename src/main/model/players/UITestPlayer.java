package model.players;

import model.ship.Ship;

import java.util.List;

public class UITestPlayer extends Player {
    public UITestPlayer() {
//        addShip(new Ship(8, true, 1, 1));
        addShip(new Ship(3, false, 3, 4));
        addShip(new Ship(2, true, 6, 6));
    }

    @Override
    public void makeAnAttack() {

    }

    @Override
    public void addAllShips(List<Integer> sizes) {

    }

    @Override
    public Ship generateOneShip(int size) {
        return null;
    }
}
