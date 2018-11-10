package isel.poo.snake.view;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.*;

public class CellTile extends Tile {

    private int back;
    private int fore;
    private char character;
    public static final int SIDE = 1;

    public CellTile(int backgroundColor,int foregroundColor, char c) {
        this.back =backgroundColor;
        this.fore =foregroundColor;
        this.character=c;
    }

    @Override
    public void paint() {
        super.paint();
        Console.setBackground(this.back);
        Console.setForeground(this.fore);
        print(0,0,this.character);
    }


    public static Tile tileOf(Object cell) {

        if(cell instanceof ObstacleCell)
            return new ObstacleTile();

        if(cell instanceof SnakeHeadCell)
            return new SnakeHeadTile();

        if(cell instanceof AppleCell)
            return new AppleTile();

        if(cell instanceof MouseCell)
            return new MouseTile();

        if(cell instanceof DeadSnakeHeadCell)
            return new DeadSnakeHeadTile();

        if(cell instanceof SnakeBodyCell)
            return new SnakeBodyTile();


        return new EmptyTile();
    }

}
