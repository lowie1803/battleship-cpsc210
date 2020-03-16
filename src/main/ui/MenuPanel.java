package ui;

import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuPanel extends JPanel {
    ImageIcon background = new ImageIcon("E:/CPSC210/project_f1z2b/data/battleshipbylow.png");
    JLabel imageAsLabel;

    public MenuPanel() {
//        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT));
        imageAsLabel = new JLabel(background);
        add(imageAsLabel);
    }
}
