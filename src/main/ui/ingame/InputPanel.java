package ui.ingame;

import model.BattleshipGame;
import model.GameMode;
import model.Move;
import persistence.Writer;
import settings.AudioSet;
import settings.ColorSet;
//import settings.Settings.*;
import ui.App;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static settings.Settings.*;

public class InputPanel extends JPanel {
    private static final int SPINNER_HEIGHT = 30;
    private static final int SPINNER_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 120;
    private static final String HIT_ANNOUNCEMENT = "Your shot is hit! :D";
    private static final String MISSED_ANNOUNCEMENT = "Your shot didn't hit anything! :(";
    private static final String REPEATED_WARNING = "You've already made this move";

    App app;

    BattleshipGame game;
    JSpinner spColumn;
    JSpinner spRow;
    JButton attackButton;
    JButton saveButton;
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
        attackButton.addActionListener(e -> attackButtonAction());
        saveButton.addActionListener(e -> {
            saveButtonAction();
        });
    }

    private void saveButtonAction() {
        Writer writer;
        try {
            writer = new Writer(new File("data/saved.txt"));
            writer.write(game);
            writer.close();
            app.setLoadable(true);
            app.toMenu();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(app, "data/saved.txt not found!", "ERROR", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(app, "Cannot save file!", "ERROR", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void attackButtonAction() {
        try {
            Move hit = game.inflictAttack((int)(((String)spColumn.getValue()).charAt(0)) - (int)'0',
                    (int)(((String)spRow.getValue()).charAt(0)) - (int)'A' + 1);
            if (game.getGameMode() == GameMode.PVP) {
                app.toTurnFiller();
            }
            if (hit.getStatus() == Move.Status.HIT) {
                AudioSet.playHit();
                JOptionPane.showMessageDialog(app, HIT_ANNOUNCEMENT, "HIT", JOptionPane.PLAIN_MESSAGE);
            } else if (hit.getStatus() == Move.Status.MISS) {
                AudioSet.playMiss();
                JOptionPane.showMessageDialog(app, MISSED_ANNOUNCEMENT, "MISSED", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (MoveAlreadyTakenException ex) {
            AudioSet.playError();
            JOptionPane.showMessageDialog(app, REPEATED_WARNING, "Repeated Move", JOptionPane.WARNING_MESSAGE);
        }
        if (game.gameEnded()) {
            playConcludeAudioAccordingly();
            game.clearSavedGame();
            app.toConclusion();
        }
    }

    void playConcludeAudioAccordingly() {
        if (game.getGameMode() == GameMode.PVP) {
            AudioSet.playVictory();
        } else {
            if (game.getWinner().equals("1")) {
                AudioSet.playVictory();
            } else {
                AudioSet.playLoser();
            }
        }
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
        modifySaveButton();
        modifyDescriptions();

        add(turnAnnouncer);
        add(spColumn);
        add(spRow);
        add(attackButton);
        add(rowDescription);
        add(columnDescription);
        add(saveButton);
    }

    private void modifySaveButton() {
        saveButton = new JButton("Save & Quit");
        saveButton.setBackground(ColorSet.BUTTON);
        saveButton.setFont(MAIN_FONT_SMALL);
        saveButton.setBounds(400, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void modifyAnnouncer() {
        turnAnnouncer = new JLabel(turnAnnouncement());
        turnAnnouncer.setFont(MAIN_FONT);
        turnAnnouncer.setForeground(ColorSet.ANNOUNCER);
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
