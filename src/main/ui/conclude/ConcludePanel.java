package ui.conclude;

import model.BattleshipGame;
import model.GameMode;
import settings.AudioSet;
import settings.Settings;
import ui.App;
import ui.InGamePanel;
import ui.drawer.ComponentDrawer;

import javax.swing.*;
import java.awt.*;

public class ConcludePanel extends InGamePanel {
    //TODO: add score display
    private static final int BOARD_X2 = Settings.BOARD_X2;
    private static final int BOARD_X1 = Settings.BOARD_X1;
    private static final int BOARD_Y = Settings.BOARD_Y;

    JButton backToMenu;

    JLabel winnerAnnouncer;

    public ConcludePanel(BattleshipGame game, App app) {
        super(game, app);
        modifyContents();
    }

    @Override
    public void modifyContents() {
        winnerAnnouncer = new JLabel();
        winnerAnnouncer.setFont(Settings.MAIN_FONT_LARGE);
        winnerAnnouncer.setBounds(220, 380, 400, 80);
        winnerAnnouncer.setForeground(Color.RED);

        backToMenu = new JButton("Back to Menu");
        backToMenu.setFont(Settings.MAIN_FONT);
        backToMenu.setBounds(200, 450, 200, 50);
        backToMenu.addActionListener(e -> {
            AudioSet.playButtonClick();
            app.toMenu();
        });

        add(winnerAnnouncer);
        add(backToMenu);
    }

    private String winnerAnnouncement() {
        if (game.getGameMode() == GameMode.PVP) {
            return "PLAYER " + game.getWinner() + " WINS!";
        } else {
            if (game.getWinner().equals("1")) {
                return "YOU WON!";
            } else {
                return "YOU LOST!";
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        winnerAnnouncer.setText(winnerAnnouncement());
        drawGame(g);

    }

    private void drawGame(Graphics g) {
        ComponentDrawer.drawBoards(g, game.getGridSize());
        ComponentDrawer.drawPlayerShips(g, game.getGridSize(), game.player2(), BOARD_X2, BOARD_Y);
        ComponentDrawer.drawPlayerShips(g, game.getGridSize(), game.player1(), BOARD_X1, BOARD_Y);
    }
}
