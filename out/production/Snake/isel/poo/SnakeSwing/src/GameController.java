
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameController  implements Runnable, KeyListener {

    private final Grid grid;
    private final GameView gameView;

    private boolean running;

    public GameController(Grid grid, GameView gameView) {
        this.grid = grid;
        this.gameView = gameView;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(Math.max(50, 200 - grid.getScore() / 5 * 30));                     //DEFAULT_MOVE_INTERVAL
            } catch (InterruptedException e) {
                break;
            }
            grid.isDirectionChanged = false;
            if (grid.nextRound()) {
                gameView.draw();
            } else {
                System.out.print("Congraduations! Your scores: " + grid.getScore());
                gameView.showGameOverMessage();
                running = false;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (grid.isDirectionChanged == false) {
            switch (keyCode) {
                case KeyEvent.VK_UP :
                    grid.changeDirection(Direction.UP);
                    break;
                case KeyEvent.VK_RIGHT :
                    grid.changeDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_DOWN :
                    grid.changeDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT :
                    grid.changeDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_SPACE :
                    break;
            }
        }
        // repaint the canvas
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

}
