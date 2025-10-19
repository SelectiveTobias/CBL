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
    int fallingSpeed = 1;
    private boolean isGameOver = false;
    
    Color colorOfAsteroid = new Color(255, 255, 255);

    Random random = new Random();

    public GamePanel gamePanel;

    boolean validPositionForAsteroid;

    private AsteroidFormation asteroidFormation;
    private Hitdetection hitdetection;
    private Homescreen homescreen;
    private GameOver gameOver;
    JButton buttonExit;
    JPanel gameOverPanel;


    private Ship ship;

    /**
     * regulates the uses of methods in the GameUI class.
     */
    public GameUI() {
        asteroidFormation = new AsteroidFormation();
        hitdetection = new Hitdetection();
        ship = new Ship(screenwidth, screenheight);
        gameOver = new GameOver();

        asteroidFormation.setGameUI(this);
        gameOver.setGameUI(this);
        hitdetection.setAsteroidFormation(asteroidFormation);
        hitdetection.setGameUI(this);

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
        this.setExtendedState(MAXIMIZED_BOTH); 
        this.setResizable(true); 
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
        //asteroidFormation.tuegenerator();
        Timer timer = new Timer(gameSpeed, e -> {
            //stops updating when the game is over
            if (isGameOver) {
                return;
            }
            // move all asteroids in the list asteroids down
            for (Asteroid a : asteroidFormation.asteroids) {
                a.yCoordinate += fallingSpeed;
            }
            //int r = random.nextInt(256);
            //int g = random.nextInt(256);
            //int b = random.nextInt(256);
            //colorOfAsteroid = new Color(r, g, b);
            hitdetection.asteroidGroundDetecter();
            hitdetection.bulletAsteroidDetector();
            gamePanel.repaint();
            //System.out.println(homescreen.score);

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
            //stops updating when the game is over
            if (isGameOver) {
                return;
            }
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
            //stops updating when the game is over
            if (isGameOver) {
                return;
            }
            Bullets.addBullet(new Bullets(ship.getShipX(), ship.getShipY(), ship.getWidthShip()));
        });
        bulletSpawnTimer.start();
    }

    /**
     * displays the gamePanel.
     */
    public class GamePanel extends JPanel {
        private final Image heartImage;

        /**
         * made to load the heartImage.
         */
        public GamePanel() {
            // Load the heart image once
            java.net.URL imgURL = getClass().getResource("/heartIconForLives.png");
            if (imgURL != null) {
                heartImage = new ImageIcon(imgURL).getImage()
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            } else {
                System.err.println("Heart image not found!");
                heartImage = null;
        }
    }

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
            // draw hearts for lives
            if (heartImage != null) {
                for (int i = 0; i < hitdetection.lives; i++) {
                    g.drawImage(heartImage, 20 + i * 35, 20, null);
                }
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

    /**
     * creates the Game Over Panel.
     * @return the Panel
     */
    public JPanel createGameOverPanel() {
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BorderLayout());
        gameOverPanel.setBounds(0, 0, screenwidth, screenheight);
        gameOverPanel.setBackground(new Color(0, 0, 0, 180));

        JLabel gameOverText = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverText.setFont(new Font("Arial", Font.BOLD, 100));
        gameOverText.setForeground(Color.WHITE);

        gameOverPanel.add(gameOverText, BorderLayout.CENTER);
        gameOverPanel.setVisible(true);

        JButton goToHomescreen = new JButton();
        //THIS STIL NEEDS THE CODE to GO TO THE HOMESCREEN

        return gameOverPanel;
    }

    /**
     * ends the game by setting isGameOver to true and triggering the game over panel.
     */
    public void triggerGameEnd() {
        if (isGameOver) {
            return;
        }
        isGameOver = true;
        SwingUtilities.invokeLater(() -> {
            gameOver.gameEnder();
        });
    }

    @Override 
    public void actionPerformed(ActionEvent e) { 
        command = e.getActionCommand(); 
        if (command.equals("exit")) {
            System.exit(0);
        }
    }
}