package ui;

import model.HumanPlayer;
import model.Player;
import model.RandomizedBot;
import persistence.Reader;
import persistence.Writer;
import settings.Settings;
import model.Ship;

import java.io.*;
import java.util.*;

public class BattleShipApp {
    private Settings settings = new Settings();
    private Scanner input = new Scanner(System.in);
    private Player[] player = {new HumanPlayer(), new RandomizedBot()};


    public BattleShipApp() {
        runApp();
    }

    private void runApp() {
        String command;
        Set<String> legalCommand = new HashSet<>();
        legalCommand.add("s");
        legalCommand.add("q");

        File loadGame = new File(Settings.SAVED_GAMES_DATA);

        while (true) {
            if (loadGame.length() == 0) {
                command = stringCommandGetter("Enter s to start new game, q to quit: ", legalCommand);
            } else {
                legalCommand.add("l");
                command = stringCommandGetter("Enter s to start new game, q to quit, l to load game: ", legalCommand);
                legalCommand.remove("l");
            }
            if (command.equals("s")) {
                initialGame();
            } else if (command.equals("l")) {
                loadGame();
            } else if (command.equals("q")) {
                break;
            }
        }
    }

    private String stringCommandGetter(String description, Set<String> legalCommand) {
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

    private void loadGame() {
        try {
            player = Reader.readPlayers(new File(Settings.SAVED_GAMES_DATA));
            runGame();
        } catch (IOException e) {
            System.out.println("Error: no saved games.");
        }
    }

    // MODIFIES: this
    // EFFECTS: initiate a game, including letting players arrange the ships and randomize the arrangement for
    // the comp.
    private void initialGame() {
        player[0] = new HumanPlayer();
        getInputForShip(player[0], Settings.DEFAULT_SHIP1_SIZE);
        getInputForShip(player[0], Settings.DEFAULT_SHIP2_SIZE);
        getInputForShip(player[0], Settings.DEFAULT_SHIP3_SIZE);

        player[1] = new RandomizedBot();
        player[1].generateAllShips(settings.defaultSizes);
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: letting player and comp take turns to play the game
    private void runGame() {
        while (!player[0].lostGame() && !player[1].lostGame()) {

            int currentPlayer = turnCount() % 2;
            int lastPlayer = (currentPlayer ^ 1);
//            System.out.println(turnCount() + " " + currentPlayer + " " + lastPlayer);
            if (turnCount() > 0) {
                lastMoveAnnouncer(player[lastPlayer], lastPlayer + 1);
            } else {
                beginAnnouncer();
            }
            if (currentPlayer == 0) { // not a human player
                boolean quit = inGameMenu();
                if (quit) {
                    return;
                }
            } else {
                player[currentPlayer].makeAnAttack(player[lastPlayer]);
            }
        }
        concludeGame();
    }

    private int turnCount() {
        return player[0].getMoveCount() + player[1].getMoveCount();
    }

    private void beginAnnouncer() {
        System.out.println("GAME BEGINS!");
    }

    // EFFECTS: return true if chose to quit & save
    private boolean inGameMenu() {
        Set<String> legalCommand = new HashSet<>();
        legalCommand.add("a");
        legalCommand.add("s");
        legalCommand.add("q");
        String command = stringCommandGetter(
                "Type a to attack, or s to view current score, or q to quit and save...", legalCommand);
        if (command.equals("a")) {
            makeInputtedAttack(player[0], player[1]);
        } else if (command.equals("s")) {
            System.out.println("Current score: " + player[0].getPoints() + " " + player[1].getPoints());
        } else {
            saveGameToFile();
            return true;
        }
        return false;
    }

    private void lastMoveAnnouncer(Player p, int index) {
        System.out.println("Player " + index + " make move: "
                + p.latestMove().getKey() + " " + p.latestMove().getValue());
    }

    private void saveGameToFile() {
        try {
            Writer writer = new Writer(new File(Settings.SAVED_GAMES_DATA));
            writer.write(player[0]);
            writer.write(player[1]);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save game... Game deleted!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void concludeGame() {
        if (player[0].lostGame()) {
            System.out.println("You lost!");
        } else {
            System.out.println("You won!");
        }
        System.out.println("The score is " + player[0].getPoints() + " - " + player[1].getPoints() + "\n\n");

        clearSavedGame();
    }

    private void clearSavedGame() {
        Writer writer;
        try {
            writer = new Writer(new File(Settings.SAVED_GAMES_DATA));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Do something!");
        }
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
        Set<String> legalCommands = new HashSet<>();
        legalCommands.add("H");
        legalCommands.add("V");

        String commandString = stringCommandGetter("How would ship of size " + length + " orient?\n"
                + "Type H for horizontal. Type V for vertical.", legalCommands);
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
}
