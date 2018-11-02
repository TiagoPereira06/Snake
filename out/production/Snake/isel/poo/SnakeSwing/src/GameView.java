
import javax.swing.*;
import java.awt.*;

public class GameView {

    //use Graphics API draw pics
    private JPanel canvas;
    private final Grid grid;
    public final int DEFAULT_NODE_SIZE = 15;

    public GameView(Grid grid) {
        this.grid = grid;
    }

    public void init() {
        canvas = new JPanel() {
            @Override
            public void paintComponent(Graphics graphics) {
                drawGridBackground(graphics);
                drawSnake(graphics, grid.getSnake());
                drawFood(graphics, grid.getFood());
            }
        };
    }

    public void draw() {
        canvas.repaint();
    }

    public JPanel getCanvas() {
        return canvas;
    }

    public void drawSnake(Graphics graphics, Snake snake) {
        for (Node node : snake.getBody()) {
            drawSquare(graphics, node, Color.GREEN);
        }
    }
    public void drawFood(Graphics graphics, Node squareArea) {
        drawCircle(graphics, squareArea, Color.RED);
    }
    public void drawGridBackground(Graphics graphics) {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                drawSquare(graphics, new Node(i, j), new Color(127, 127, 127, 255));
            }
        }
    }

    private void drawSquare(Graphics graphics, Node squareArea, Color color) {
        graphics.setColor(color);
        int size = DEFAULT_NODE_SIZE;
        graphics.fillRect(squareArea.getX() * size, squareArea.getY() * size, size - 1, size - 1);
    }
    private void drawCircle(Graphics graphics, Node squareArea, Color color) {
        graphics.setColor(color);
        int size = DEFAULT_NODE_SIZE;
        graphics.fillOval(squareArea.getX() * size-1, squareArea.getY() * size-1, size, size);
    }

    public void showGameOverMessage() {
        JOptionPane.showMessageDialog(null, "Game Over!", "GameOver", JOptionPane.INFORMATION_MESSAGE);
    }
}
