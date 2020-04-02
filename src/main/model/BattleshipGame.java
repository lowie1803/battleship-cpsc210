package model;

import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import model.players.SmartBot;
import persistence.Reader;
import persistence.Saveable;
import persistence.Writer;
import settings.Settings;
import ui.ingame.MoveAlreadyTakenException;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import static model.GameMode.*;


public class BattleshipGame implements Saveable {
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

    // MODIFIES: this, player
    // EFFECTS: reset and start a new game.
    public void reset() {
        if (gameMode == PVP) {
            player[0] = new HumanPlayer();
            player[1] = new HumanPlayer();
        } else if (gameMode == PVCE) {
            player[0] = new HumanPlayer();
            player[1] = new RandomizedBot();
        } else {
            player[0] = new HumanPlayer();
            player[1] = new SmartBot();
        }
        player[0].setOpponent(player[1]);
        player[1].setOpponent(player[0]);
        turnSwitch = false;
    }

    // EFFECTS: return the player that is taking the next move in the game
    private Player currentPlayer() {
        if (turnSwitch) {
            return player[1];
        } else {
            return player[0];
        }
    }

    // MODIFIES: this, player
    // EFFECTS: currentPlayer() try to attack opponent on cell (x, y) and then change moves accordingly, either moving
    // on to the next human player or let the bot do their moves.
    public Move inflictAttack(int x, int y) throws MoveAlreadyTakenException {
        int pts = currentPlayer().attack(x, y);
        if (pts == -1) {
            throw new MoveAlreadyTakenException();
        }

        Move ret = currentPlayer().latestMove();

        if (ret.getStatus() != Move.Status.HIT) {
            if (gameMode == PVP) {
                changeTurn();
            } else {
                controlBotAttack(player2());
            }
        }
        return ret;
    }

    // MODIFIES: this, player
    // EFFECTS: control how the Bot plays. Use a loop to continue if the bot successively make moves those hit.
    private void controlBotAttack(Player player) {
        int points = player.makeAnAttack();
        while (points > 0) {
            points = player.makeAnAttack();
        }
    }

    // EFFECTS: check to see if the game has ended
    public boolean gameEnded() {
        return (player[0].lostGame() || player[1].lostGame());
    }

    // EFFECTS: return "1" if player1() won, "2" if player2() won, "0" otherwise
    public String getWinner() {
        if (player[1].lostGame()) {
            return "1";
        } else if (player[0].lostGame()) {
            return "2";
        } else {
            return "0";
        }
    }

    private void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setPvPGameMode() {
        setGameMode(PVP);
        reset();
    }

    public void setPvCEGameMode() {
        setGameMode(PVCE);
        reset();
    }

    public void setPvCHGameMode() {
        setGameMode(PVCH);
        reset();
    }

    // MODIFIES: data/saved.txt
    // EFFECTS: clear the game data
    public void clearSavedGame() {
        Writer writer;
        try {
            writer = new Writer(new File(Settings.SAVED_GAMES_DATA));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECT: Change the value of turnSwitch
    public void changeTurn() {
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

    @Override
    // EFFECTS: save the info of the game by the following format:
    // - first object is int = size of the grids
    // - second object is int denotes the enumeration of the game mode
    // - third object is boolean, denotes whose turn is it currently
    // - followed by a group of object which has the information of player 1
    // - followed by a group of object which has the information of player 2
    // (see model/players/Player.java for more details)
    public void save(PrintWriter printWriter) {
        printWriter.print(gridSize);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(gameMode);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(turnSwitch);
        printWriter.print(Reader.DELIMITER);
        player[0].save(printWriter);
        player[1].save(printWriter);
    }

    public void setSize(int parseInt) {
        gridSize = parseInt;
    }
}
