package ui.ingame;

import model.BattleshipGame;
import ui.App;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    BattleshipGame game;
    DisplayPanel displayPanel;
    InputPanel inputPanel;
    App app;

    public GamePanel(BattleshipGame game, App app) {
        this.app = app;
        this.game = game;
        displayPanel = new DisplayPanel(game, app);
        inputPanel = new InputPanel(game, app);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(displayPanel, BorderLayout.NORTH, 0);
        add(inputPanel, BorderLayout.SOUTH, 1);
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
