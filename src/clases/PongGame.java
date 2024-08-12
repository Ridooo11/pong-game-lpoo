package clases;

import javax.swing.*;
import java.awt.*;

public class PongGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();

        // Centrando la ventana en la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int x = (screenSize.width - frameWidth) / 2;
        int y = (screenSize.height - frameHeight) / 2;
        frame.setLocation(x, y);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

