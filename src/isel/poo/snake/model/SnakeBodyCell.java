package isel.poo.snake.model;

public class SnakeBodyCell extends Cell {
    private int line, col;

    SnakeBodyCell(int line, int col) {
        setCord(line, col);
    }

    SnakeBodyCell() {

    }

    int getLine() {
        return line;
    }

    void setCord(int line, int col) {
        this.col = col;
        this.line = line;
    }

    int getCol() {
        return col;
    }
}