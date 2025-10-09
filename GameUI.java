import java.awt.*; 
import java.util.Random;
import javax.swing.*;



/**
 * creates the UI the player uses to access the game.
 */
public class GameUI extends JFrame {
    int gameSpeed = 10;
    int fallingSpeed = 5;
    
    Color colorOfAsteroid = new Color(255, 255, 255);

    Random random = new Random();

    private GamePanel gamePanel;

    boolean validPositionForAsteroid;

    private AsteroidFormation asteroidFormation;
    private Hitdetection hitdetection;

    /**
     * regulates the uses of methods in the GameUI class.
     */
    public GameUI() {
        asteroidFormation = new AsteroidFormation();
        asteroidFormation.setGameUI(this);

        hitdetection = new Hitdetection();
        hitdetection.setGameUI(this);

        hitdetection.setAsteroidFormation(asteroidFormation);
        frame();
    }

    /**
     * creates the frame of the game.
     */
    void frame() { 
        //sets up original frame and panel
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setResizable(false); 
        //this.setUndecorated(true);

        gamePanel = new GamePanel(); 
        gamePanel.setBackground(Color.BLACK);
        this.setContentPane(gamePanel);

        this.setVisible(true);

        asteroidFormation.rectangleGenerator();
        Timer timer = new Timer(gameSpeed, e -> {
            
            // move all asteroids in the list asteroids down
            for (Asteroid a : asteroidFormation.asteroids) {
                a.yCoordinate += fallingSpeed;
            }
            //int r = random.nextInt(256);
            //int g = random.nextInt(256);
            //int b = random.nextInt(256);
            //colorOfAsteroid = new Color(r, g, b);
            hitdetection.asteroidGroundDetecter();
            gamePanel.repaint();

            //for now generate new rectangular shape when they hit the bottom
            if (asteroidFormation.asteroids.isEmpty()) {
                asteroidFormation.rectangleGenerator();
            }
            
        });
        timer.start();
    }


    private class GamePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            g.setColor(colorOfAsteroid);

            for (Asteroid a : asteroidFormation.asteroids) {
                g.fillOval(a.xCoordinate, a.yCoordinate, a.width, a.height);
            }
        }
    }
}