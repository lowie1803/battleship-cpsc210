package ui;

import model.BattleshipGame;
import settings.Settings;
import ui.conclude.ConcludePanel;
import ui.menus.AboutPanel;
import ui.menus.ModePanel;
import ui.ingame.GamePanel;
import ui.menus.MenuPanel;
import ui.playerconfig.ConfigPanel;
import ui.turnfiller.TurnFillerPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static ui.App.PanelStatus.ABOUT;

public class App extends JFrame {
    private static final int INTERVAL = 20;
    private Settings settings = new Settings();

    GamePanel gamePanel;
    MenuPanel menuPanel;
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
        menuPanel = new MenuPanel(battleshipGame, this);
        concludePanel = new ConcludePanel(battleshipGame, this);
        modePanel = new ModePanel(battleshipGame, this);
        turnFillerPanel = new TurnFillerPanel(battleshipGame, this);
        configPanel = new ConfigPanel(battleshipGame, this);
        aboutPanel = new AboutPanel(battleshipGame, this);
//        try {
//            setIconImage(ImageIO.read(new File(Settings.IMAGE_MENU)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
