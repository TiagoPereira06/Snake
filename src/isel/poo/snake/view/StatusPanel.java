package isel.poo.snake.view;

import isel.leic.pg.Console;
import isel.poo.console.FieldView;
import isel.poo.console.View;

public class StatusPanel extends View {
    public static final int HEIGHT=0,WIDTH = 0;
    public int apples;
    public int score;
    public int level;


    public StatusPanel(int winWidth) {

    }


    public void setApples(int apples) {
        this.apples=apples;
    }

    public void setScore(int score) {
        this.score=score;
    }

    public void setLevel(int number) {
        this.level=level;
    }
}
