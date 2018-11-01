package isel.poo.snake.view;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.*;

public class CellTile extends Tile {
    //TODO:
    private int color;
    private char character;
    public static final int SIDE = 1;

    public CellTile(int color, char c) {
        this.color=color;
        this.character=c;
    }

    @Override
    public void paint() {
        super.paint();
        Console.setBackground(this.color);
        Console.setForeground(Console.BLACK);
        print(0,0,this.character);
    }
/*
    private void printElementTile() {

        char elementSymbol = '@';
        int color = Console.BLACK;
        if (cell instanceof Robot)
            elementSymbol = '+';
        else if (cell instanceof JunkPile)
            elementSymbol = '*';

        Console.setBackground(color);
        print(0, 0, elementSymbol);
    }
    private void printEmptyTile() {
        print(0, 0, ' ');
    }
*/

    public static Tile tileOf(Object cell) {

        if(cell instanceof ObstacleCell)
            return new ObstacleTile();

        if(cell instanceof SnakeHeadCell)
            return new SnakeHeadTile();

        if(cell instanceof AppleCell)
            return new AppleTile();

        if(cell instanceof SnakeBodyCell)
            return new SnakeBodyTile();

        return new EmptyTile();
    }

}
