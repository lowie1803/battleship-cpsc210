package ui.conclude;

import javafx.util.Pair;
import model.BattleshipGame;
import model.GameMode;
import model.players.Player;
import model.ship.Ship;
import settings.AudioSet;
import settings.ColorSet;
import settings.Settings;
import ui.App;
import ui.drawer.ComponentDrawer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConcludePanel extends JPanel {
    private static final int BOARD_X2 = Settings.BOARD_X2;
    private static final int BOARD_X1 = Settings.BOARD_X1;
    private static final int BOARD_Y = Settings.BOARD_Y;

    App app;
    BattleshipGame game;
    BufferedImage backgroundImage;
    JButton backToMenu;

    JLabel winnerAnnouncer;

    public ConcludePanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setLayout(null);
        setBackground(ColorSet.BACKGROUND);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
        try {
            backgroundImage = ImageIO.read(new File(Settings.IMAGE_GAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        winnerAnnouncer = new JLabel();
        winnerAnnouncer.setFont(Settings.MAIN_FONT_LARGE);
        winnerAnnouncer.setBounds(220, 380, 400, 80);
        winnerAnnouncer.setForeground(Color.RED);

        backToMenu = new JButton("Back to Menu");
        backToMenu.setFont(Settings.MAIN_FONT);
        backToMenu.setBounds(200, 450, 200, 50);

        add(winnerAnnouncer);
        add(backToMenu);
        backToMenu.addActionListener(e -> {
            AudioSet.playButtonClick();
            app.toMenu();

        });
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
        g.drawImage(backgroundImage, 0, 0, null);
        winnerAnnouncer.setText(winnerAnnouncement());
        drawGame(g);

    }

    private void drawGame(Graphics g) {
        ComponentDrawer.drawBoards(g, game.getGridSize());
        ComponentDrawer.drawPlayerShips(g, game.getGridSize(), game.player2(), BOARD_X2, BOARD_Y);
        ComponentDrawer.drawPlayerShips(g, game.getGridSize(), game.player1(), BOARD_X1, BOARD_Y);
    }
}
