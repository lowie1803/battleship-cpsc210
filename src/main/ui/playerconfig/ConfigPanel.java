package ui.playerconfig;

import model.BattleshipGame;
import model.GameMode;
import model.players.Player;
import model.ship.Ship;
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

import static settings.Settings.*;

public class ConfigPanel extends JPanel {
    private static final int SPINNER_HEIGHT = 30;
    private static final int SPINNER_WIDTH = 50;
    private static final String CONFLICT_MESSAGE = "The ship you just add has conflicted body.\nPlease try again!";
    App app;
    BattleshipGame game;

    JButton addButton;
    JSpinner spOrientation;
    JSpinner spRow;
    JSpinner spColumn;
    BufferedImage background;

    JLabel questionLabel;
    JLabel coordinateLabel;
    JLabel orientationLabel;

    int index = 0;

    public ConfigPanel(BattleshipGame game, App app) {
        this.game = game;
        this.app = app;
        setBackground(ColorSet.BACKGROUND);
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

    private void addComponents() {
        add(spColumn);
        add(spRow);
        add(spOrientation);

        add(addButton);

        add(questionLabel);
        add(coordinateLabel);
        add(orientationLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        questionLabel.setText(questionString());
        drawDisplay(g);
    }

    private void drawDisplay(Graphics g) {
        ComponentDrawer.drawBoards(g, game.getGridSize());
        if (currentPlayer() == game.player1()) {
            ComponentDrawer.drawPlayerShips(g, game.getGridSize(), currentPlayer(), BOARD_X1, BOARD_Y);
        } else {
            ComponentDrawer.drawPlayerShips(g, game.getGridSize(), currentPlayer(), BOARD_X2, BOARD_Y);
        }
    }

    private void buttonAction() {
        Player player = currentPlayer();
        int size = currentShipSize();
        boolean addable = player.addShip(new Ship(size, spOrientation.getValue().equals("Horizontal"),
                (int)((String)spColumn.getValue()).charAt(0) - (int)'0',
                (int)((String)spRow.getValue()).charAt(0) - (int)'A' + 1));
        if (!addable) {
            JOptionPane.showMessageDialog(app, CONFLICT_MESSAGE,"Conflicted", JOptionPane.ERROR_MESSAGE);
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
        spinnersReset();
    }

    private Player currentPlayer() {
        if (game.getGameMode() == GameMode.PVP && index >= game.getListOfSizes().size()) {
            return game.player2();
        } else {
            return game.player1();
        }
    }

    private int currentShipSize() {
        return game.getListOfSizes().get(index % game.getListOfSizes().size());
    }

    private void spinnersReset() {
        spColumn.setValue("1");
        spRow.setValue("A");
    }

    private void modifyLabels() {
        questionLabel = new JLabel("How?");
        questionLabel.setFont(Settings.MAIN_FONT);
        questionLabel.setBounds(20, 400, 550, 50);

        coordinateLabel = new JLabel("Starting cell coordinates");
        orientationLabel = new JLabel("Orientation");
        coordinateLabel.setFont(Settings.MAIN_FONT_SMALL);
        orientationLabel.setFont(Settings.MAIN_FONT_SMALL);

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
        addButton.setFont(Settings.MAIN_FONT);
        addButton.setBounds(450, 500, 100, 30);
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(ColorSet.BUTTON);
        addButton.addActionListener(e -> buttonAction());
    }

    private void modifySpinners() {
        spColumn = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('1', game.getGridSize())));
        spRow = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('A', game.getGridSize())));
        spColumn.setFont(Settings.MAIN_FONT);
        spRow.setFont(Settings.MAIN_FONT);
        spColumn.setBounds(30, 500, SPINNER_WIDTH, SPINNER_HEIGHT);
        spRow.setBounds(150, 500, SPINNER_WIDTH, SPINNER_HEIGHT);

        String[] orientations = {"Horizontal", "Vertical"};
        spOrientation = new JSpinner(new SpinnerListModel(orientations));
        spOrientation.setFont(Settings.MAIN_FONT_SMALL);
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
