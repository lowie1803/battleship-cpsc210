package ui;

import model.BattleshipGame;
import settings.Settings;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
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
        // TODO: draw stuff inside game
    }

    private void drawBoards(Graphics g) {
        drawGrid(g, 50, 100, 200, game.getGridSize());
        drawGrid(g, 350, 100, 200, game.getGridSize());
    }

    private void drawGrid(Graphics g, int x, int y, int pixelSize, int gridSize) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", 0, 16));
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
