package ui.drawer;

import javafx.util.Pair;
import model.Move;
import model.players.Player;
import model.ship.Ship;
import settings.ColorSet;
import settings.Settings;

import java.awt.*;

import static settings.Settings.*;

public class ComponentDrawer {
    public static void drawPlayerMoves(Graphics g, int gridSize, Player player, int boardX, int boardY,
                                       Color hit, Color miss) {
        int cellSize = BOARD_PIXEL_SIZE / gridSize;
        for (Move m: player.getMoves()) {
            if (m.getStatus() == Move.Status.HIT) {
                g.setColor(hit);
            } else {
                g.setColor(miss);
            }
            g.fillRoundRect(boardX + (m.getXCoordinate() - 1) * cellSize, boardY + (m.getYCoordinate() - 1) * cellSize,
                    cellSize, cellSize, 20, 20);
        }
    }

    public static void drawPlayerShips(Graphics g, int gridSize, Player player, int boardX, int boardY) {
        for (Ship s: player.getAllShips()) {
            drawShip(g, gridSize, s, boardX, boardY);
        }
    }

    public static void drawShip(Graphics g, int gridSize, Ship s, int boardX, int boardY) {
        int cellSize = BOARD_PIXEL_SIZE / gridSize;
        g.setColor(ColorSet.SHIP);
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
        g.setFont(MAIN_FONT_SMALL);
        g.setColor(ColorSet.GRID_LINE);
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
                    g.setColor(ColorSet.GRID_FILL);
                    g.fillRect(x + cellSize * i, y + cellSize * j, cellSize, cellSize);
                    g.setColor(ColorSet.GRID_LINE);
                    g.drawRect(x + cellSize * i, y + cellSize * j, cellSize, cellSize);
                }
            }
        }
    }


}
