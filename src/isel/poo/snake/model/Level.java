package isel.poo.snake.model;


import java.util.LinkedList;
import java.util.List;

import static isel.poo.snake.model.Dir.*;

public class Level {


    private int height, width, levelNumber, remApples, moves, initialLine, initialCol, stepCounter, score = 0, sectionsAdded, lastScoreStepCounter,
            initialAppleCount, prevLineSnake, prevColSnake;
    private Game currentGame;
    private Cell[][] board;
    private Dir currentSnakeDirection = UP, prevSnakeDirection;
    private List<SnakeBodyCell> snake;
    private SnakeHeadCell snakeHead;
    private Observer update;
    private int lineSnake, colSnake; //Current Cord SnakeHead
    private boolean snakeDead = false, addAfterMove, teletransportation;

    Level(int levelNumber, int height, int width) {
        board = new Cell[height][width];
        snake = new LinkedList<>();
        setHeight(height);
        setWidth(width);
        fillEmptyCells(board);//TODO: VER SE HÁ OUTRA MANEIRA DE FAZER ISTO
        setLevelNumber(levelNumber);
        remApples = 10;
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

    public Cell getCell(int l, int c) {
        return board[l][c];
    }

    public boolean snakeIsDead() {
        return snakeDead;
    }

    public boolean isFinished() {
        return remApples ==0 || snakeIsDead();
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

    void init(Game game) {
        // TODO: INEFICIENTE E ESTÚPIDO
        findHead(); //TODO: ARRANJAR OUTRA MANEIRA DE OBTER AS COORDENADAS DA CABEÇA DA SNAKE
        currentGame = game;
        currentGame.setScore(score);
        currentGame.setLevelNumber(levelNumber);
        moves = 0;

    }

    public void step() {
        ++stepCounter;
        losesSection();
        lineSnake = snakeHead.getLine();
        colSnake = snakeHead.getCol();
        teletransport();
        if (safeToMove(lineSnake, colSnake, currentSnakeDirection)) {
            moveHead(currentSnakeDirection);
            if (stepCounter > 1 && !addAfterMove) {
                sectionsAdded = 0;
                moveBody();
            }
            if (stepCounter <= 4) {
                snake.add(stepCounter, new SnakeBodyCell(initialLine, initialCol));
                update.cellCreated(initialLine, initialCol, snake.get(stepCounter));
                putCell(initialLine, initialCol, snake.get(stepCounter));
            }
        } else return;
/*
            System.out.println("SNAKE DEAD - " + snakeDead);
            System.out.println("REM APPLES - " + remApples);
            System.out.println("SCORE - " + score);
            System.out.println("ADD AFTER MOVE - " + addAfterMove);
            System.out.println("SECTION ADDED - " + sectionsAdded);*/
        if (sectionsAdded >= 4) addAfterMove = false;
        teletransportation = false;

    }

    private void teletransport() {
        if ((lineSnake == 0 && currentSnakeDirection == UP) || (lineSnake == getHeight() - 1 && currentSnakeDirection == DOWN)
                || (colSnake == 0 && currentSnakeDirection == LEFT) || (colSnake == getWidth() - 1 && currentSnakeDirection == RIGHT)) {
            teletransportation = true;
            prevLineSnake = lineSnake;
            prevColSnake = colSnake;
            prevSnakeDirection = currentSnakeDirection;
            invertDirection(currentSnakeDirection);

            if (currentSnakeDirection == DOWN) {
                lineSnake = getHeight() - 2;
                if(getCell(getHeight()-1,colSnake) instanceof AppleCell)
                    updateRoutineAfterScore();
            } else if (currentSnakeDirection == UP) {
                lineSnake = 1;
                if(getCell(0,colSnake) instanceof AppleCell)
                    updateRoutineAfterScore();
            } else if (currentSnakeDirection == LEFT) {
                colSnake = 1;
                if(getCell(lineSnake,0) instanceof AppleCell)
                    updateRoutineAfterScore();
            } else if (currentSnakeDirection == RIGHT) {
                colSnake = getWidth() - 2;
                if(getCell(lineSnake,getWidth()-1) instanceof AppleCell)
                    updateRoutineAfterScore();

            }
        }
    }

    private void invertDirection(Dir currentSnakeDirection) {
        if(currentSnakeDirection == UP) setSnakeDirection(DOWN);
        else if(currentSnakeDirection == DOWN) setSnakeDirection(UP);
        else if(currentSnakeDirection == LEFT) setSnakeDirection(RIGHT);
        else setSnakeDirection(LEFT);

    }


    private void moveHead(Dir dir) {
        if(teletransportation){
            lineSnake = prevLineSnake;
            colSnake = prevColSnake;
            setSnakeDirection(prevSnakeDirection);
            dir = currentSnakeDirection;
        }
        ++moves;
            if (dir == UP) {
                if (teletransportation) {
                    update.cellMoved(lineSnake, colSnake,getHeight()-1, colSnake, snakeHead);
                    putCell(getHeight()-1, colSnake, snakeHead);
                    snakeHead.setCord(getHeight()-1, colSnake);

                }else{
                    if(board[lineSnake-1][colSnake] instanceof AppleCell){
                        updateRoutineAfterScore();
                    }
                    update.cellMoved(lineSnake, colSnake, lineSnake - 1, colSnake, snakeHead);
                    putCell(lineSnake - 1, colSnake, snakeHead);
                    snakeHead.setCord(lineSnake - 1, colSnake);
                }
            } else if (dir == Dir.DOWN) {
                if(teletransportation){
                    update.cellMoved(lineSnake, colSnake,0, colSnake, snakeHead);
                    putCell(0, colSnake, snakeHead);
                    snakeHead.setCord(0, colSnake);
                }else {
                    if (board[lineSnake + 1][colSnake] instanceof AppleCell) {
                        updateRoutineAfterScore();
                    }
                    update.cellMoved(lineSnake, colSnake, lineSnake + 1, colSnake, snakeHead);
                    putCell(lineSnake + 1, colSnake, snakeHead);
                    snakeHead.setCord(lineSnake + 1, colSnake);
                }
            } else if (dir == Dir.LEFT) {
                if (teletransportation) {
                    update.cellMoved(lineSnake, colSnake,lineSnake, getWidth()-1, snakeHead);
                    putCell(lineSnake, getWidth()-1, snakeHead);
                    snakeHead.setCord(lineSnake, getWidth()-1);
                }else {
                    if (board[lineSnake][colSnake - 1] instanceof AppleCell) {
                        updateRoutineAfterScore();
                    }

                update.cellMoved(lineSnake, colSnake, lineSnake, colSnake - 1, snakeHead);
                putCell(lineSnake, colSnake - 1, snakeHead);
                snakeHead.setCord(lineSnake, colSnake - 1);
            }
             }else {
                if (teletransportation) {
                    update.cellMoved(lineSnake, colSnake, lineSnake, 0, snakeHead);
                    putCell(lineSnake, 0, snakeHead);
                    snakeHead.setCord(lineSnake, 0);
                } else {
                    if (board[lineSnake][colSnake + 1] instanceof AppleCell) {
                        updateRoutineAfterScore();
                    }
                    update.cellMoved(lineSnake, colSnake, lineSnake, colSnake + 1, snakeHead);
                    putCell(lineSnake, colSnake + 1, snakeHead);
                    snakeHead.setCord(lineSnake, colSnake + 1);

                }
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
            if ((getCell(currentSnakeHeadLine - 1,currentSnakeHeadCol)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine-1,currentSnakeHeadCol) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        } else if (dir == DOWN) {
            if ((getCell(currentSnakeHeadLine + 1,currentSnakeHeadCol)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine+1,currentSnakeHeadCol) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        } else if (dir == LEFT) {
            if ((getCell(currentSnakeHeadLine,currentSnakeHeadCol-1)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine,currentSnakeHeadCol-1) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        }else{
            if ((getCell(currentSnakeHeadLine,currentSnakeHeadCol+1)instanceof ObstacleCell)||(getCell(currentSnakeHeadLine,currentSnakeHeadCol+1) instanceof SnakeBodyCell)) {
                deadSnake(currentSnakeHeadLine,currentSnakeHeadCol);
                return false;
            }
        }
        return true;
    }

    private void findHead() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getCell(i, j) instanceof SnakeHeadCell) {
                    initialLine = i;
                    initialCol = j;
                    snakeHead = (SnakeHeadCell) getCell(initialLine, initialCol);
                    snakeHead.setCord(initialLine, initialCol);
                    snake.add(snakeHead);
                }
            }
        }
    }

    void putCell(int l, int c, Cell cell) {
        if (cell instanceof AppleCell) ++initialAppleCount;
        board[l][c] = cell;
    }

    private void deadSnake(int currentSnakeHeadLine, int currentSnakeHeadCol) {
        if(teletransportation){
            currentSnakeHeadLine=prevLineSnake;
            currentSnakeHeadCol=prevColSnake;
        }
        DeadSnakeHeadCell dead = new DeadSnakeHeadCell(currentSnakeHeadLine,currentSnakeHeadCol);
        update.cellCreated(currentSnakeHeadLine, currentSnakeHeadCol,dead);
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

    private void fillEmptyCells(Cell[][] board) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new EmptyCell();
            }
        }
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
        if(remApples> initialAppleCount)genNewApple();
        lastScoreStepCounter=stepCounter;
        updateNumbers();
        addAfterMove=true;
    }


    private void genNewApple() {
            int l1 = (int) Math.floor(Math.random() * (getHeight()));
            int c1 = (int) Math.floor(Math.random()* (getWidth()));
            AppleCell apple = new AppleCell();

            while (!isEmpty(l1,c1)) {
                l1 = (int) Math.floor(Math.random()* (getHeight()));
                c1 = (int) Math.floor(Math.random()* (getWidth()));
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

    public interface Observer {

        // Level.Listener
        void cellUpdated(int l, int c, Cell cell);

        void cellCreated(int l, int c, Cell cell);

        void cellRemoved(int l, int c);

        void cellMoved(int fromL, int fromC, int toL, int toC, Cell cell);

        void applesUpdated(int apples);

    }
}
