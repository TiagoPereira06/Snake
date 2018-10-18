package isel.poo.snake.model;

import isel.poo.snake.ctrl.Snake;


public class Level {


    private int height, width, levelNumber;
    private Cell[][] board;

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public Level(int levelNumber, int height, int width) {
        board = new Cell[height][width];
        setHeight(height);
        setWidth(width);
        setLevelNumber(levelNumber);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return height;
    }

    public void init(Game game) {

    }

    public void putCell(int l, int c, Cell cell) {
        board[l][c]=cell;
    }

    public boolean snakeIsDead() {
        //TODO
        return false;
    }

    public boolean isFinished() {
        //TODO
        return false;
    }

    public int getNumber() {
        return levelNumber;
    }

    public int getRemainingApples() {
        //TODO
        return 0;
    }

    public Object getCell(int l, int c) {
        return board[l][c];
    }

    public void setObserver(Observer updater) {
        //TODO
    }

    public void setSnakeDirection(Dir dir) {
        //TODO
    }

    public void step() {
        //TODO
    }

    public interface Observer {
        // Level.Listener
        void cellUpdated(int l, int c, isel.poo.snake.model.Cell cell);

        void cellCreated(int l, int c, isel.poo.snake.model.Cell cell);

        void cellRemoved(int l, int c);

        void cellMoved(int fromL, int fromC, int toL, int toC, isel.poo.snake.model.Cell cell);

        void applesUpdated(int apples);
        //TODO
    }
}
