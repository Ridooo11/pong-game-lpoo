package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelPrincipal extends JPanel implements ActionListener {

    private static final long serialVersionUID = -1480359743256212572L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final int PERIOD_DURATION = 60;
    private static final int SCORE_LIMIT = 7;
    private static final int SCORE_DIFFERENCE = 2;

    private Timer timer;
    private Jugador player1, player2;
    private Pelota ball;
    private int player1Score = 0;
    private int player2Score = 0;
    private long periodStartTime;
    private boolean gameRunning = true;
    private boolean firstHalf = true;

    public PanelPrincipal() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        player1 = new Jugador(30, HEIGHT / 2 - Jugador.HEIGHT / 2);
        player2 = new Jugador(WIDTH - 40 - Jugador.WIDTH, HEIGHT / 2 - Jugador.HEIGHT / 2);
        ball = new Pelota(WIDTH / 2, HEIGHT / 2);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) player1.setUpPressed(true);
                if (key == KeyEvent.VK_S) player1.setDownPressed(true);
                if (key == KeyEvent.VK_UP) player2.setUpPressed(true);
                if (key == KeyEvent.VK_DOWN) player2.setDownPressed(true);
                if (key == KeyEvent.VK_ENTER && !gameRunning) {
                    resetGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) player1.setUpPressed(false);
                if (key == KeyEvent.VK_S) player1.setDownPressed(false);
                if (key == KeyEvent.VK_UP) player2.setUpPressed(false);
                if (key == KeyEvent.VK_DOWN) player2.setDownPressed(false);
            }
        });

        timer = new Timer(10, this);
        timer.start();
        periodStartTime = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPaddles(g);
        drawBall(g);
        drawScores(g);
        drawDividingLine(g);
        drawTimer(g);
        drawPeriodIndicator(g);
        if (!gameRunning) {
            drawGameOver(g);
        }
    }

    private void drawPaddles(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(player1.getX(), player1.getY(), Jugador.WIDTH, Jugador.HEIGHT);
        g.fillRect(player2.getX(), player2.getY(), Jugador.WIDTH, Jugador.HEIGHT);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(ball.getX(), ball.getY(), Pelota.SIZE, Pelota.SIZE);
    }

    private void drawScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        if (firstHalf) {
            g.drawString("Jugador 1: " + player1Score, WIDTH / 2 - 370, 30);
            g.drawString("Jugador 2: " + player2Score, WIDTH / 2 + 210, 30);
        } else {
            g.drawString("Jugador 2: " + player2Score, WIDTH / 2 - 370, 30);
            g.drawString("Jugador 1: " + player1Score, WIDTH / 2 + 210, 30);
        }
    }

    private void drawDividingLine(Graphics g) {
        g.setColor(Color.WHITE);
        int x = WIDTH / 2;
        g.drawLine(x, 0, x, HEIGHT);
    }

    private void drawTimer(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        long elapsedTime = (System.currentTimeMillis() - periodStartTime) / 1000;
        long remainingTime = PERIOD_DURATION - elapsedTime;
        g.drawString("Tiempo: " + Math.max(0, remainingTime), WIDTH / 2 - 110, 30);
    }

    private void drawPeriodIndicator(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        String period = firstHalf ? "P.T." : "S.T.";
        g.drawString(period, WIDTH / 2 + 60, 30);
    }

    private void drawGameOver(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 50));
        String message;
        if (player1Score > player2Score) {
            message = "Jugador 1 gana";
        } else if (player2Score > player1Score) {
            message = "Jugador 2 gana";
        } else {
            message = "Empate";
        }
        g.drawString(message, WIDTH / 2 - g.getFontMetrics().stringWidth(message) / 2, HEIGHT / 2 - 50);
        
     
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Pulse ENTER para reiniciar", WIDTH / 2 - g.getFontMetrics().stringWidth("Pulse ENTER para reiniciar") / 2, HEIGHT / 2 + 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            long elapsedTime = (System.currentTimeMillis() - periodStartTime) / 1000;
            if (elapsedTime >= PERIOD_DURATION) {
                if (firstHalf) {
                    firstHalf = false;
                    periodStartTime = System.currentTimeMillis();
                    ball.reset();
                } else {
                    gameRunning = false;
                }
            }

            player1.move();
            player2.move();
            ball.move();
            ball.checkCollision(player1, player2);
            checkForScore();
            repaint();
        }
    }

    private void checkForScore() {
        if (ball.getX() < 0) {
            if (firstHalf) {
                player2Score++;
            } else {
                player1Score++;
            }
            ball.reset();
            ball.resetWithDirection(false); 
        } else if (ball.getX() > WIDTH) {
            if (firstHalf) {
                player1Score++;
            } else {
                player2Score++;
            }
            ball.resetWithDirection(true); 
            ball.reset();
        }

        if (Math.abs(player1Score - player2Score) >= SCORE_DIFFERENCE &&
            (player1Score >= SCORE_LIMIT || player2Score >= SCORE_LIMIT)) {
            gameRunning = false;
        }
    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        firstHalf = true;
        periodStartTime = System.currentTimeMillis();
        ball.reset();
        gameRunning = true;
        repaint();
    }
}
