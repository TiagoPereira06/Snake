package isel.poo.snake.model;

public class Cell {


    public static Cell newInstance(char type) {
        switch (type) {
            case ' ':
                return new EmptyCell();
            case 'X':
                return new ObstacleCell();
            case '@':
                return new SnakeHeadCell();
            case 'A':
                return new AppleCell();

        }
        return null;

    }
}
