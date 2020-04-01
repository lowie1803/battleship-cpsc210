package ui.turnfiller;

import model.BattleshipGame;
import model.GameMode;
import settings.AudioSet;
import settings.ColorSet;
import settings.Settings;
import ui.App;
import ui.InGamePanel;

import javax.swing.*;
import java.awt.*;

public class TurnFillerPanel extends InGamePanel {
    JLabel nextToContinue;
    JLabel turnAnnouncer;
    JButton button;

    public TurnFillerPanel(BattleshipGame game, App app) {
        super(game, app);
        modifyContents();
    }

    @Override
    public void modifyContents() {
        modifyNTC();
        modifyTurnAnnouncer();
        modifyButton();
    }

    private void modifyNTC() {
        nextToContinue = new JLabel("Press NEXT to continue...");
        nextToContinue.setFont(Settings.MAIN_FONT_LARGE);
        nextToContinue.setBounds(100, 410, 600, 100);
        add(nextToContinue);
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
        add(button);
    }

    private void modifyTurnAnnouncer() {
        turnAnnouncer = new JLabel();
        turnAnnouncer.setFont(Settings.MAIN_FONT);
        turnAnnouncer.setBounds(15, 400, 600, 30);
        turnAnnouncer.setForeground(ColorSet.ANNOUNCER);
        add(turnAnnouncer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
