package isel.poo.snake.model;

public class SnakeBodyCell extends Cell {
    private int X,Y;

    public SnakeBodyCell(int x, int y){
        this.X=x;
        this.Y=y;
    }

    public SnakeBodyCell(){
    }


    public int getX() {
        return X;
    }

    public void setCord(int x,int y) {
        Y=y;
        X = x;
    }

    public int getY() {
        return Y;
    }
}

