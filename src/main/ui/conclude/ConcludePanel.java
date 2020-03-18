package ui.conclude;

import javafx.util.Pair;
import model.BattleshipGame;
import model.GameMode;
import model.players.Player;
import model.ship.Ship;
import settings.Settings;
import ui.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConcludePanel extends JPanel implements KeyListener {
    private static final int BOARD_X2 = Settings.BOARD_X2;
    private static final int BOARD_X1 = Settings.BOARD_X1;
    private static final int BOARD_Y = Settings.BOARD_Y;
    private static final int BOARD_PIXEL_SIZE = Settings.BOARD_PIXEL_SIZE;

    App app;
    BattleshipGame game;
    BufferedImage backgroundImage;
    JButton backToMenu;

    JLabel winnerAnnouncer;

    public ConcludePanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setLayout(null);
        setBackground(Settings.INPUT_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
        try {
            backgroundImage = ImageIO.read(new File(Settings.IMAGE_GAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        winnerAnnouncer = new JLabel();
        winnerAnnouncer.setFont(Settings.mainFontLarge);
        winnerAnnouncer.setBounds(220, 380, 400, 80);
        winnerAnnouncer.setForeground(Color.RED);

        backToMenu = new JButton("Back to Menu");
        backToMenu.setFont(Settings.mainFont);
        backToMenu.setBounds(200, 450, 200, 50);

        add(winnerAnnouncer);
        add(backToMenu);
        backToMenu.addActionListener(e -> {
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
        drawBoards(g);
        drawPlayerShips(g, game.player2(), BOARD_X2, BOARD_Y);
        drawPlayerShips(g, game.player1(), BOARD_X1, BOARD_Y);
    }

    private void drawPlayerShips(Graphics g, Player player, int boardX, int boardY) {
        for (Ship s: player.getAllShips()) {
            drawShip(g, s, boardX, boardY);
        }
    }

    private void drawShip(Graphics g, Ship s, int boardX, int boardY) {
        int cellSize = BOARD_PIXEL_SIZE / game.getGridSize();
        g.setColor(Color.ORANGE);
        for (Pair p: s.allCells()) {
            g.fillOval(boardX + ((int)p.getKey() - 1) * cellSize,
                    boardY + ((int)p.getValue() - 1) * cellSize, cellSize, cellSize);
        }
    }

    private void drawBoards(Graphics g) {
        drawGrid(g, BOARD_X1, BOARD_Y, BOARD_PIXEL_SIZE, game.getGridSize());
        drawGrid(g, BOARD_X2, BOARD_Y, BOARD_PIXEL_SIZE, game.getGridSize());
    }

    private void drawGrid(Graphics g, int x, int y, int pixelSize, int gridSize) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        int cellSize = pixelSize / gridSize;
        for (int i = -1; i < gridSize; i++) {
            for (int j = -1; j < gridSize; j++) {
                if (i == -1 && j == -1) {
                    continue;
                }
                if (i == -1) {
                    g.drawString("" + (char)('A' + j),
                            x + cellSize * i + cellSize / 2, y + cellSize * (j + 1));
                } else if (j == -1) {
                    g.drawString("" + (char)('1' + i),
                            x + cellSize * i + cellSize / 2, y + cellSize * (j + 1));
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x + cellSize * i, y + cellSize * j, cellSize, cellSize);
                    g.setColor(Color.WHITE);
                    g.drawRect(x + cellSize * i, y + cellSize * j, cellSize, cellSize);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
