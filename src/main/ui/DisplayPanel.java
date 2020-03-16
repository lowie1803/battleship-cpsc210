package ui;

import javafx.util.Pair;
import model.BattleshipGame;
import model.Move;
import model.players.Player;
import model.ship.Ship;
import settings.Settings;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private static final int BOARD_Y = 100;
    private static final int BOARD_X1 = 50;
    private static final int BOARD_X2 = 350;
    private static final int BOARD_PIXEL_SIZE = 200;
    BattleshipGame game;

    public DisplayPanel(BattleshipGame game) {
        this.game = game;
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, 400));
        setBackground(Color.BLUE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);
    }

    private void drawGame(Graphics g) {
        drawBoards(g);
        if (game.turn1()) {
            drawPlayerShips(g, game.player2(), BOARD_X2, BOARD_Y);
            drawPlayerMoves(g, game.player2(), BOARD_X1, BOARD_Y);
        } else {
            drawPlayerMoves(g, game.player1(), BOARD_X2, BOARD_Y);
            drawPlayerShips(g, game.player1(), BOARD_X1, BOARD_Y);
        }
    }

    private void drawPlayerMoves(Graphics g, Player player, int boardX, int boardY) {
        int cellSize = BOARD_PIXEL_SIZE / game.getGridSize();
        for (Move m: player.getMoves()) {
            if (m.getStatus() == Move.Status.HIT) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRoundRect(boardX + (m.getXCoordinate() - 1) * cellSize, boardY + (m.getYCoordinate() - 1) * cellSize,
                    cellSize, cellSize, 20, 20);
            g.setColor(Color.WHITE);
//            System.out.println("something");
        }
    }

    private void drawPlayerShips(Graphics g, Player player, int boardX, int boardY) {
        for (Ship s: player.getAllShips()) {
            drawShip(g, s, boardX, boardY);
        }
    }

    private void drawShip(Graphics g, Ship s, int boardX, int boardY) {
        //TODO: create image so it legitimately look like ships
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
}
