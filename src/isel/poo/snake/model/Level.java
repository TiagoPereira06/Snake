package isel.poo.snake.model;


import java.util.LinkedList;
import java.util.List;

public class Level {


    private int height, width, levelNumber,remApples = 10,moves,inicialX,inicialY,stepCounter;
    private Game currentGame;
    private Cell[][] board;
    private Dir currentSnakeDirection = Dir.UP;
    private List<SnakeBodyCell> snake = new LinkedList<>();
    private SnakeHeadCell snakeHead;
    private Observer update;

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
        //TODO:
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
        //TODO:
        snake.add((SnakeBodyCell) board[inicialX][inicialY]);
        currentGame=game;
        game.setScore(0);
        game.setLevelNumber(levelNumber);
        moves=0;

    }

    public void putCell(int l, int c, Cell cell) {
        //TODO: INEFI
        if(cell instanceof SnakeHeadCell) {
            ((SnakeHeadCell) cell).setCord(l,c);
            snakeHead = (SnakeHeadCell) cell;
            inicialX = l;
            inicialY = c;
        }
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
/*        for (int i = 0; i <height ; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] instanceof AppleCell)
                    ++remApples;
            }
        }*/
        return remApples;
    }

    public Cell getCell(int l, int c) {
        return board[l][c];
    }

    public void setObserver(Observer updater) {
        this.update = updater;
    }

    public void setSnakeDirection(Dir dir) {
        currentSnakeDirection=dir;
    }

    public void step() {
        fillEmptyCells(board);
        ++stepCounter;
        move(currentSnakeDirection);
        if(stepCounter<=4){
            snake.add(stepCounter,new SnakeBodyCell(inicialX,inicialY));
            update.cellUpdated(inicialX,inicialY,snake.get(stepCounter));
            board[inicialX][inicialY] = snake.get(stepCounter);
        }

        //TODO:


    }

    private void move(Dir dir) {
        //TODO:
        ++moves;
        if(dir==Dir.UP){
            for (int i = 0; i<snake.size() ; i++) {
                moveCell(snake.get(i).getX(),snake.get(i).getY()-1,snake.get(i));

            }
        }
    }
    private void moveCell(int x,int y, SnakeBodyCell cell){
        EmptyCell a = new EmptyCell();
        board[cell.getX()][cell.getY()] = a;
        update.cellUpdated(cell.getX(),cell.getY(),a);
        cell.setCord(x,y);
        board[x][y] = cell;
        update.cellUpdated(x,y,cell);


    }

    private void fillEmptyCells(Cell[][] board) {
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j <width ; j++) {
                board[i][j] = new EmptyCell();
            }
        }
    }
    public interface Observer {

        // Level.Listener
        void cellUpdated(int l, int c, Cell cell);
        void cellCreated(int l, int c, Cell cell);

        void cellRemoved(int l, int c);

        void cellMoved(int fromL, int fromC, int toL, int toC, isel.poo.snake.model.Cell cell);

        void applesUpdated(int apples);

        //TODO
    }
}
