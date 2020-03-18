package ui.drawer;

import javafx.util.Pair;
import model.Move;
import model.players.Player;
import model.ship.Ship;

import java.awt.*;

import static settings.Settings.*;

public class ComponentDrawer {
    public static void drawPlayerMoves(Graphics g, int gridSize, Player player, int boardX, int boardY) {
        int cellSize = BOARD_PIXEL_SIZE / gridSize;
        for (Move m: player.getMoves()) {
            if (m.getStatus() == Move.Status.HIT) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRoundRect(boardX + (m.getXCoordinate() - 1) * cellSize, boardY + (m.getYCoordinate() - 1) * cellSize,
                    cellSize, cellSize, 20, 20);
            g.setColor(Color.WHITE);
        }
    }

    public static void drawPlayerShips(Graphics g, int gridSize, Player player, int boardX, int boardY) {
        for (Ship s: player.getAllShips()) {
            drawShip(g, gridSize, s, boardX, boardY);
        }
    }

    public static void drawShip(Graphics g, int gridSize, Ship s, int boardX, int boardY) {
        int cellSize = BOARD_PIXEL_SIZE / gridSize;
        g.setColor(Color.ORANGE);
        for (Pair p: s.allCells()) {
            g.fillOval(boardX + ((int)p.getKey() - 1) * cellSize,
                    boardY + ((int)p.getValue() - 1) * cellSize, cellSize, cellSize);
        }
    }

    public static void drawBoards(Graphics g, int gridSize) {
        drawGrid(g, BOARD_X1, BOARD_Y, BOARD_PIXEL_SIZE, gridSize);
        drawGrid(g, BOARD_X2, BOARD_Y, BOARD_PIXEL_SIZE, gridSize);
    }

    public static void drawGrid(Graphics g, int x, int y, int pixelSize, int gridSize) {
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
