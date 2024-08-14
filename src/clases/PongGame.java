package clases;

import javax.swing.*;

public class PongGame {

    public static void main(String[] args) {
    	JFrame frame = new JFrame("Pong Game");
        PanelPrincipal gamePanel = new PanelPrincipal();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);             
    }
}

