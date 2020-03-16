package model;

import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import persistence.Reader;
import persistence.Writer;
import settings.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BattleshipGame {
    private Player[] player = {null, null};
    Settings settings = new Settings();
    private int gridSize;
    private GameMode gameMode = GameMode.PVCE;
    private boolean turnSwitch = false;

    public BattleshipGame() {
        gridSize = Settings.GRID_SIZE;
//        initialGame();
    }

    // MODIFIES: this
    // EFFECTS: initiate a game, including letting players arrange the ships and randomize the arrangement for
    // the comp.
    private void initialGame() {

        player[0] = new HumanPlayer();
        player[0].addAllShips(settings.defaultSizes);

        player[1] = new RandomizedBot();
        player[1].addAllShips(settings.defaultSizes);
    }

    // MODIFIES: this
    // EFFECTS: letting player and comp take turns to play the game
    private void runGame() {
        while (!player[0].lostGame() && !player[1].lostGame()) {
            int currentPlayer;
            int lastPlayer;
            if (!turnSwitch) {
                currentPlayer = 0;
            } else {
                currentPlayer = 1;
            }
            lastPlayer = (currentPlayer ^ 1);

            boolean quit = player[currentPlayer].inGameMenu(player[lastPlayer], currentPlayer);
            if (quit) {
                saveGameToFile();
                return;
            }
            changeTurn();
        }
        concludeGame();
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

    private void loadGame() {
        try {
            player = Reader.readPlayers(new File(Settings.SAVED_GAMES_DATA));
            runGame();
        } catch (IOException e) {
//            System.out.println("Error: no saved games.");
        }
    }

    // MODIFIES: this
    // EFFECT: Change the value of turnSwitch
    private void changeTurn() {
        turnSwitch = !turnSwitch;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public boolean turn1() {
        return turnSwitch;
    }

    public int getGridSize() {
        return gridSize;
    }


}
