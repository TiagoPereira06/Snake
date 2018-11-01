package isel.poo.snake.model;

public class Cell {


    public static Cell newInstance(char type) {
        if (type =='X' ) return new ObstacleCell();
        if (type == '@' ) return  new SnakeHeadCell();
        if (type == 'A' ) return  new AppleCell();
        return  new EmptyCell();

    }
}
