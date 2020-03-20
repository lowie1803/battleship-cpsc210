package ui.turnfiller;

import model.BattleshipGame;
import model.GameMode;
import settings.AudioSet;
import settings.ColorSet;
import settings.Settings;
import ui.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TurnFillerPanel extends JPanel {
    App app;
    BattleshipGame game;
    JLabel nextToContinue;
    JLabel turnAnnouncer;
    JButton button;
    BufferedImage background;

    public TurnFillerPanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
        setBackground(ColorSet.BACKGROUND);
        modifyNTC();
        modifyTurnAnnouncer();
        modifyButton();
        try {
            background = ImageIO.read(new File(Settings.IMAGE_GAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(nextToContinue);
        add(button);
        add(turnAnnouncer);
    }

    private void modifyNTC() {
        nextToContinue = new JLabel("Press NEXT to continue...");
        nextToContinue.setFont(Settings.MAIN_FONT_LARGE);
        nextToContinue.setBounds(100, 410, 600, 100);
    }

    private void modifyButton() {
        button = new JButton("NEXT");
        button.setFont(Settings.MAIN_FONT);
        button.setBackground(ColorSet.BUTTON);
        button.setBounds(250, 500, 100, 50);
        button.addActionListener(e -> {
            AudioSet.playButtonClick();
            app.toGame();
        });
    }

    private void modifyTurnAnnouncer() {
        turnAnnouncer = new JLabel();
        turnAnnouncer.setFont(Settings.MAIN_FONT);
        turnAnnouncer.setBounds(15, 400, 600, 30);
        turnAnnouncer.setForeground(ColorSet.ANNOUNCER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        turnAnnouncer.setText(turnAnnouncement());
    }

    private String turnAnnouncement() {
        if (game.getGameMode() == GameMode.PVP) {
            if (game.turn1()) {
                return "Player 2's turn";
            } else {
                return "Player 1's turn";
            }
        } else {
            return "It's your turn!";
        }
    }
}
