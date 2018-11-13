package isel.poo.snake.model;

public class MouseCell extends Cell {
    private int line, col;

    MouseCell(int line, int col) {
        setCord(line, col);
    }

    MouseCell() {
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

