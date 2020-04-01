package ui;

import model.BattleshipGame;
import settings.ColorSet;
import settings.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class InGamePanel extends JPanel {
    protected App app;
    protected BattleshipGame game;
    Image background;

    public InGamePanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
        setBackground(ColorSet.BACKGROUND);
        try {
            background = ImageIO.read(new File(Settings.IMAGE_GAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void modifyContents();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}
