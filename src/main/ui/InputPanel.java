package ui;

import model.BattleshipGame;
import model.GameMode;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;

public class InputPanel extends JPanel {
    private static final int SPINNER_HEIGHT = 30;
    private static final int SPINNER_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 150;

    BattleshipGame game;
    JSpinner spColumn;
    JSpinner spRow;
    JButton attackButton;
    JLabel turnAnnouncer;
    JLabel rowDescription;
    JLabel columnDescription;

    Font fontCS = new Font("Comic Sans MS", BOLD, 20);
    Font fontCSSmall = new Font("Comic Sans MS", Font.PLAIN, 16);

    public InputPanel(BattleshipGame game) {
        this.game = game;
        setPreferredSize(new Dimension(600, 200));
        setBackground(Color.GRAY);
        setLayout(null);

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

        attackButton.addActionListener(e -> {
            System.out.println(spRow.getValue());
        });
    }

    private void modifyAnnouncer() {
        turnAnnouncer = new JLabel(turnAnnouncement());
        turnAnnouncer.setFont(fontCS);
        turnAnnouncer.setBounds(15, 15, 600, 30);
    }

    private void modifySpinners() {
        spColumn = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('1', game.getGridSize())));
        spRow = new JSpinner(new SpinnerListModel(generateConsecutiveStringFrom('A', game.getGridSize())));
        spColumn.setFont(fontCS);
        spRow.setFont(fontCS);
        spColumn.setBounds(30, 100, SPINNER_WIDTH, SPINNER_HEIGHT);
        spRow.setBounds(150, 100, SPINNER_WIDTH, SPINNER_HEIGHT);
    }

    private void modifyAttackButton() {
        attackButton = new JButton("Attack!");
        attackButton.setBackground(new Color(59, 89, 182));
        attackButton.setFont(fontCS);
        attackButton.setBounds(250, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void modifyDescriptions() {
        rowDescription = new JLabel("Row (A~" + (char)('A' + game.getGridSize() - 1) + "):");
        columnDescription = new JLabel("Column (1~" + (char)('1' + game.getGridSize() - 1) + "):");
        rowDescription.setFont(fontCSSmall);
        rowDescription.setBounds(150, 70, 100, 30);
        columnDescription.setFont(fontCSSmall);
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
        if (game.getGameMode() == GameMode.PVCE || game.getGameMode() == GameMode.PVCH) {
            return "It's Your Turn!";
        } else {
            if (game.turn1()) {
                return "Player 1's turn!";
            } else {
                return "Player 2's turn!";
            }
        }
    }
}
