
import javax.swing.*;
import java.awt.*;
public class SnakeApp  implements Runnable  {


    static final int DEFAULT_GRID_WIDTH = 30;
    static final int DEFAULT_GRID_HEIGHT = 30;
    static GameView gameView;
    static GameController gameController;

    public void run() {

        JFrame window = new JFrame("Snake");               //creat game window

        Container contentPane = window.getContentPane();
        // use Grid initialize the gamaView
        Grid grid = new Grid(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);
        gameView = new GameView(grid);
        gameView.init();

        // set JPanel's size
        gameView.getCanvas().setPreferredSize(new Dimension(DEFAULT_GRID_WIDTH * gameView.DEFAULT_NODE_SIZE,
                DEFAULT_GRID_HEIGHT * gameView.DEFAULT_NODE_SIZE));
        // add JPanel to windows
        contentPane.add(gameView.getCanvas(), BorderLayout.CENTER);

        // draw grid and snake
        window.pack();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        gameController = new GameController(grid, gameView);
        window.addKeyListener(gameController);

        // start the thread
        new Thread(gameController).start();

    }

    public static void main(String[] args) {
        //SnakeApp snakeApp = new SnakeApp();
        //snakeApp.run();
        SwingUtilities.invokeLater(new SnakeApp());
    }


}
