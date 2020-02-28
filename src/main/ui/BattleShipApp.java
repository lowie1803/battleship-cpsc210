package ui;

import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import persistence.Reader;
import persistence.Writer;
import settings.Settings;

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
        player[0].addAllShips(settings.defaultSizes);

        player[1] = new RandomizedBot();
        player[1].addAllShips(settings.defaultSizes);
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: letting player and comp take turns to play the game
    private void runGame() {
        while (!player[0].lostGame() && !player[1].lostGame()) {
            int currentPlayer = turnCount() % 2;
            int lastPlayer = (currentPlayer ^ 1);

            if (turnCount() > 0) {
                lastMoveAnnouncer(player[lastPlayer], lastPlayer + 1);
            } else {
                beginAnnouncer();
            }

            boolean quit = player[currentPlayer].inGameMenu(player[lastPlayer], currentPlayer);
            if (quit) {
                saveGameToFile();
                return;
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
            System.out.println("Player 2 won!");
        } else {
            System.out.println("Player 1 won!");
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
}
