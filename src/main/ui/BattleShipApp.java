package ui;

import model.Player;
import settings.Settings;
import model.Ship;

import java.util.Random;
import java.util.Scanner;

public class BattleShipApp {
    private Scanner input;
    private Player[] player = {new Player(), new Player()};

    public BattleShipApp() {
        runApp();
    }

    private void runApp() {
        String command;
        input = new Scanner(System.in);

        while (true) {
            System.out.println("Enter s to start new game, q to quit: ");
            command = input.next();
            if (command.equals("s")) {
                initialGame();
            } else if (command.equals("q")) {
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initiate a game, including letting players arrange the ships and randomize the arrangement for
    // the comp.
    private void initialGame() {
        player[0] = new Player();
        player[1] = new Player();
        getInputForShip(player[0], Settings.DEFAULT_SHIP1_SIZE);
        getInputForShip(player[0], Settings.DEFAULT_SHIP2_SIZE);
        getInputForShip(player[0], Settings.DEFAULT_SHIP3_SIZE);

        randomShip(player[1], Settings.DEFAULT_SHIP1_SIZE);
        randomShip(player[1], Settings.DEFAULT_SHIP2_SIZE);
        randomShip(player[1], Settings.DEFAULT_SHIP3_SIZE);

//        player[0].printBoard();
//        player[1].printBoard();

        runGame();
    }

    // MODIFIES: this
    // EFFECTS: letting player and comp take turns to play the game
    private void runGame() {
        int turnCount = 0;
        while (!player[0].lostGame() && !player[1].lostGame()) {
            if (turnCount % 2 == 0) {
                System.out.println("Type a to attack, or s to view current score...");
                String command = input.next();

                while (!command.equals("a") && !command.equals("s")) {
                    System.out.println("Wrong command! Please retype... (a/s)");
                    command = input.next();
                }

                if (command.equals("a")) {
                    makeInputtedAttack(player[0], player[1]);
                } else {
                    System.out.println("Current score: " + player[0].getPoints()
                            + " " + player[1].getPoints());
                    turnCount--; // since player haven't make a turn yet
                }

            } else {
                makeRandomAttack(player[1], player[0]);
            }
            turnCount++;
        }

        concludeGame();
    }

    private void concludeGame() {
        if (player[0].lostGame()) {
            System.out.println("You lost!");
        } else {
            System.out.println("You won!");
        }
        System.out.println("The score is " + player[0].getPoints() + " - " + player[1].getPoints() + "\n\n");
    }


    // MODIFIES: this
    // EFFECTS: make randomized attack for the comp
    private void makeRandomAttack(Player offense, Player defense) {
        Random rand = new Random();
        int x = rand.nextInt(defense.getGridSize()) + 1;
        int y = rand.nextInt(defense.getGridSize()) + 1;
        while (offense.attack(defense, x, y) == -1) {
            x = rand.nextInt(defense.getGridSize()) + 1;
            y = rand.nextInt(defense.getGridSize()) + 1;
        }
        System.out.println("Your opponent chose to attack in " + x + " " + y);
        System.out.println();
    }

    // MODIFIES: this
    // EFFECTS: input an attack for player
    private void makeInputtedAttack(Player offense, Player defense) {
        System.out.println("Choose one grid to attack!\n(X first, Y follows)");
        int x = input.nextInt();
        int y = input.nextInt();
        int result = offense.attack(defense, x, y);

        while (result == -1) {
            System.out.println("Illegal move! Please redo!");
            System.out.println("(Either your choice are out of bounds or you have made this move before)");
            x = input.nextInt();
            y = input.nextInt();
            result = offense.attack(defense, x, y);
        }

        if (result == 0) {
            System.out.println("MISSED!\n");
        } else {
            System.out.println("HIT! Earned " + result + " points!\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: read from input for player to create new ship
    private void getInputForShip(Player p, int length) {
        System.out.println("How would ship of size " + length + " orient?\n"
                + "Type H for horizontal. Type V for vertical.");
        String commandString = input.next();

        while (!commandString.equals("H") && !commandString.equals("V")) {
            System.out.println("Wrong command, please retype!\nType H for horizontal. Type V for vertical.");
            commandString = input.next();
        }
        boolean isHorizontal = commandString.equals("H");


        if (isHorizontal) {
            System.out.println("Type in the leftmost coordinates for the ship:\nX first, Y follows:");
        } else {
            System.out.println("Type in the upmost coordinates for the ship:\nX first, Y follows:");
        }
        int x = input.nextInt();
        int y = input.nextInt();
        while (!p.addShip(new Ship(length, isHorizontal, x, y))) {
            System.out.println("Conflicted! Please type in the coordinates again!\nX first, Y follows");
            x = input.nextInt();
            y = input.nextInt();
        }
    }

    // MODIFIES: this
    // EFFECTS: generate a random ship for comp
    private void randomShip(Player p, int length) {
        Random rand = new Random();
        boolean isHorizontal = rand.nextBoolean();
        int x = rand.nextInt(p.getGridSize()) + 1;
        int y = rand.nextInt(p.getGridSize()) + 1;
        while (!p.addShip(new Ship(length, isHorizontal, x, y))) {
            isHorizontal = rand.nextBoolean();
            x = rand.nextInt(p.getGridSize()) + 1;
            y = rand.nextInt(p.getGridSize()) + 1;
        }
    }
}
