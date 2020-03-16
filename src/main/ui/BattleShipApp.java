package ui;

import model.BattleshipGame;
import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import settings.Settings;

import javax.swing.*;

public class BattleShipApp extends JFrame {
    private Settings settings = new Settings();
    private Player[] player = {new HumanPlayer(), new RandomizedBot()};

    GamePanel gamePanel;
    MenuPanel menuPanel;
    BattleshipGame battleshipGame;


    public BattleShipApp() {
        super("Battelxip by low_");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        battleshipGame = new BattleshipGame();
        gamePanel = new GamePanel(battleshipGame);
        menuPanel = new MenuPanel();
        add(gamePanel);
        pack();
        setVisible(true);
        runApp();
    }

    private void runApp() {

    }
}
