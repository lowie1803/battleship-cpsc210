package model;

import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import model.players.UITestPlayer;
import persistence.Reader;
import persistence.Writer;
import settings.Settings;
import ui.MoveAlreadyTakenException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BattleshipGame {
    private Player[] player = {null, null};
    Settings settings = new Settings();
    private int gridSize;
    private GameMode gameMode = GameMode.PVP;
    private boolean turnSwitch = false;

    public BattleshipGame() {
        gridSize = Settings.GRID_SIZE;
        initialGame();
    }

    // MODIFIES: this
    // EFFECTS: initiate a game, including letting players arrange the ships and randomize the arrangement for
    // the comp.
    private void initialGame() {
        //TODO: remodify players after testing
        player[0] = new UITestPlayer();
        player[0].addAllShips(settings.defaultSizes);

        //TODO: remodify players after testing
        player[1] = new UITestPlayer();
        player[1].addAllShips(settings.defaultSizes);

        player[0].setOpponent(player[1]);
        player[1].setOpponent(player[0]);

        // TODO: Delete after testing
//        player[0].attack(1, 1);
//        player[0].attack(6, 5);
    }

    private Player currentPlayer() {
        if (turnSwitch) {
            return player[1];
        } else {
            return player[0];
        }
    }

    public void inflictAttack(int x, int y) throws MoveAlreadyTakenException {
        int pts = currentPlayer().attack(x, y);
        if (pts == -1) {
            throw new MoveAlreadyTakenException();
        }

        System.out.println("Gained " + pts + " points!");

        if (gameMode == GameMode.PVP) {
            turnSwitch = !turnSwitch;
        } else {
            player2().makeAnAttack();
        }

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
//        try {
//            player = Reader.readPlayers(new File(Settings.SAVED_GAMES_DATA));
//            runGame();
//        } catch (IOException e) {
////            System.out.println("Error: no saved games.");
//        }
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

    public Player player1() {
        return player[0];
    }

    public Player player2() {
        return player[1];
    }


}