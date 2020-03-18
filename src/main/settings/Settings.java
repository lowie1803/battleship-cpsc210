package settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    // FRAME SETTINGS
    public static int FRAME_WIDTH = 600;
    public static int FRAME_HEIGHT = 600;

    // IN GAME BOARD CONFIGURATION
    public static final int BOARD_Y = 100;
    public static final int BOARD_X1 = 50;
    public static final int BOARD_X2 = 350;
    public static final int BOARD_PIXEL_SIZE = 200;

    // PANEL SETTINGS

    // FONTS
    public static final Font mainFont = new Font("Comic Sans MS", Font.BOLD, 20);
    public static final Font mainFontSmall = new Font("Comic Sans MS", Font.BOLD, 14);
    public static final Font mainFontLarge = new Font("Comic Sans MS", Font.BOLD, 30);


    // SAVED DATA
    public static String SAVED_GAMES_DATA = "./data/saved.txt";

    // BACKGROUNDS
    public static String IMAGE_MENU = "data/menubackground.png";
    public static String IMAGE_GAME = "data/gamebackground.png";

    // COLORS
    public static Color BUTTON_COLOR = new Color(59, 89, 182);
    public static Color INPUT_BACKGROUND_COLOR = Color.GRAY;


    // Customizables by games
    public static int GRID_SIZE = 8;
    public static int SHIP_DESTRUCTION_BONUS = 3;
    public List<Integer> defaultSizes = new ArrayList<>();

    public Settings() {
        defaultSizes.add(8);
//        defaultSizes.add(3);
//        defaultSizes.add(2);
    }
}
