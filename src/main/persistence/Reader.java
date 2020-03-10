package persistence;

import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import model.ship.Ship;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {
    public static final String DELIMITER = ",";

    public static Player[] readPlayers(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one account
    private static Player[] parseContent(List<String> fileContent) {
        // TODO: customize this so that there are PvP mode and PvC mode
        Player[] players = {new HumanPlayer(), new RandomizedBot()};
        ArrayList<String> lineComponents = splitString(fileContent.get(0));
        parsePlayer(lineComponents, players[0]);

        lineComponents = splitString(fileContent.get(1));
        parsePlayer(lineComponents, players[1]);
        return players;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components must be in a right format. Which means:
    // - starts with an int, which is the size of the grid
    // - follows by an int, which is the number of current points
    // - follows by an int, which is the number of ships. Let's set this to n
    // - follows by 5*n strings, which is the infos of n ships
    // - follows by an int, which is the number of moves made. Let's set this to m
    // - follows by 2*m ints, denotes infos of m moves.
    private static void parsePlayer(List<String> components, Player player) {
        player.setGridSize(Integer.parseInt(components.get(0)));
        player.setPoints(Integer.parseInt(components.get(1)));
        int shipCount = Integer.parseInt(components.get(2));

        int currentIndex = 3;
        for (int i = 0; i < shipCount; i++) {
            player.addShip(parseShip(components.subList(currentIndex, currentIndex + 5)));
            currentIndex += 5;
        }

        int moveCount = Integer.parseInt(components.get(currentIndex));
        currentIndex++;
        for (int i = 0; i < moveCount; i++) {
            player.addMove(Integer.parseInt(components.get(currentIndex)),
                    Integer.parseInt(components.get(currentIndex + 1)));
            currentIndex += 2;
        }
//        return player;
    }

    // REQUIRES: components has size 5, with first string is an int, follows by a boolean, then 2 ints, then a boolean
    // EFFECTS: returns a ship that parsed from components
    private static Ship parseShip(List<String> components) {
        Ship ship = new Ship(Integer.parseInt(components.get(0)),
                Boolean.parseBoolean(components.get(1)),
                Integer.parseInt(components.get(2)),
                Integer.parseInt(components.get(3)));
        ship.setDestroyed(Boolean.parseBoolean(components.get(4)));
        return ship;
    }

}
