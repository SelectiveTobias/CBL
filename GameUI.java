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
    int timesPerLevel = 4;
    int savedScore;
    private boolean isGameOver = false;
    private int bulletSpawnDelay = Bullets.BULLET_SPAWN_DELAY; 
    
    Color colorOfAsteroid = new Color(255, 255, 255);

    Random random = new Random();

    public GamePanel gamePanel;

    private AsteroidFormation asteroidFormation;
    private Hitdetection hitdetection;
    private GameOver gameOver;
    JButton buttonExit;
    JPanel gameOverPanel;
    JLabel liveScore;

    int timesPerformed = 0;

    private Ship ship;

    private long lastBulletSpawnTime = 0;

    // Intro flag
    private boolean isIntro = true;
    private final int introFallingSpeed = 5;  // TU/e intro falls faster
    private int normalFallingSpeed = 1;       // normal game falling speed

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

        // TU/e intro
        asteroidFormation.tuegenerator();

        frame();
        startGameLoop();
        shipControl();
    }

    /**
     * creates the frame of the game.
     */
    void frame() { 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setExtendedState(MAXIMIZED_BOTH); 
        this.setResizable(true); 
        this.setUndecorated(true);

        gamePanel = new GamePanel(); 
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setLayout(null);
        gamePanel.setFocusable(true);

        liveScore = liveScore(); 
        gamePanel.add(liveScore);
        
        buttonExit = createButtonExit();
        gamePanel.add(buttonExit);
        this.setContentPane(gamePanel);

        this.setVisible(true);
    }

    private Timer gameLoopTimer;

    private void startGameLoop() {
        int frameDelay = 16; // 60 FPS

        gameLoopTimer = new Timer(frameDelay, e -> {
            // Move asteroids
            int currentFallingSpeed = isIntro ? introFallingSpeed : normalFallingSpeed;
            for (Asteroid a : asteroidFormation.asteroids) {
                a.yCoordinate += currentFallingSpeed;
            }

            if (isIntro) {
                // Check if all intro asteroids reached bottom
                boolean allReachedBottom = asteroidFormation.asteroids.stream()
                    .allMatch(a -> a.yCoordinate + a.height >= screenheight);

                if (allReachedBottom) {
                    asteroidFormation.asteroids.clear();
                    isIntro = false;
                    timesPerformed = 0;
                    bulletSpawnDelay = Bullets.BULLET_SPAWN_DELAY;
                }
                // During intro, no bullets and no collisions
            } else {
                // Normal game logic
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastBulletSpawnTime >= bulletSpawnDelay) {
                    Bullets.addBullet(new Bullets(ship.getShipX(),
                        ship.getShipY(), ship.getWidthShip()));
                    lastBulletSpawnTime = currentTime;
                }

                List<Bullets> bulletsList = Bullets.getBulletsList();
                bulletsList.removeIf(bullet -> bullet.getBulletY() < -bullet.getHeightBullet());
                for (Bullets bullet : bulletsList) {
                    bullet.moveUp();
                }

                hitdetection.asteroidGroundDetecter();
                hitdetection.bulletAsteroidDetector();
                liveScore.setText("Score " + hitdetection.score);

                //generate new asteroids when old ones are gone
                if (asteroidFormation.asteroids.isEmpty()) {
                    asteroidFormation.chooseShape();
                    timesPerformed++;

                    //every timesPerLevel times it gets harder
                    if (timesPerformed % timesPerLevel == 0) {
                        normalFallingSpeed += 1;
                        if (bulletSpawnDelay > 100) {
                            bulletSpawnDelay -= 35;
                        }
                    }
                }
            }

            gamePanel.repaint();
        });

        gameLoopTimer.start();
    }

    private void shipControl() {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
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
     * gamePanel so we can draw in the actual gamewindow.
     */
    public class GamePanel extends JPanel {
        private final Image heartImage;

        /**
         * this is for the hearts on top of the screen.
         */
        public GamePanel() {
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

            // draw the ship
            g.setColor(Color.GREEN);
            g.fillRect(ship.getShipX(), ship.getShipY(), ship.getWidthShip(), ship.getHeightShip());

            // draw bullets
            g.setColor(Color.RED);
            for (Bullets bullet : Bullets.getBulletsList()) {
                g.fillRect(bullet.getBulletX(), bullet.getBulletY(), 
                    bullet.getWidthBullet(), bullet.getHeightBullet());
            }   

            // draw hearts
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

    private JLabel liveScore() {
        JLabel liveScore = new JLabel();
        int yPositioning = 55;
        int xPositioning = 20;
        liveScore.setBounds(xPositioning, yPositioning, 150, 25);  
        liveScore.setFont(new Font("Times new Roman", Font.BOLD, 30)); 
        liveScore.setOpaque(false); 
        liveScore.setBackground(Color.BLACK); 
        liveScore.setForeground(Color.WHITE); 
        return liveScore; 
    }

    /**
     * creates gameOverPanel.
     * @return the panel
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

        gameOverPanel.add(Box.createVerticalGlue());
        gameOverPanel.add(gameOverText);
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        gameOverPanel.add(goToHomeScreen);
        gameOverPanel.add(Box.createVerticalGlue());

        gameOverPanel.setVisible(true);
        return gameOverPanel;
    }

    private JButton createHomeScreenButton() {
        JButton goToHomescreen = new JButton("Main Menu");
        goToHomescreen.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        goToHomescreen.setPreferredSize(new Dimension(250, 80));
        goToHomescreen.setMaximumSize(new Dimension(250, 80));
        goToHomescreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        goToHomescreen.setBackground(Color.GRAY);
        goToHomescreen.setForeground(Color.WHITE);
        goToHomescreen.setFocusPainted(false);
        goToHomescreen.setActionCommand("back");
        goToHomescreen.addActionListener(this);
        return goToHomescreen;
    }

    /**
     * makes the game stop and resets for the next play.
     */
    public void triggerGameEnd() {
        Bullets.clearBullets();
        if (isGameOver) {
            return;
        }
        isGameOver = true;
        savedScore = hitdetection.getScore();
        if (gameLoopTimer != null) {
            gameLoopTimer.stop(); // stop the old loop
        }
        if (savedScore > Scoreboard.readScore()) {
            Scoreboard.writeScore(savedScore);
        }
        SwingUtilities.invokeLater(() -> gameOver.gameEnder());
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
