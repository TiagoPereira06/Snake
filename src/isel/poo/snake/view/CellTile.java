package isel.poo.snake.view;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.*;

public class CellTile extends Tile {
    //TODO:

    public static final int SIDE = 1;
/*
    private final Cell cell;


    public CellTile() {
        this(null);
    }

    public CellTile(Cell cell) {
        this.cell = cell;
        setSize(1, 1);
    }

    @Override
    public void paint() {
        super.paint();
        if (cell instanceof EmptyCell) printEmptyTile();
       else printElementTile();
    }

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

        if(cell instanceof EmptyCell)
            return new EmptyTile();

        if(cell instanceof ObstacleCell)
            return new ObstacleTile();

        if(cell instanceof SnakeHeadCell)
            return new SnakeHeadTile();

        if(cell instanceof AppleCell)
            return new AppleTile();

        return null;
    }

}
