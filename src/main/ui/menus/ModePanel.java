package ui.menus;

import model.BattleshipGame;
import settings.AudioSet;
import settings.ColorSet;
import settings.Settings;
import ui.App;
import ui.MenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModePanel extends MenuPanel {

    public ModePanel(BattleshipGame game, App app) {
        super(game, app);
        modifyContents();
    }

    @Override
    public void modifyContents() {
        modifyBackButton();
        modifyPvPButton();
        modifyPvCEButton();
        modifyPvCHButton();
    }

    private void modifyPvPButton() {
        JButton pvp = new JButton("2 Players");
        pvp.setFont(Settings.MAIN_FONT);
        pvp.setBounds(400, 400, 150, 50);
        pvp.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvPGameMode();
            app.toConfigPanel();
        });
        add(pvp);
    }

    private void modifyPvCHButton() {
        JButton pvch = new JButton("1 Player (Hard)");
        pvch.setFont(Settings.MAIN_FONT_SMALL);
        pvch.setBounds(225, 400, 150, 50);
        pvch.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvCHGameMode();
            app.toConfigPanel();
        });
        add(pvch);
    }

    private void modifyPvCEButton() {
        JButton pvce = new JButton("1 Player (Easy)");
        pvce.setFont(Settings.MAIN_FONT_SMALL);
        pvce.setBounds(50, 400, 150, 50);
        pvce.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvCEGameMode();
            app.toConfigPanel();
        });
        add(pvce);
    }

    private void modifyBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setBounds(200, 500, 200, 50);
        backButton.setFont(Settings.MAIN_FONT);
        backButton.setBackground(ColorSet.BUTTON);
        backButton.addActionListener(e -> {
            AudioSet.playButtonClick();
            app.toMenu();
        });
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
