package clases;

public class Jugador {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 100;
    private static final int INITIAL_SPEED = 5;
    private static final int ACCELERATION = 1; // Aumento de velocidad

    private int x, y;
    private boolean upPressed, downPressed;
    private int speed;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = INITIAL_SPEED;
    }

    public void move() {
        if (upPressed && y > 0) y -= speed;
        if (downPressed && y < PanelPrincipal.HEIGHT - HEIGHT) y += speed;
    }

    public void increaseSpeed() {
        speed += ACCELERATION;
    }

    public void resetSpeed() {
        speed = INITIAL_SPEED;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
}
