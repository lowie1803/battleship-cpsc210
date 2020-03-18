package ui.playerconfig;

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static settings.Settings.*;

public class ConfigPanel extends JPanel {
    private static final int SPINNER_HEIGHT = 30;
    private static final int SPINNER_WIDTH = 50;
    App app;
    BattleshipGame game;

    JButton addButton;
    JSpinner spOrientation;
    JSpinner spRow;
    JSpinner spColumn;
    BufferedImage background;

    JLabel questionLabel;
//    JLabel rowLabel;
    JLabel coordinateLabel;
    JLabel orientationLabel;

    int index = 0;

    public ConfigPanel(BattleshipGame game, App app) {
        this.game = game;
        this.app = app;
        setBackground(Settings.INPUT_BACKGROUND_COLOR);
        setLayout(null);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));

        modifySpinners();
        modifyButton();
        modifyLabels();
        addComponents();

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
        questionLabel.setText(questionString());
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

    private void buttonAction() {
        Player player;
        if (game.getGameMode() == GameMode.PVP && index >= game.getListOfSizes().size()) {
            player = game.player2();
        } else {
            player = game.player1();
        }
        int size = game.getListOfSizes().get(index % game.getListOfSizes().size());
        boolean addable = player.addShip(new Ship(size, spOrientation.getValue().equals("Horizontal"),
                (int)((String)spColumn.getValue()).charAt(0) - (int)'0',
                (int)((String)spRow.getValue()).charAt(0) - (int)'A' + 1));
        if (!addable) {
            JOptionPane.showMessageDialog(app, "The ship you just add has conflicted body.\nPlease try again!",
                    "Conflicted", JOptionPane.ERROR_MESSAGE);
        } else {
            index++;
            if ((index == game.getListOfSizes().size() && game.getGameMode() != GameMode.PVP)
                    || (index == game.getListOfSizes().size() * 2)) {
                app.toTurnFiller();
                if (game.getGameMode() != GameMode.PVP) {
                    game.player2().addAllShips(game.getListOfSizes());
                }
            }
        }
    }

    private void addComponents() {
        add(spColumn);
        add(spRow);
        add(spOrientation);

        add(addButton);

        add(questionLabel);
        add(coordinateLabel);
        add(orientationLabel);
    }

    private void modifyLabels() {
        questionLabel = new JLabel("How?");
        questionLabel.setFont(Settings.mainFont);
        questionLabel.setBounds(20, 400, 550, 50);

        coordinateLabel = new JLabel("Starting cell coordinates");
        orientationLabel = new JLabel("Orientation");
        coordinateLabel.setFont(Settings.mainFontSmall);
        orientationLabel.setFont(Settings.mainFontSmall);

        coordinateLabel.setBounds(30, 460, 200, 50);
        orientationLabel.setBounds(270, 460, 200, 50);
    }

    private String questionString() {
        String ret1 = "How would ship of size ";
        String ret2 = " be placed?";
        int ret3 = game.getListOfSizes().get(index % game.getListOfSizes().size());
        return ret1 + ret3 + ret2;
    }

    private void modifyButton() {
        addButton = new JButton("Add");
        addButton.setFont(Settings.mainFont);
        addButton.setBounds(450, 500, 100, 30);
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Settings.BUTTON_COLOR);
        addButton.addActionListener(e -> buttonAction());
    }

    private void modifySpinners() {
        spColumn = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('1', game.getGridSize())));
        spRow = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('A', game.getGridSize())));
        spColumn.setFont(Settings.mainFont);
        spRow.setFont(Settings.mainFont);
        spColumn.setBounds(30, 500, SPINNER_WIDTH, SPINNER_HEIGHT);
        spRow.setBounds(150, 500, SPINNER_WIDTH, SPINNER_HEIGHT);

        String[] orientations = {"Horizontal", "Vertical"};
        spOrientation = new JSpinner(new SpinnerListModel(orientations));
        spOrientation.setFont(Settings.mainFontSmall);
        spOrientation.setBounds(270, 500, SPINNER_WIDTH * 2, SPINNER_HEIGHT);
    }

    private String[] generateConsecutiveStringFrom(char c, int len) {
        String[] res = new String[len];
        for (int i = 0; i < len; i++) {
            res[i] = "" + (char)(c + i);
        }
        return res;
    }
}
