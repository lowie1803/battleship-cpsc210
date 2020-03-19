package ui.mainmenu;

import model.BattleshipGame;
import settings.ColorSet;
import settings.Settings;
import ui.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {
//    ImageIcon background = new ImageIcon("data/menubackground.png");
    App app;
    JLabel imageAsLabel;
    JButton startButton;
    BattleshipGame game;

    BufferedImage background;

    public MenuPanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
        setLayout(null);
        startButton = new JButton("START GAME!");
        startButton.setBounds(200, 400, 200, 50);
        startButton.setFont(Settings.MAIN_FONT);
        startButton.setBackground(Color.WHITE);
        startButton.addActionListener(e -> {
            app.toMode();
//            System.out.println("I'm here");
        });
        add(startButton);
        try {
            background = ImageIO.read(new File(Settings.IMAGE_MENU));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}
