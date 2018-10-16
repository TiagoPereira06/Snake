package isel.poo.snake.model;

import isel.poo.snake.ctrl.Snake;


public class Level {

    public void setLevelHeigth(int levelHeigth) {
        this.levelHeigth = levelHeigth;
    }

    public void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }

    public int getLevelHeigth() {
        return levelHeigth;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    private int levelHeigth, levelWidth;

    public Level(int levelNumber, int height, int width) {

    }

    public int getHeight() {
        //TODO
        return 0;
    }

    public int getWidth() {
        //TODO
        return 0;
    }

    public void init(Game game) {
        //TODO
    }

    public void putCell(int l, int c, Cell cell) {
        //TODO
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
        //TODO
        return 0;
    }

    public int getRemainingApples() {
        //TODO
        return 0;
    }

    public Object getCell(int l, int c) {
        //TODO
        return null;
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
