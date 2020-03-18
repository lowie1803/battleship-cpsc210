package ui.ingame;

import model.BattleshipGame;
import model.GameMode;
import settings.ColorSet;
//import settings.Settings.*;
import ui.App;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;
import static settings.Settings.*;

public class InputPanel extends JPanel {
    private static final int SPINNER_HEIGHT = 30;
    private static final int SPINNER_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 150;
    App app;

    BattleshipGame game;
    JSpinner spColumn;
    JSpinner spRow;
    JButton attackButton;
    JLabel turnAnnouncer;
    JLabel rowDescription;
    JLabel columnDescription;


    public InputPanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        setPreferredSize(new Dimension(600, 200));
        setBackground(ColorSet.BACKGROUND);
        setLayout(null);
        setFont(MAIN_FONT);

        addContent();

        attackButton.addActionListener(e -> {
            try {
                game.inflictAttack((int)(((String)spColumn.getValue()).charAt(0)) - (int)'0',
                        (int)(((String)spRow.getValue()).charAt(0)) - (int)'A' + 1);
                if (game.getGameMode() == GameMode.PVP) {
                    app.toTurnFiller();
                }
            } catch (MoveAlreadyTakenException ex) {
                JOptionPane.showMessageDialog(app, "You've already made this move",
                        "Repeated Move", JOptionPane.WARNING_MESSAGE);
            }
            if (game.gameEnded()) {
                app.toConclusion();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        turnAnnouncer.setText(turnAnnouncement());
    }

    private void addContent() {
        modifyAnnouncer();
        modifySpinners();
        modifyAttackButton();
        modifyDescriptions();

        add(turnAnnouncer);
        add(spColumn);
        add(spRow);
        add(attackButton);
        add(rowDescription);
        add(columnDescription);
    }

    private void modifyAnnouncer() {
        turnAnnouncer = new JLabel(turnAnnouncement());
        turnAnnouncer.setFont(MAIN_FONT);
        turnAnnouncer.setBounds(15, 15, 600, 30);
    }

    private void modifySpinners() {
        spColumn = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('1', game.getGridSize())));
        spRow = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('A', game.getGridSize())));
        spColumn.setFont(MAIN_FONT);
        spRow.setFont(MAIN_FONT);
        spColumn.setBounds(30, 100, SPINNER_WIDTH, SPINNER_HEIGHT);
        spRow.setBounds(150, 100, SPINNER_WIDTH, SPINNER_HEIGHT);
    }

    private void modifyAttackButton() {
        attackButton = new JButton("Attack!");
        attackButton.setBackground(ColorSet.BUTTON);
        attackButton.setFont(MAIN_FONT);
        attackButton.setBounds(250, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void modifyDescriptions() {
        rowDescription = new JLabel("Row (A~" + (char)('A' + game.getGridSize() - 1) + "):");
        columnDescription = new JLabel("Column (1~" + (char)('1' + game.getGridSize() - 1) + "):");
        rowDescription.setFont(MAIN_FONT_SMALL);
        rowDescription.setBounds(150, 70, 100, 30);
        columnDescription.setFont(MAIN_FONT_SMALL);
        columnDescription.setBounds(30, 70, 100, 30);
    }

    private String[] generateConsecutiveStringFrom(char c, int len) {
        String[] res = new String[len];
        for (int i = 0; i < len; i++) {
            res[i] = "" + (char)(c + i);
        }
        return res;
    }

    private String turnAnnouncement() {
        try {
            if (game.getGameMode() == GameMode.PVCE || game.getGameMode() == GameMode.PVCH) {
                return "It's Your Turn!";
            } else {

                if (game.turn1()) {
                    return "Player 2's turn!";
                } else {
                    return "Player 1's turn!";
                }
            }
        } catch (NullPointerException e) {
            return "";
        }
    }
}
