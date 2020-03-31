package ui.menus;

import model.BattleshipGame;
import settings.AudioSet;
import settings.ColorSet;
import settings.Settings;
import ui.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModePanel extends JPanel {
    // TODO: Add back button
    BattleshipGame game;
    App app;
    BufferedImage background;
    JButton pvp;
    JButton pvce;
    JButton pvch;

    public ModePanel(BattleshipGame game, App app) {
        this.game = game;
        this.app = app;
        try {
            background = ImageIO.read(new File(Settings.IMAGE_MENU));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
        pvce = new JButton("1 Player (Easy)");
        pvch = new JButton("1 Player (Hard)");
        pvp = new JButton("2 Players");

        configureButtons();
        modifyBackButton();

        add(pvp);
        add(pvce);
        add(pvch);
    }

    private void configureButtons() {
        pvce.setFont(Settings.MAIN_FONT_SMALL);
        pvch.setFont(Settings.MAIN_FONT_SMALL);
        pvp.setFont(Settings.MAIN_FONT);

        pvce.setBounds(50, 400, 150, 50);
        pvch.setBounds(225, 400, 150, 50);
        pvp.setBounds(400, 400, 150, 50);

        pvce.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvCEGameMode();
            app.toConfigPanel();
        });

        pvch.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvCHGameMode();
            app.toConfigPanel();
        });

        pvp.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvPGameMode();
            app.toConfigPanel();
        });
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
        g.drawImage(background, 0, 0, null);
    }
}
