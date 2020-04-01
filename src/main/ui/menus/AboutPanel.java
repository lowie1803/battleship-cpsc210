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
import java.net.URI;
import java.net.URISyntaxException;

public class AboutPanel extends MenuPanel {
    String facebookImg = "data/icon/facebook-icon.png";
    String codeforcesImg = "data/icon/codeforces-icon.png";
    String gmailImg = "data/icon/gmail-icon.png";
    String linkedinImg = "data/icon/linkedin-icon.png";
    String outlookImg = "data/icon/outlook-icon.png";
    String facebookLink = "https://www.facebook.com/lowieeee";
    String codeforcesLink = "https://codeforces.com/profile/low_";
    String gmailLink = "mailto:ttuandung1803@gmail.com";
    String outlookLink = "mailto:daniel.lowie.to@outlook.com";
    String linkedinLink = "https://www.linkedin.com/in/daniel-to-83045a19a/";


    public AboutPanel(BattleshipGame game, App app) {
        super(game, app);
        modifyContents();
    }

    @Override
    public void modifyContents() {
        try {
            modifyBackButton();
            modifyText();
            modifyHyperlinkButton(facebookLink, facebookImg, 168, 330, false);
            modifyHyperlinkButton(linkedinLink, linkedinImg, 368, 330, false);
            modifyHyperlinkButton(gmailLink, gmailImg, 268, 330, false);
            modifyHyperlinkButton(outlookLink, outlookImg, 220, 410, false);
            modifyHyperlinkButton(codeforcesLink, codeforcesImg, 320, 410, true);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void modifyHyperlinkButton(String url, String img, int x, int y,
                                       boolean bgr) throws URISyntaxException {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(img));
        button.setBounds(x, y, 64, 64);
        button.setOpaque(bgr);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
//        System.out.println(url);
        URI link = new URI(url);
        button.addActionListener(e -> {
            openURL(link);
        });
        add(button);
    }

    private void modifyText() {
        JLabel content1 = new JLabel(" Personal project of Daniel To");
        content1.setFont(Settings.MAIN_FONT);
        content1.setBounds(145, 285, 310, 30);
        content1.setForeground(Color.WHITE);
        content1.setOpaque(true);
        content1.setBackground(Color.BLACK);
        add(content1);
    }

    private void openURL(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
