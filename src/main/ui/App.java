package ui;

import model.BattleshipGame;
import settings.Settings;
import ui.conclude.ConcludePanel;
import ui.menus.AboutPanel;
import ui.menus.ModePanel;
import ui.ingame.GamePanel;
import ui.menus.MainMenuPanel;
import ui.playerconfig.ConfigPanel;
import ui.turnfiller.TurnFillerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.App.PanelStatus.ABOUT;

public class App extends JFrame {
    private static final int INTERVAL = 20;

    GamePanel gamePanel;
    MainMenuPanel mainMenuPanel;
    ConcludePanel concludePanel;
    ModePanel modePanel;
    TurnFillerPanel turnFillerPanel;
    ConfigPanel configPanel;
    AboutPanel aboutPanel;
    boolean loadable = true;

    BattleshipGame battleshipGame;
    static PanelStatus panelStatus;

    public App() {
        super("Battleship by low_");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        battleshipGame = new BattleshipGame();
        gamePanel = new GamePanel(battleshipGame, this);
        mainMenuPanel = new MainMenuPanel(battleshipGame, this);
        concludePanel = new ConcludePanel(battleshipGame, this);
        modePanel = new ModePanel(battleshipGame, this);
        turnFillerPanel = new TurnFillerPanel(battleshipGame, this);
        configPanel = new ConfigPanel(battleshipGame, this);
        aboutPanel = new AboutPanel(battleshipGame, this);
        setResizable(false);
        add(mainMenuPanel);
        panelStatus = PanelStatus.MENU;
        pack();
        setVisible(true);
        addTimer();
    }

    private void addTimer() {
        Timer t = new Timer(INTERVAL, ae -> {
            if (panelStatus == PanelStatus.MENU) {
                mainMenuPanel.repaint();
            } else if (panelStatus == PanelStatus.INGAME) {
                gamePanel.repaint();
            } else if (panelStatus == PanelStatus.CONFIG) {
                configPanel.repaint();
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
        setContentPane(mainMenuPanel);
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

    public void toAbout() {
        setContentPane(aboutPanel);
        revalidate();
        setPanelStatus(ABOUT);
    }

    public boolean getLoadable() {
        return loadable;
    }

    public void setLoadable(boolean b) {
        loadable = b;
    }

    public enum PanelStatus {
        MENU, INGAME, CONCLUDE, TURNFILLER, MODE, CONFIG, ABOUT
    }
}
