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
    public static final String FONT_LINK = "data/gamefont.ttf";
    public static final String FONT_NAME = "Falstin";
    public static final Font MAIN_FONT = new Font(FONT_NAME, Font.BOLD, 20);
    public static final Font MAIN_FONT_SMALL = new Font(FONT_NAME, Font.BOLD, 14);
    public static final Font MAIN_FONT_LARGE = new Font(FONT_NAME, Font.BOLD, 30);


    // SAVED DATA
    public static String SAVED_GAMES_DATA = "./data/saved.txt";

    // BACKGROUNDS
    public static String IMAGE_MENU = "data/menubackground.png";
    public static String IMAGE_GAME = "data/gamebackground.png";

    // Customizables by games
    public static int GRID_SIZE = 6;
    public List<Integer> defaultSizes = new ArrayList<>();

    public Settings() {
        defaultSizes.add(5);
        defaultSizes.add(3);
        defaultSizes.add(2);
    }
}
