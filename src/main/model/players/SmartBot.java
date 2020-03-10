package model.players;

import model.ship.Ship;

import java.util.List;

public class SmartBot extends Player {
    // TODO: Implement SmartBot
    @Override
    public boolean inGameMenu(Player opponent, int index) {
        return false;
    }

    @Override
    public void makeAnAttack(Player opponent) {

    }

    @Override
    public void addAllShips(List<Integer> sizes) {

    }

    @Override
    public Ship generateOneShip(int size) {
        return null;
    }
}
