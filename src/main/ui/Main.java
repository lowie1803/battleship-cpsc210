package ui;

import settings.Settings;

import java.awt.*;
import java.io.File;

public class Main {
    // MODIFIES: GraphicsEnvironment.getLocalGraphicsEnvironment()
    // EFFECTS: add a new font using a file path to the system so that the UI can render it.
    private static void addFontToSystem(String path) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addFontToSystem(Settings.FONT_LINK);
        new App();
    }
}
