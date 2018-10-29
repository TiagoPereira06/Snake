package isel.poo.snake.model;


public class Level {


    private int height, width, levelNumber,remApples;
    private Cell[][] board;
    private Dir currentSnakeDirection;

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
        fillEmptyCells(board);
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
        return remApples==0;
    }

    public int getNumber() {
        return levelNumber;
    }

    public int getRemainingApples() {
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] instanceof AppleCell)
                    ++remApples;
            }
        }
        return remApples;
    }

    public Cell getCell(int l, int c) {
        return board[l][c];
    }

    public void setObserver(Observer updater) {
        //TODO
    }

    public void setSnakeDirection(Dir dir) {
        currentSnakeDirection=dir;
    }

    public void step() {
        move(currentSnakeDirection);

    }

    private void move(Dir dir) {
        //TODO:
        if(dir==Dir.UP){}
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
    private void fillEmptyCells(Cell[][] board) {
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j <width ; j++) {
                board[i][j] = new EmptyCell();
            }
        }
    }
}
