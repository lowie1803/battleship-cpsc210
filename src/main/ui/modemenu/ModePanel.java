package ui.modemenu;

import model.BattleshipGame;
import settings.AudioSet;
import settings.Settings;
import ui.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModePanel extends JPanel {
    BattleshipGame game;
    App app;
    BufferedImage background;
    JButton pvp;
    JButton pvc;

    public ModePanel(BattleshipGame game, App app) {
        this.game = game;
        this.app = app;
        try {
            background = ImageIO.read(new File(Settings.IMAGE_MENU));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
        pvc = new JButton("1 Player");
        pvp = new JButton("2 Players");

        configureButtons();

        add(pvp);
        add(pvc);
    }

    private void configureButtons() {
        pvc.setFont(Settings.MAIN_FONT);
        pvp.setFont(Settings.MAIN_FONT);

        pvc.setBounds(100, 400, 150, 50);
        pvp.setBounds(350, 400, 150, 50);

        pvc.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvCGameMode();
            game.reset();
            app.toConfigPanel();
        });

        pvp.addActionListener(e -> {
            AudioSet.playButtonClick();
            game.setPvPGameMode();
            game.reset();
            app.toConfigPanel();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}
