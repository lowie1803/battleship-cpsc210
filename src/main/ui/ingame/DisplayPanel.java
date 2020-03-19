package ui.ingame;

import model.BattleshipGame;
import settings.ColorSet;
import settings.Settings;
import ui.App;
import ui.drawer.ComponentDrawer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static settings.ColorSet.*;

public class DisplayPanel extends JPanel {
    private static final int BOARD_Y = Settings.BOARD_Y;
    private static final int BOARD_X1 = Settings.BOARD_X1;
    private static final int BOARD_X2 = Settings.BOARD_X2;
    private static final int BOARD_PIXEL_SIZE = Settings.BOARD_PIXEL_SIZE;
    BattleshipGame game;
    App app;
    BufferedImage background;

    public DisplayPanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, 400));
        setBackground(Color.BLUE);
        try {
            background = ImageIO.read(new File(Settings.IMAGE_GAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        drawGame(g);
    }

    private void drawGame(Graphics g) {
        ComponentDrawer.drawBoards(g, game.getGridSize());
        if (game.turn1()) {
            ComponentDrawer.drawPlayerShips(g, game.getGridSize(), game.player2(), BOARD_X2, BOARD_Y);
            ComponentDrawer.drawPlayerMoves(g, game.getGridSize(), game.player2(), BOARD_X1, BOARD_Y,
                    SHOT_FOR_HIT, SHOT_FOR_MISSED);

            ComponentDrawer.drawPlayerMoves(g, game.getGridSize(), game.player1(), BOARD_X2, BOARD_Y,
                    SHOT_AGAINST_HIT, SHOT_AGAINST_MISSED);
        } else {
            ComponentDrawer.drawPlayerShips(g, game.getGridSize(), game.player1(), BOARD_X1, BOARD_Y);
            ComponentDrawer.drawPlayerMoves(g, game.getGridSize(), game.player1(), BOARD_X2, BOARD_Y,
                    SHOT_FOR_HIT, SHOT_FOR_MISSED);

            ComponentDrawer.drawPlayerMoves(g, game.getGridSize(), game.player2(), BOARD_X1, BOARD_Y,
                    SHOT_AGAINST_HIT, SHOT_AGAINST_MISSED);
        }
    }
}
