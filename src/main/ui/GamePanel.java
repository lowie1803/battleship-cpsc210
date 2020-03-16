package ui;

import model.BattleshipGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    BattleshipGame game;
    DisplayPanel displayPanel;
    InputPanel inputPanel;

    public GamePanel(BattleshipGame game) {
        this.game = game;
        displayPanel = new DisplayPanel(game);
        inputPanel = new InputPanel(game);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(displayPanel, BorderLayout.NORTH, 0);
        add(inputPanel, BorderLayout.SOUTH, 1);
    }
}
