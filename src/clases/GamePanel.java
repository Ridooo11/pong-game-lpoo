package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1480359743256212572L;
	public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final int PERIOD_DURATION = 60; // segundos por período
    private static final int SCORE_LIMIT = 7;
    private static final int SCORE_DIFFERENCE = 2;

    private Timer timer;
    private Paddle player1, player2;
    private Ball ball;
    private int player1Score = 0;
    private int player2Score = 0;
    private long periodStartTime;
    private boolean gameRunning = true;
    private boolean firstHalf = true;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        player1 = new Paddle(30, HEIGHT / 2 - Paddle.HEIGHT / 2);
        player2 = new Paddle(WIDTH - 40 - Paddle.WIDTH, HEIGHT / 2 - Paddle.HEIGHT / 2);
        ball = new Ball(WIDTH / 2, HEIGHT / 2);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) player1.setUpPressed(true);
                if (key == KeyEvent.VK_S) player1.setDownPressed(true);
                if (key == KeyEvent.VK_UP) player2.setUpPressed(true);
                if (key == KeyEvent.VK_DOWN) player2.setDownPressed(true);
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
        g.fillRect(player1.getX(), player1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        g.fillRect(player2.getX(), player2.getY(), Paddle.WIDTH, Paddle.HEIGHT);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(ball.getX(), ball.getY(), Ball.SIZE, Ball.SIZE);
    }

    private void drawScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Jugador 1: " + player1Score, WIDTH / 2 - 370, 30);
        g.drawString("Jugador 2: " + player2Score, WIDTH / 2 + 210, 30);
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 50));
        String message;
        if (player1Score > player2Score) {
            message = "Jugador 2 gana";
        } else if (player2Score > player1Score) {
            message = "Jugador 2 gana";
        } else {
            message = "Empate";
        }
        g.drawString(message, WIDTH / 2 - g.getFontMetrics().stringWidth(message) / 2, HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            long elapsedTime = (System.currentTimeMillis() - periodStartTime) / 1000;
            if (elapsedTime >= PERIOD_DURATION) {
                if (firstHalf) {
                    // Cambio de tiempo
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
            player2Score++;
            ball.reset();  
            
            player2.increaseSpeed(); 
        } else if (ball.getX() > WIDTH) {
            player1Score++;
            ball.reset();  
            
            player1.increaseSpeed(); 
        }

       
        if (Math.abs(player1Score - player2Score) >= SCORE_DIFFERENCE &&
            (player1Score >= SCORE_LIMIT || player2Score >= SCORE_LIMIT)) {
            gameRunning = false;
        }
    }
}



