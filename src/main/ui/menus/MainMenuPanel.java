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
import persistence.Reader;
import ui.MenuPanel;

import static java.lang.System.exit;

public class MainMenuPanel extends MenuPanel {
    // TODO: add features to let player choose the size and the ships.
    JButton startButton;
    JButton quitButton;
    JButton instructionButton;
    JButton aboutButton;
    JButton loadButton;

    public MainMenuPanel(BattleshipGame game, App app) {
        super(game, app);
        modifyContents();
    }

    private void modifyLoadButton() {
        loadButton = new JButton("Load game");
        loadButton.setBounds(50, 425, 200, 50);
        loadButton.setFont(Settings.MAIN_FONT);
        loadButton.setBackground(ColorSet.BUTTON);
        toggleLoad(app.getLoadable());
        loadButton.addActionListener(e -> {
            AudioSet.playButtonClick();
            try {
                Reader.readGame(game, new File(Settings.SAVED_GAMES_DATA));
                app.toGame();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(app, "Nothing to load!", "", JOptionPane.WARNING_MESSAGE);
                app.setLoadable(false);
                toggleLoad(false);
            }
        });
    }

    private void modifyAboutButton() {
        aboutButton = new JButton("About");
        aboutButton.setBounds(50, 500, 200, 50);
        aboutButton.setFont(Settings.MAIN_FONT);
        aboutButton.setBackground(ColorSet.BUTTON);
        aboutButton.addActionListener(e -> {
            AudioSet.playButtonClick();
            app.toAbout();
        });
    }

    private void modifyInstructionButton() {
        instructionButton = new JButton("Instruction");
        instructionButton.setBounds(350, 425, 200, 50);
        instructionButton.setFont(Settings.MAIN_FONT);
        instructionButton.setBackground(ColorSet.BUTTON);
        instructionButton.addActionListener(e -> {
            AudioSet.playButtonClick();
            //TODO: implement instruction page
        });
    }

    private void modifyStartButton() {
        startButton = new JButton("New Game");
        startButton.setBounds(150, 350, 300, 50);
        startButton.setFont(Settings.MAIN_FONT);
        startButton.setBackground(Color.WHITE);
        startButton.addActionListener(e -> {
            AudioSet.playButtonClick();
            app.toMode();
        });
    }

    private void modifyQuitButton() {
        quitButton = new JButton("Quit");
        quitButton.setBounds(350, 500, 200, 50);
        quitButton.setFont(Settings.MAIN_FONT);
        quitButton.setBackground(ColorSet.BUTTON);
        quitButton.addActionListener(e -> {
            AudioSet.playButtonClick();
            exit(0);
        });
    }

    private void addComponents() {
        add(startButton);
        add(quitButton);
        add(instructionButton);
        add(aboutButton);
        add(loadButton);
    }

    public void toggleLoad(boolean b) {
        loadButton.setEnabled(b);
    }

    @Override
    public void modifyContents() {
        modifyStartButton();
        modifyQuitButton();
        modifyInstructionButton();
        modifyAboutButton();
        modifyLoadButton();
        addComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        toggleLoad(app.getLoadable());
    }
}
