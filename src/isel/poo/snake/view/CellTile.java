package isel.poo.snake.view;

import isel.poo.console.tile.Tile;
import isel.poo.snake.model.*;

public class CellTile extends Tile {
    //TODO:
    public static final int SIDE = 5;

    public static Tile tileOf(Object cell) {

        if(cell == null)
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
