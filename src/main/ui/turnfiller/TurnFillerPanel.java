package ui.turnfiller;

import model.BattleshipGame;
import settings.Settings;
import ui.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TurnFillerPanel extends JPanel {
    App app;
    BattleshipGame game;
    JLabel turnAnnouncer;
    JButton button;
    BufferedImage background;

    public TurnFillerPanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
        setBackground(Settings.INPUT_BACKGROUND_COLOR);

        turnAnnouncer = new JLabel("Press NEXT to continue...");
        turnAnnouncer.setFont(Settings.mainFontLarge);
        turnAnnouncer.setBounds(100, 400, 600, 100);

        button = new JButton("Next");
        button.setFont(Settings.mainFont);
        button.setBackground(Settings.BUTTON_COLOR);
        button.setBounds(250, 500, 100, 50);
        button.addActionListener(e -> {
            app.toGame();
        });

        try {
            background = ImageIO.read(new File(Settings.IMAGE_GAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(turnAnnouncer);
        add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}
