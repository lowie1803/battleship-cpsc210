package settings;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    public static int DEFAULT_SHIP1_SIZE = 8;
    public static int DEFAULT_SHIP2_SIZE = 3;
    public static int DEFAULT_SHIP3_SIZE = 2;
    public static String SAVED_GAMES_DATA = "./data/saved.txt";

    // Customizables by games
    public static int GRID_SIZE = 8;
    public static int SHIP_DESTRUCTION_BONUS = 3;
    public List<Integer> defaultSizes = new ArrayList<>();

    public Settings() {
        defaultSizes.add(8);
        defaultSizes.add(3);
        defaultSizes.add(2);
    }
}
