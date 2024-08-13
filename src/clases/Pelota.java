package clases;

import java.awt.Rectangle;

public class Ball {

    public static final int SIZE = 20;
    private static final int INITIAL_SPEED = 3;
    private static final int ACCELERATION = 1;
    private static final int MAX_SPEED = 10; // Velocidad máxima

    private int x, y;
    private int velocityX, velocityY;
    private int speedIncreaseCount = 0;

    public Ball(int startX, int startY) {
        x = startX;
        y = startY;
        velocityX = INITIAL_SPEED;
        velocityY = INITIAL_SPEED;
    }

    public void move() {
        x += velocityX;
        y += velocityY;

        if (y <= 0 || y >= GamePanel.HEIGHT - SIZE) velocityY = -velocityY;
    }

    public void checkCollision(Paddle p1, Paddle p2) {
        Rectangle ballBounds = new Rectangle(x, y, SIZE, SIZE);
        Rectangle paddle1Bounds = new Rectangle(p1.getX(), p1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        Rectangle paddle2Bounds = new Rectangle(p2.getX(), p2.getY(), Paddle.WIDTH, Paddle.HEIGHT);

        if (ballBounds.intersects(paddle1Bounds) || ballBounds.intersects(paddle2Bounds)) {
            velocityX = -velocityX;
            increaseSpeed();
        }
    }

    private void increaseSpeed() {
        speedIncreaseCount++;
        int newSpeed = INITIAL_SPEED + speedIncreaseCount * ACCELERATION;
        if (newSpeed > MAX_SPEED) newSpeed = MAX_SPEED;
        velocityX = (velocityX > 0) ? newSpeed : -newSpeed;
        velocityY = (velocityY > 0) ? newSpeed : -newSpeed;
    }

    public void reset() {
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        velocityX = INITIAL_SPEED;
        velocityY = INITIAL_SPEED;
        speedIncreaseCount = 0; // Reiniciar el contador de aumento de velocidad
    }

    public void changeDirection() {
        velocityX = -velocityX;
        velocityY = -velocityY;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

