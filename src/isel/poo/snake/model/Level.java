package isel.poo.snake.model;


import java.util.LinkedList;
import java.util.List;

public class Level {


    private int height, width, levelNumber,remApples = 10,moves, inicialLine, inicialCol,stepCounter;
    private Game currentGame;
    private Cell[][] board;
    private Dir currentSnakeDirection = Dir.UP;
    private List<SnakeBodyCell> snake = new LinkedList<>();
    private SnakeHeadCell snakeHead;
    private Observer update;
    private int lineSnake, colSnake;



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
        setHeight(height);
        setWidth(width);
        fillEmptyCells(board);
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
        snake.add((SnakeBodyCell) board[inicialLine][inicialCol]); //ADICIONAR CABEÇA LINKEDLIST
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
            inicialLine = l;
            inicialCol = c;
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
        ++stepCounter;
        moveHead(currentSnakeDirection);
        if (stepCounter > 1)
            move();
        if(stepCounter <= 4){
            snake.add(stepCounter,new SnakeBodyCell(inicialLine, inicialCol));
            update.cellCreated(inicialLine, inicialCol,snake.get(stepCounter));
            board[inicialLine][inicialCol] = snake.get(stepCounter);
        }
        if (stepCounter>4)
            System.out.println("fds");
    }

    private void moveHead(Dir dir) {
        //TODO:
        ++moves;
        lineSnake = snakeHead.getLine();
        colSnake = snakeHead.getCol();
        if(dir==Dir.UP){
            update.cellMoved(lineSnake, colSnake, lineSnake -1, colSnake,snakeHead);
            board[lineSnake-1][colSnake] = snakeHead;
            snakeHead.setCord(lineSnake -1, colSnake);
        }
    }

    private void move() {
        int lin, col;
        lin = lineSnake;
        col = colSnake;
        for (int i = 1; i < snake.size(); i++) {
            if (i > 1) {
                lin = snake.get(i).getLine();
                col = snake.get(i).getLine();
            }
            moveCell(col, lin, snake.get(i));
        }
        //moveCell(colSnake,lineSnake,snake.get(snake.size()-1));

    }
    private void moveCell(int x,int y, SnakeBodyCell cell){
        update.cellMoved(cell.getLine(),cell.getCol(),y,x,cell);
        board[cell.getLine()][cell.getCol()] = new EmptyCell();
        cell.setCord(x,y);
        board[x][y] = cell;
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
