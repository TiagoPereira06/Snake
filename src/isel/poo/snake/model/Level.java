package isel.poo.snake.model;


import java.util.LinkedList;
import java.util.List;

public class Level {


    private int height, width, levelNumber, remApples = 10, moves, inicialLine, inicialCol, stepCounter;
    private Game currentGame;
    private Cell[][] board;
    private Dir currentSnakeDirection = Dir.UP;
    private List<SnakeBodyCell> snake;
    private SnakeHeadCell snakeHead;
    private Observer update;
    private int lineSnake, colSnake;

    Level(int levelNumber, int height, int width) {
        board = new Cell[height][width];
        snake = new LinkedList<>();
        fillEmptyCells(board);//TODO: VER SE HÁ OUTRA MANEIRA DE FAZER ISTO
        setHeight(height);
        setWidth(width);
        setLevelNumber(levelNumber);
    }

    private void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    void init(Game game) {
        // TODO: INEFICIENTE E ESTÚPIDO
        findHead(); //TODO: ARRANJAR OUTRA MANEIRA DE OBTER AS COORDENADAS DA CABEÇA DA SNAKE
        currentGame = game;
        currentGame.setScore(0);
        currentGame.setLevelNumber(levelNumber);
        moves = 0;
    }

    private void findHead() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getCell(i, j) instanceof SnakeHeadCell) {
                    inicialLine = i;
                    inicialCol = j;
                    snakeHead = (SnakeHeadCell) getCell(inicialLine, inicialCol);
                    snakeHead.setCord(inicialLine, inicialCol);
                    snake.add(snakeHead);
                }
            }
        }
    }

    void putCell(int l, int c, Cell cell) {
        board[l][c] = cell;
    }

    public Cell getCell(int l, int c) {
        return board[l][c];
    }

    public boolean snakeIsDead() {
        //TODO
        return false;
    }

    public boolean isFinished() {
        return remApples == 0;
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


    public void setObserver(Observer updater) {
        this.update = updater;
    }

    public void setSnakeDirection(Dir dir) {
        currentSnakeDirection = dir;
    }

    public void step() {
        ++stepCounter;
        moveHead(currentSnakeDirection);
        if (stepCounter > 1)
            moveBody();
        if (stepCounter <= 4) {
            snake.add(stepCounter, new SnakeBodyCell(inicialLine, inicialCol));
            update.cellCreated(inicialLine, inicialCol, snake.get(stepCounter));
            putCell(inicialLine, inicialCol, snake.get(stepCounter));
        }
    }

    private void moveHead(Dir dir) {
        //TODO: verificar se a direção para onde é pretendido ir está um obstáculo/maçã/cobra
        ++moves;
        lineSnake = snakeHead.getLine();
        colSnake = snakeHead.getCol();
        if (dir == Dir.UP) {
            update.cellMoved(lineSnake, colSnake, lineSnake - 1, colSnake, snakeHead);
            putCell(lineSnake - 1, colSnake, snakeHead);
            snakeHead.setCord(lineSnake - 1, colSnake);
        } else if (dir == Dir.DOWN) {
            update.cellMoved(lineSnake, colSnake, lineSnake + 1, colSnake, snakeHead);
            putCell(lineSnake + 1, colSnake, snakeHead);
            snakeHead.setCord(lineSnake + 1, colSnake);
        } else if (dir == Dir.LEFT) {
            update.cellMoved(lineSnake, colSnake, lineSnake, colSnake - 1, snakeHead);
            putCell(lineSnake, colSnake - 1, snakeHead);
            snakeHead.setCord(lineSnake, colSnake - 1);
        } else {
            update.cellMoved(lineSnake, colSnake, lineSnake, colSnake + 1, snakeHead);
            putCell(lineSnake, colSnake + 1, snakeHead);
            snakeHead.setCord(lineSnake, colSnake + 1);
        }
    }

    private void moveBody() {
        int lin, col, lastLine, lastCol;
        lin = lineSnake;
        col = colSnake;
        // QUE MERDA ERA AQUELA QUE ESTAVA AQUI ?? ESTUDA FDS !
        for (int i = 1; i < snake.size(); i++) {
            lastLine = snake.get(i).getLine();
            lastCol = snake.get(i).getCol();
            moveCell(col, lin, snake.get(i));
            lin = lastLine;
            col = lastCol;
        }
    }


    private void moveCell(int col, int lin, SnakeBodyCell cell) {
        update.cellMoved(cell.getLine(), cell.getCol(), lin, col, cell);
        putCell(cell.getLine(), cell.getCol(), new EmptyCell());
        cell.setCord(lin, col);
        putCell(lin, col, cell);
    }

    private void fillEmptyCells(Cell[][] board) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
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
