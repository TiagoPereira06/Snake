package isel.poo.snake.model;


import java.util.LinkedList;
import java.util.List;

import static isel.poo.snake.model.Dir.*;

public class Level {


    private int height, width, levelNumber, remApples, moves, inicialLine, inicialCol, stepCounter,score=0,sectionsAdded,lastScoreStepCounter, inicialAppleCount;
    private Game currentGame;
    private Cell[][] board;
    private Dir currentSnakeDirection = UP;
    private List<SnakeBodyCell> snake;
    private SnakeHeadCell snakeHead;
    private Observer update;
    private int lineSnake, colSnake; //Current Cord SnakeHead
    private boolean snakeDead = false, addAfterMove, teletranportation;

    Level(int levelNumber, int height, int width) {
        board = new Cell[height][width];
        snake = new LinkedList<>();
        fillEmptyCells(board,true);//TODO: VER SE HÁ OUTRA MANEIRA DE FAZER ISTO
        setHeight(height);
        setWidth(width);
        setLevelNumber(levelNumber);
        remApples=10;
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
        currentGame.setScore(score);
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
        if(cell instanceof AppleCell)++inicialAppleCount;
        board[l][c] = cell;
    }

    public Cell getCell(int l, int c) {
        return board[l][c];
    }

    public boolean snakeIsDead() {
        return snakeDead;
    }

    public boolean isFinished() {
        return remApples == 0 || snakeIsDead();
    }

    public int getNumber() {
        return levelNumber;
    }

    public int getRemainingApples() {
        return remApples;
    }


    public void setObserver(Observer updater) {
        this.update = updater;
    }

    public void setSnakeDirection(Dir dir) {
        currentSnakeDirection = dir;
    }

    public void step() {
        fillEmptyCells(board,false);
        ++stepCounter;
        losesSection();
            lineSnake = snakeHead.getLine();
            colSnake = snakeHead.getCol();
            //checkTeletransportation(lineSnake,colSnake,currentSnakeDirection);
            if (safeToMove(lineSnake, colSnake, currentSnakeDirection)) {
                moveHead(currentSnakeDirection);
                if (stepCounter > 1 && !addAfterMove) {
                    sectionsAdded = 0;
                    moveBody();
                }
                if (stepCounter <= 4) {
                    snake.add(stepCounter, new SnakeBodyCell(inicialLine, inicialCol));
                    update.cellCreated(inicialLine, inicialCol, snake.get(stepCounter));
                    putCell(inicialLine, inicialCol, snake.get(stepCounter));
                }
            } else return;
/*
            System.out.println("SNAKE DEAD - " + snakeDead);
            System.out.println("REM APPLES - " + remApples);
            System.out.println("SCORE - " + score);
            System.out.println("ADD AFTER MOVE - " + addAfterMove);
            System.out.println("SECTION ADDED - " + sectionsAdded);*/
            System.out.println(stepCounter);
            if (sectionsAdded >= 4) addAfterMove = false;

        }


    private void moveHead(Dir dir) {
        ++moves;
        if (dir == UP) {
            if (getCell(lineSnake - 1,colSnake) instanceof AppleCell) {
                updateRoutineAfterScore();
            }
         else {
                update.cellMoved(lineSnake, colSnake, lineSnake - 1, colSnake, snakeHead);
                putCell(lineSnake - 1, colSnake, snakeHead);
                snakeHead.setCord(lineSnake - 1, colSnake);
            }

            } else if (dir == Dir.DOWN) {
                if(board[lineSnake+1][colSnake] instanceof AppleCell){
                    updateRoutineAfterScore();
                }
                update.cellMoved(lineSnake, colSnake, lineSnake + 1, colSnake, snakeHead);
                putCell(lineSnake + 1, colSnake, snakeHead);
                snakeHead.setCord(lineSnake + 1, colSnake);
            } else if (dir == Dir.LEFT) {
                if(board[lineSnake][colSnake-1] instanceof AppleCell){
                    updateRoutineAfterScore();
                }
                update.cellMoved(lineSnake, colSnake, lineSnake, colSnake - 1, snakeHead);
                putCell(lineSnake, colSnake - 1, snakeHead);
                snakeHead.setCord(lineSnake, colSnake - 1);
            } else {
                if(board[lineSnake][colSnake+1] instanceof AppleCell){
                    updateRoutineAfterScore();
                }
                update.cellMoved(lineSnake, colSnake, lineSnake, colSnake + 1, snakeHead);
                putCell(lineSnake, colSnake + 1, snakeHead);
                snakeHead.setCord(lineSnake, colSnake + 1);

            }
        if (addAfterMove){
                ++sectionsAdded;
                snake.add(1, new SnakeBodyCell(lineSnake, colSnake));
                update.cellCreated(lineSnake, colSnake, snake.get(1));
                putCell(lineSnake, colSnake, snake.get(1));

            }

    }


    private boolean safeToMove(int currentSnakeHeadLine, int currentSnakeHeadCol, Dir dir) {
        if (dir == UP) {
            if(currentSnakeHeadLine==0 && getCell(getHeight()-1,currentSnakeHeadCol) == null)
                return true;
            if ((getCell(currentSnakeHeadLine - 1,currentSnakeHeadCol)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine-1,currentSnakeHeadCol) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        }
        if (dir == DOWN) {
            if ((getCell(currentSnakeHeadLine + 1,currentSnakeHeadCol)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine+1,currentSnakeHeadCol) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        }
        if (dir == LEFT) {
            if ((getCell(currentSnakeHeadLine,currentSnakeHeadCol-1)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine,currentSnakeHeadCol-1) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        }
        if (dir == RIGHT) {
            if ((getCell(currentSnakeHeadLine,currentSnakeHeadCol+1)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine,currentSnakeHeadCol+1) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        }
        return true;
    }

    private void deadSnake(int currentSnakeHeadLine, int currentSnakeHeadCol) {
        DeadSnakeHeadCell dead = new DeadSnakeHeadCell();
        update.cellUpdated(currentSnakeHeadLine, currentSnakeHeadCol,dead);
        dead.setCord(currentSnakeHeadLine,currentSnakeHeadCol);
        putCell(currentSnakeHeadLine,currentSnakeHeadCol,dead);
        snakeDead = true;
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

    private void losesSection() {
        double test=(double)(stepCounter-lastScoreStepCounter)/10;
        if(test%1==0&&test!=0){
            EmptyCell emptyCell = new EmptyCell();
            update.cellCreated(snake.get(snake.size()-1).getLine(),snake.get(snake.size()-1).getCol(),emptyCell);
            putCell(snake.get(snake.size()-1).getLine(),snake.get(snake.size()-1).getCol(),emptyCell);
            snake.remove(snake.size()-1);
            --score;
            currentGame.setScore(score);
        }

    }

    private void updateRoutineAfterScore() {
        if(remApples>inicialAppleCount)genNewApple();
        lastScoreStepCounter=stepCounter;
        updateNumbers();
        addAfterMove=true;
    }

    private void genNewApple() {
            int l1 = (int) (Math.random() * (getHeight()));
            int c1 = (int) (Math.random() * (getWidth()));
            AppleCell apple = new AppleCell();

            while (!isEmpty(l1,c1)) {
                l1 = (int) (Math.random() * (getHeight()));
                c1 = (int) (Math.random() * (getWidth()));
            }
            update.cellCreated(l1,c1,apple);
            board[l1][c1] = apple;

        }

    private boolean isEmpty(int line, int col) {
        return board[line][col]instanceof EmptyCell;
    }

    private void updateNumbers() {
        --remApples;
        update.applesUpdated(remApples);
        score+=4;
        currentGame.setScore(score);
    }

    private void checkTeletransportation(int lineSnake, int colSnake, Dir currentSnakeDirection) {
        if (lineSnake == 0) {
            update.cellMoved(lineSnake, colSnake, getHeight() - 1, colSnake, snakeHead);
            putCell(getHeight() - 1, colSnake, snakeHead);
            snakeHead.setCord(getHeight() - 1, colSnake);
        }
    }

    private void fillEmptyCells(Cell[][] board, boolean all) {
        if (all) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    board[i][j] = new EmptyCell();
                }
            }
        } else {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if(getCell(i,j) == null)
                        board[i][j] = new EmptyCell();
                }

            }
        }
    }
    public interface Observer {


        // Level.Listener
        void cellUpdated(int l, int c, Cell cell);

        void cellCreated(int l, int c, Cell cell);

        void cellRemoved(int l, int c);

        void cellMoved(int fromL, int fromC, int toL, int toC, Cell cell);

        void applesUpdated(int apples);

    }
}
