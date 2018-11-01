package isel.poo.snake.model;

public class SnakeBodyCell extends Cell {
    private int line, col;

    public SnakeBodyCell(int x, int y){
        this.line = x;
        this.col = y;
    }

    public SnakeBodyCell(){
    }


    public int getLine() {
        return line;
    }

    public void setCord(int x,int y) {
        col = y;
        line = x;
    }

    public int getCol() {
        return col;
    }
}

