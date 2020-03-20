package ui;

import model.BattleshipGame;
import settings.Settings;
import ui.conclude.ConcludePanel;
import ui.modemenu.ModePanel;
import ui.ingame.GamePanel;
import ui.mainmenu.MenuPanel;
import ui.playerconfig.ConfigPanel;
import ui.turnfiller.TurnFillerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private static final int INTERVAL = 20;
    private Settings settings = new Settings();

    GamePanel gamePanel;
    MenuPanel menuPanel;
    ConcludePanel concludePanel;
    ModePanel modePanel;
    TurnFillerPanel turnFillerPanel;
    ConfigPanel configPanel;
    boolean loadable = true;

    BattleshipGame battleshipGame;
    static PanelStatus panelStatus;



    public App() {
        super("Battleship by low_");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        battleshipGame = new BattleshipGame();
        gamePanel = new GamePanel(battleshipGame, this);
        menuPanel = new MenuPanel(battleshipGame, this);
        concludePanel = new ConcludePanel(battleshipGame, this);
        modePanel = new ModePanel(battleshipGame, this);
        turnFillerPanel = new TurnFillerPanel(battleshipGame, this);
        configPanel = new ConfigPanel(battleshipGame, this);
        setResizable(false);
        add(menuPanel);
        panelStatus = PanelStatus.MENU;
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
                if (panelStatus == PanelStatus.MENU) {
                    menuPanel.repaint();
                } else if (panelStatus == PanelStatus.INGAME) {
                    gamePanel.repaint();
                } else if (panelStatus == PanelStatus.CONFIG) {
                    configPanel.repaint();
                }
            }
        });

        t.start();
    }

    public static void setPanelStatus(PanelStatus panelStatus) {
        App.panelStatus = panelStatus;
    }

    public static PanelStatus getPanelStatus() {
        return panelStatus;
    }

    public void toMenu() {
        setContentPane(menuPanel);
        revalidate();
        setPanelStatus(PanelStatus.MENU);
    }

    public void toMode() {
        setContentPane(modePanel);
        revalidate();
        setPanelStatus(PanelStatus.MODE);
    }

    public void toGame() {
        setContentPane(gamePanel);
        revalidate();
        setPanelStatus(PanelStatus.INGAME);
    }

    public void toTurnFiller() {
        setContentPane(turnFillerPanel);
        revalidate();
        setPanelStatus(PanelStatus.TURNFILLER);
    }

    public void toConclusion() {
        setContentPane(concludePanel);
        revalidate();
        setPanelStatus(PanelStatus.CONCLUDE);
    }

    public void toConfigPanel() {
        setContentPane(configPanel);
        revalidate();
        setPanelStatus(PanelStatus.CONFIG);
    }

    public boolean getLoadable() {
        return loadable;
    }

    public void setLoadable(boolean b) {
        loadable = b;
    }

    public enum PanelStatus {
        MENU, INGAME, CONCLUDE, TURNFILLER, MODE, CONFIG
    }
}
