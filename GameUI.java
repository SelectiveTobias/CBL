import java.awt.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * creates the UI the player uses to access the game.
 */
public class GameUI extends JFrame implements ActionListener {
    int screenwidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
    String command;
    int gameSpeed = 4;
    int fallingSpeed = 1;
    int timesPerLevel = 4;
    int savedScore;
    private boolean isGameOver = false;
    private int bulletSpawnDelay = Bullets.BULLET_SPAWN_DELAY; 
    
    Color colorOfAsteroid = new Color(255, 255, 255);

    Random random = new Random();

    public GamePanel gamePanel;

    boolean validPositionForAsteroid;

    private AsteroidFormation asteroidFormation;
    private Hitdetection hitdetection;
    private GameOver gameOver;
    JButton buttonExit;
    JPanel gameOverPanel;

    int timesPerformed = 0;

    private Ship ship;

    private long lastBulletSpawnTime = 0;


    /**
     * regulates the uses of methods in the GameUI class.
     */
    public GameUI() {
        asteroidFormation = new AsteroidFormation();
        ship = new Ship(screenwidth, screenheight);
        
        gameOver = new GameOver();
        hitdetection = new Hitdetection();

        gameOver.setGameUI(this);
        hitdetection.setAsteroidFormation(asteroidFormation);
        hitdetection.setGameUI(this);

        frame();
        startGameLoop();
        shipControl();

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

    private Timer gameLoopTimer;

    private void startGameLoop() {
        int frameDelay = 16; // 60 FPS

        gameLoopTimer = new Timer(frameDelay, e -> {
            if (isGameOver) {
                return;
            }

            // Move asteroids
            for (Asteroid a : asteroidFormation.asteroids) {
                a.yCoordinate += fallingSpeed;
            }

            // Spawn bullets
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBulletSpawnTime >= bulletSpawnDelay) {
                Bullets.addBullet(new Bullets(ship.getShipX(),
                    ship.getShipY(), ship.getWidthShip()));
                lastBulletSpawnTime = currentTime;
            }

            // Move bullets up
            List<Bullets> bulletsList = Bullets.getBulletsList();
            bulletsList.removeIf(bullet -> bullet.getBulletY() < -bullet.getHeightBullet());
            for (Bullets bullet : bulletsList) {
                bullet.moveUp();
            }

            // Collisions
            hitdetection.asteroidGroundDetecter();
            hitdetection.bulletAsteroidDetector();

            // Repaint once per frame
            gamePanel.repaint();

            // Spawn new asteroids when cleared
            if (asteroidFormation.asteroids.isEmpty()) {
                asteroidFormation.chooseShape();
                timesPerformed++;

                if (timesPerformed % timesPerLevel == 0) {
                    fallingSpeed += 1;
                    if (bulletSpawnDelay > 100) {
                        bulletSpawnDelay -= 35;
                    }
                }
            }
        });
        gameLoopTimer.start();
    }


    private void shipControl() {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            // handle key presses to move the ship
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_LEFT) {
                    ship.moveLeft();
                    SwingUtilities.invokeLater(() -> gamePanel.repaint());;
                } else if (code == KeyEvent.VK_RIGHT) {
                    ship.moveRight();
                    SwingUtilities.invokeLater(() -> gamePanel.repaint());;
                }
            }
        });
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
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        gameOverPanel.setBounds(0, 0, screenwidth, screenheight);
        gameOverPanel.setBackground(new Color(0, 0, 0, 180));

        JLabel gameOverText = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverText.setFont(new Font("Arial", Font.BOLD, 100));
        gameOverText.setForeground(Color.WHITE);
        gameOverText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton goToHomeScreen = createHomeScreenButton();

        // Add vertical spacing and alignment
        gameOverPanel.add(Box.createVerticalGlue());       // pushes content to center vertically
        gameOverPanel.add(gameOverText);
        gameOverPanel.add(Box.createRigidArea(
            new Dimension(0, 40))); // spacing between text & button
        gameOverPanel.add(goToHomeScreen);
        gameOverPanel.add(Box.createVerticalGlue());       // keeps it centered vertically

        gameOverPanel.setVisible(true);
        return gameOverPanel;
    }

    private JButton createHomeScreenButton() {
        JButton goToHomescreen = new JButton("Main Menu");
        goToHomescreen.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        goToHomescreen.setPreferredSize(new Dimension(250, 80));
        goToHomescreen.setMaximumSize(new Dimension(250, 80)); // fixes width in BoxLayout
        goToHomescreen.setAlignmentX(Component.CENTER_ALIGNMENT); // centers horizontally
        goToHomescreen.setBackground(Color.GRAY);
        goToHomescreen.setForeground(Color.WHITE);
        goToHomescreen.setFocusPainted(false);
        goToHomescreen.setActionCommand("back");
        goToHomescreen.addActionListener(this);
        return goToHomescreen;
    }
    


    /**
     * ends the game by setting isGameOver to true and triggering the game over panel.
     */
    public void triggerGameEnd() {
        if (isGameOver) {
            return;
        }
        isGameOver = true;
        savedScore = hitdetection.getScore();
        if (savedScore > Scoreboard.readScore()) {
            Scoreboard.writeScore(savedScore);
        }
        SwingUtilities.invokeLater(() -> {
            gameOver.gameEnder();
        });
    }

    @Override 
    public void actionPerformed(ActionEvent e) { 
        command = e.getActionCommand(); 
        if (command.equals("exit")) {
            System.exit(0);
        } else if (command.equals("back")) {
            this.dispose();
            new Homescreen();
        }
    }
}