
import java.util.Arrays;
import java.util.Random;

public class Grid {


    private boolean isCovered[][];
    private final int width;
    private final int height;
    private int scores = 0;

    private Snake snake;
    private Node food;

    private Direction snakeDirection = Direction.LEFT;         // initial direction is LEFT
    public boolean isDirectionChanged = false;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        isCovered = new boolean[width][height];

        initSnake();
        createFood();
    }
    private Snake initSnake() {                                 // initial Snake has 3 Node
        snake = new Snake();
        for (int i = 0; i < 3; i++) {
            snake.addTail(new Node(i + width / 2, height / 2));
            isCovered[i + width / 2][height / 2] = true;
        }
        return snake;
    }

    public Node createFood() {
        int x,y;
        do {
            x = new Random( ).nextInt(width);
            y = new Random( ).nextInt(height);
        } while (isCovered[x][y] == true);
        food = new Node(x, y);
        return food;
    }

    public boolean nextRound() {                                     //follow the direction and move one step
        if (isMoveValid(snakeDirection)) {
            Node move = snake.move(snakeDirection);
            if (snake.isEatFood(food)) {                             //if ate food, add the Node moved at tail
                snake.addTail(move);
                createFood();
                System.out.println(++scores);
            } else isCovered[move.getX()][move.getY()] = false;
            return true;
        } else return false;
    }
    private boolean isMoveValid(Direction direction) {
        int headX = snake.getHead().getX();
        int headY = snake.getHead().getY();
        switch(direction) {
            case UP :
                headY--;
                break;
            case RIGHT :
                headX++;
                break;
            case DOWN :
                headY++;
                break;
            case LEFT :
                headX--;
                break;
        }
        if (headX < 0 || headX >= width || headY < 0 || headY >= height) return false;
        if (isCovered[headX][headY] == true) return false;
        isCovered[headX][headY] = true;
        return true;
    }

    public void changeDirection(Direction newDirection) {
        if (snakeDirection.compatibleWith(newDirection)) {
            snakeDirection = newDirection;
            isDirectionChanged = true;
        }
    }

    public Snake getSnake() { return snake; }
    public Node getFood() { return food; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getScore() { return scores; }

}
