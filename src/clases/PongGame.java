package clases;

import javax.swing.*;
import java.awt.*;

public class PongGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PanelPrincipal gamePanel = new PanelPrincipal();
        frame.add(gamePanel);
        frame.pack();

        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int x = (screenSize.width - frameWidth) / 2;
        int y = (screenSize.height - frameHeight) / 2;
        frame.setLocation(x, y);
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

