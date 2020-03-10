package model.players;

import model.ship.Ship;

import java.util.*;

public class HumanPlayer extends Player {
    Scanner input = new Scanner(System.in);

    @Override
    public boolean inGameMenu(Player opponent, int index) {
        Set<String> legalCommand = new HashSet<>();
        legalCommand.add("a");
        legalCommand.add("s");
        legalCommand.add("q");
        String command = stringCommandGetter(
                "Type a to attack, or s to view current score, or q to quit and save...", legalCommand);
        if (command.equals("a")) {
            makeAnAttack(opponent);
        } else if (command.equals("s")) {
            System.out.println("Current score: " + getPoints() + " " + opponent.getPoints());
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void makeAnAttack(Player opponent) {
        System.out.println("Choose one grid to attack!\n(X first, Y follows)");
        int x = input.nextInt();
        int y = input.nextInt();
        int result = attack(opponent, x, y);

        while (result == -1) {
            System.out.println("Illegal move! Please redo!");
            System.out.println("(Either your choice are out of bounds or you have made this move before)");
            x = input.nextInt();
            y = input.nextInt();
            result = attack(opponent, x, y);
        }

        if (result == 0) {
            System.out.println("MISSED!\n");
        } else {
            System.out.println("HIT! Earned " + result + " points!\n");
        }
    }

    @Override
    public void addAllShips(List<Integer> sizes) {
        List<Ship> ships = new ArrayList<>(0);
        for (Integer i: sizes) {
            ships.add(generateOneShip(i));
        }

        while (!tryAddShips(ships)) {
            System.out.println("Conflicted! Please type in the coordinates again!");
            ships.clear();
            ships = new ArrayList<>(0);
            for (Integer i: sizes) {
                ships.add(generateOneShip(i));
            }
        }
    }



    @Override
    public Ship generateOneShip(int size) {
        Set<String> legalCommands = new HashSet<>();
        legalCommands.add("H");
        legalCommands.add("V");

        String commandString = stringCommandGetter("How would ship of size " + size + " orient?\n"
                + "Type H for horizontal. Type V for vertical.", legalCommands);
        boolean isHorizontal = commandString.equals("H");


        if (isHorizontal) {
            System.out.println("Type in the leftmost coordinates for the ship:\nX first, Y follows:");
        } else {
            System.out.println("Type in the upmost coordinates for the ship:\nX first, Y follows:");
        }
        int x = input.nextInt();
        int y = input.nextInt();
        return new Ship(size, isHorizontal, x, y);
    }

    public String stringCommandGetter(String description, Set<String> legalCommand) {
        System.out.println(description);
        String cmd;
        cmd = input.next();
        while (!legalCommand.contains(cmd)) {
            System.out.println("Wrong command! Please retype: ");
            System.out.println(description);
            cmd = input.next();
        }
        return cmd;
    }
}
