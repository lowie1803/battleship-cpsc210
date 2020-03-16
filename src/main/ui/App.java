package ui;

import model.BattleshipGame;
import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import settings.Settings;

import javax.swing.*;

public class App extends JFrame {
    private Settings settings = new Settings();

    GamePanel gamePanel;
    MenuPanel menuPanel;
    BattleshipGame battleshipGame;


    public App() {
        super("Battelxip by low_");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        battleshipGame = new BattleshipGame();
        gamePanel = new GamePanel(battleshipGame);
        menuPanel = new MenuPanel();
        setResizable(false);
        add(gamePanel);
        pack();
        setVisible(true);
        runApp();
    }

    private void runApp() {

    }
}
