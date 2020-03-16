package ui;

import model.BattleshipGame;
import model.players.HumanPlayer;
import model.players.Player;
import model.players.RandomizedBot;
import settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private static final int INTERVAL = 20;
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
        addTimer();
//        runApp();
    }

    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
//                battleshipGame.update();
                gamePanel.repaint();
//                sp.update();
            }
        });

        t.start();
    }
}
