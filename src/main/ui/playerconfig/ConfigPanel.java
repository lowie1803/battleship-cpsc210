package ui.playerconfig;

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
    int index = 0;

    public ConfigPanel(BattleshipGame game, App app) {
        this.game = game;
        this.app = app;
        setBackground(Settings.INPUT_BACKGROUND_COLOR);
        setLayout(null);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));

        modifySpinners();
        modifyButton();
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
