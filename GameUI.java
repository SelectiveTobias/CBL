import java.awt.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * creates the UI the player uses to access the game.
 */
public class GameUI extends JFrame implements ActionListener {
    int screenwidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
    String command;
    int gameSpeed = 4;
    int fallingSpeed = 5;
    
    Color colorOfAsteroid = new Color(255, 255, 255);

    Random random = new Random();

    private GamePanel gamePanel;

    boolean validPositionForAsteroid;

    private AsteroidFormation asteroidFormation;
    private Hitdetection hitdetection;
    JButton buttonExit;

    private Ship ship;

    /**
     * regulates the uses of methods in the GameUI class.
     */
    public GameUI() {
        asteroidFormation = new AsteroidFormation();
        asteroidFormation.setGameUI(this);

        ship = new Ship(screenwidth, screenheight);

        hitdetection = new Hitdetection();
        hitdetection.setGameUI(this);

        hitdetection.setAsteroidFormation(asteroidFormation);

        frame();
        asteroidDisplay();
        shipControl();
        bulletMovement();
    }

    /**
     * creates the frame of the game.
     */
    void frame() { 
        //sets up original frame and panel
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setResizable(false); 
        this.setUndecorated(true);

        gamePanel = new GamePanel(); 
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setLayout(null);
        gamePanel.setFocusable(true);
        
        buttonExit = createButtonExit();
        gamePanel.add(buttonExit);
        this.setContentPane(gamePanel);

        this.setVisible(true);
    }

    private void shipControl() {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            // handle key presses to move the ship
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_LEFT) {
                    ship.moveLeft();
                    gamePanel.repaint();
                } else if (code == KeyEvent.VK_RIGHT) {
                    ship.moveRight();
                    gamePanel.repaint();
                }
            }
        });
    }

    private void asteroidDisplay() {
        asteroidFormation.tuegenerator();
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
                asteroidFormation.chooseShape();
            } 
        });
        timer.start();
    }

    private void bulletMovement() {
        // move bullets up and remove if off-screen
        Timer bulletMoveTimer = new Timer(gameSpeed, e -> {
            List<Bullets> bulletsList = Bullets.getBulletsList();
            bulletsList.removeIf(bullet -> bullet.getBulletY() < -bullet.getHeightBullet());
            for (Bullets bullet : bulletsList) {
                bullet.moveUp();
            }
            gamePanel.repaint();
        });
        bulletMoveTimer.start();

        // spawn new bullets at regular intervals
        Timer bulletSpawnTimer = new Timer(Bullets.BULLET_SPAWN_DELAY, e -> {
            Bullets.addBullet(new Bullets(ship.getShipX(), ship.getShipY(), ship.getWidthShip()));
        });
        bulletSpawnTimer.start();
    }

    private class GamePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // draw asteroids
            g.setColor(colorOfAsteroid);
            for (Asteroid a : asteroidFormation.asteroids) {
                g.fillOval(a.xCoordinate, a.yCoordinate, a.width, a.height);
            }

            // draw the ship at the bottom
            g.setColor(Color.GREEN);
            g.fillRect(
                ship.getShipX(), 
                ship.getShipY(), 
                ship.getWidthShip(), 
                ship.getHeightShip()
                );

            // draw bullets
            g.setColor(Color.RED);
            for (Bullets bullet : Bullets.getBulletsList()) {
                g.fillRect(
                    bullet.getBulletX(),
                    bullet.getBulletY(),
                    bullet.getWidthBullet(),
                    bullet.getHeightBullet()
                );
            }       
        }
    }

    private JButton createButtonExit() { 
        JButton buttonExit = new JButton("Exit");
        buttonExit.setFont(new Font("Times New Roman", Font.PLAIN, 25)); 
        buttonExit.setBorderPainted(true); 
        buttonExit.setBounds(screenwidth - 95, 20, 75, 30); 
        buttonExit.setBackground(Color.red); 
        buttonExit.setForeground(Color.WHITE);
        buttonExit.setFocusPainted(false); 
        buttonExit.setBorder(null); 
        buttonExit.setActionCommand("exit"); 
        buttonExit.addActionListener(this);
        buttonExit.setFocusable(false);
        return buttonExit;
    }

    @Override 
    public void actionPerformed(ActionEvent e) { 
        command = e.getActionCommand(); 
        if (command.equals("exit")) {
            System.exit(0);
        }
    }
}