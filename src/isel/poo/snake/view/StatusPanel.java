package isel.poo.snake.view;

import isel.leic.pg.Console;
import isel.poo.console.FieldView;
import isel.poo.console.View;

public class StatusPanel extends View {
    public static int HEIGHT = 10, WIDTH = 8;

    public static int apples,level,score;
    private FieldView Level = new FieldView("Level", 1, 0, "---");
    private FieldView Apples = new FieldView("Apples", 4, 0, "---");
    private FieldView Score = new FieldView("Score", 7, 0, "---");

    public StatusPanel(int winWidth) {
        super(0, winWidth, HEIGHT, WIDTH, Console.LIGHT_GRAY);
        WIDTH = winWidth;
        addView(Level);
        addView(Apples);
        addView(Score);


    }


    public void setApples(int apples) {
        this.Apples.setValue(apples);
        this.apples=apples;
    }

    public void setScore(int score) {
        this.Score.setValue(score);
        this.score=score;
    }

    public void setLevel(int level) {
        this.Level.setValue(level);
        this.level=level;
    }
}