package persistence;

import model.BattleshipGame;
import model.Move;
import model.players.Player;
import model.ship.Ship;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {
    public static final String DELIMITER = ",";
    static int ptr = 0;

    // EFFECTS: returns content of file as a list of strings
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void readGame(BattleshipGame game, File file) throws IOException {
        List<String> fileContent = readFile(file);
        parseContent(game, fileContent);
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one account
    private static void parseContent(BattleshipGame game, List<String> fileContent) {
//        BattleshipGame game = new BattleshipGame();
        ArrayList<String> lineComponents = splitString(fileContent.get(0));
        parseGame(game, lineComponents);
//        return game;
    }

    private static void parseGame(BattleshipGame game, ArrayList<String> lineComponents) {
        game.setSize(Integer.parseInt(lineComponents.get(0)));

        if (lineComponents.get(1).equals("PVP")) {
            game.setPvPGameMode();
        } else if (lineComponents.get(1).equals("PVCE")) {
//            System.out.println(lineComponents.get(1));
            game.setPvCEGameMode();
        }
        if (Boolean.parseBoolean(lineComponents.get(2))) {
            game.changeTurn();
        }
        ptr = 3;
        parsePlayer(game.player1(), lineComponents);
//        ptr++;
        parsePlayer(game.player2(), lineComponents);
//        System.out.println(game.player1().getAllShips().size());
    }

    private static void parsePlayer(Player p, ArrayList<String> lineComponents) {
        int shipCount = Integer.parseInt(lineComponents.get(ptr));
        ptr++;
        for (int i = 0; i < shipCount; i++) {
            p.addShip(parseShip(lineComponents.subList(ptr, ptr + 5)));
            ptr += 5;
        }
        int moveCount = Integer.parseInt(lineComponents.get(ptr));
        ptr++;
        for (int i = 0; i < moveCount; i++) {
            p.addMove(parseMove(lineComponents.subList(ptr, ptr + 3)));
            ptr += 3;
        }
    }

    private static Move parseMove(List<String> components) {
        String status = components.get(2);
        int x = Integer.parseInt(components.get(0));
        int y = Integer.parseInt(components.get(1));
        if (status.equals("HIT")) {
            return new Move(x, y, Move.Status.HIT);
        } else if (status.equals("MISS")) {
            return new Move(x, y, Move.Status.MISS);
        } else {
            return new Move(x, y, Move.Status.BOMBED);
        }
//        return new Move(x, y, status);
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }


    // REQUIRES: components has size 5, with first string is an int, follows by a boolean, then 2 ints, then an int
    // EFFECTS: returns a ship that parsed from components
    private static Ship parseShip(List<String> components) {
//        System.out.println(ptr);
        Ship ship = new Ship(Integer.parseInt(components.get(0)),
                Boolean.parseBoolean(components.get(1)),
                Integer.parseInt(components.get(2)),
                Integer.parseInt(components.get(3)));
        int destroyed = Integer.parseInt(components.get(4));
        for (int i = 0; i < destroyed; i++) {
            ship.setDestroyed();
        }
//        System.out.println(ship);
        return ship;
    }

}
