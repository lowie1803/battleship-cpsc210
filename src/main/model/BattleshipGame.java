package model;

import javafx.util.Pair;
import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import model.players.UITestPlayer;
import persistence.Writer;
import settings.Settings;
import ui.ingame.MoveAlreadyTakenException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static model.GameMode.*;


public class BattleshipGame {
    private Player[] player = {null, null};
    Settings settings = new Settings();
    private int gridSize;
    private GameMode gameMode = PVCE;
    private boolean turnSwitch = false;
    private ArrayList<Integer> listOfSizes;

    public BattleshipGame() {
        gridSize = Settings.GRID_SIZE;
        listOfSizes = new ArrayList<>(settings.defaultSizes);
    }

    public void reset() {
        if (gameMode == PVP) {
            player[0] = new HumanPlayer();
            player[1] = new HumanPlayer();
        } else {
            player[0] = new HumanPlayer();
            player[1] = new RandomizedBot();
        }
        player[0].setOpponent(player[1]);
        player[1].setOpponent(player[0]);
        initialGame();
    }

    // MODIFIES: this
    // EFFECTS: initiate a game, including letting players arrange the ships and randomize the arrangement for
    // the comp.
    private void initialGame() {
        player[0].setOpponent(player[1]);
        player[1].setOpponent(player[0]);
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

        if (gameMode == PVP) {
            changeTurn();
        } else {
            player2().makeAnAttack();
        }
    }

    public boolean gameEnded() {
        return (player[0].lostGame() || player[1].lostGame());
    }

    public String getWinner() {
        if (player[0].lostGame()) {
            return "2";
        } else if (player[1].lostGame()) {
            return "1";
        } else {
            return "0";
        }
    }

    private void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setPvPGameMode() {
        this.gameMode = PVP;
        player[0] = new HumanPlayer();
        player[1] = new HumanPlayer();
        initialGame();
    }

    public void setPvCGameMode() {
        this.gameMode = GameMode.PVCE;
        player[0] = new HumanPlayer();
        player[1] = new RandomizedBot();
        initialGame();
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

    public ArrayList<Integer> getListOfSizes() {
        return listOfSizes;
    }
}
