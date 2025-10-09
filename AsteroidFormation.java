import java.awt.*; 
import java.util.ArrayList; 
import java.util.Random;

/**
 * determines in which formation the asteroids will fall.
 */
public class AsteroidFormation {

    public ArrayList<Asteroid> asteroids = new ArrayList<>();

    Random random = new Random();
    int screenwidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private GameUI gameUI;

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    int width = screenwidth / 25;
    int height = screenheight / 15;

    /**
     * chooses a random shape to generate.
     */
    public void chooseShape() {
        int choice = random.nextInt(4);
        if (choice == 0) {
            rectangleGenerator();
        } else if (choice == 1) {
            zshapeGenerator();
        } else if (choice == 2) {
            circleGenerator();
        } else if (choice == 3) {
            heartGenerator();
        }
    }

    /**
     * generates 9 asteroids in a rectangular shape.
     */
    private void rectangleGenerator() {
        int amountOfRowsRectangle = 3;   //increase amount of asteroids per call with this.
        int amountOfColumnsRectangle = 3;

        int startX = random.nextInt(screenwidth - 400);
        int startY = 0;
        int spacingX = 100;
        int spacingY = 100;
        
        for (int row = 0; row < amountOfRowsRectangle; row++) {
            for (int col = 0; col < amountOfColumnsRectangle; col++) {
                int xCoordinate = startX + row * spacingX;
                int yCoordinate = startY + col * spacingY;
                asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
            }
        }
        gameUI.repaint();
        
    }

    /**
    * generates 9 asteroids in a zshape.
    */
    private void zshapeGenerator() {
        int amountOfRowsCircle = 4;   //increase amount of asteroids per call with this.

        int startX = random.nextInt(screenwidth - 500);
        int startY = 0;
        int spacingX = 100;
        int spacingY = 90;
        
        for (int row = 0; row < amountOfRowsCircle; row++) {
            if (row == 0 || row == 3) {
                for (int i = 0; i < 4; i++) {
                    int xCoordinate = startX + i * spacingX;
                    int yCoordinate = startY + row * spacingY;;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }
            } else {
                int xCoordinate = startX + (3 - row) * spacingX;
                int yCoordinate = startY + row * spacingY;
                asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
            }
        }
        gameUI.repaint();
    }
    
    /**
     * generates asteroids in a circular shape.
     */
    private void circleGenerator() {
        int numAsteroids = 9;  // number of asteroids around the circle
        int radius = 150;       // size of the circle

        int centerX = random.nextInt(screenwidth - 400) + 200;
        int centerY = 0;

        for (int i = 0; i < numAsteroids; i++) {
            double angle = 2 * Math.PI * i / numAsteroids;
            int xCoordinate = centerX + (int) (radius * Math.cos(angle));
            int yCoordinate = centerY + (int) (radius * Math.sin(angle));
            asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
        }
        gameUI.repaint(); 
    }  
    
    /**
    * generates 9 asteroids in a triangular shape.
    */
    public void heartGenerator() {
        int amountOfRowsCircle = 4;   //increase amount of asteroids per call with this.

        int startX = random.nextInt(screenwidth - 700) + 300;
        int startY = 0;
        int spacingX = 100;
        int spacingY = 90;
        
        for (int row = 0; row < amountOfRowsCircle; row++) {
            if (row == 0) {
                for (int i = 0; i < 5; i++) {
                    if (i == 0 || i == 2 || i == 4) {
                        int xCoordinate = startX + i * spacingX - 2 * spacingX;
                        int yCoordinate = startY + row * spacingY;
                        asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                    } else {
                        int xCoordinate = startX + i * spacingX - 2 * spacingX;
                        int yCoordinate = startY + row * spacingY - 50;
                        asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                    }
                }
            } else {
                for (int v = 0; v < 2; v++) {
                    int sign = (int) Math.pow(-1, v);
                    int xCoordinate = startX - sign * (3 - row) * spacingX;
                    int yCoordinate = startY + row * spacingY;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }

            }
        }
        gameUI.repaint();
    }

    /**
     * generates asteroids in TU/e shape.
     */
    public void tuegenerator() {
        int amountOfRowsT = 4; //increase amount of asteroids per call with this.
        int amountOfColumnsT = 3;
        int amountOfRowsU = 4;
        int amountOfColumnsU = 3;

        int startX = random.nextInt(screenwidth - 400);
        int startY = 0;
        int spacingX = 100;
        int spacingY = 100;
        
        //generate the T
        for (int col = 0; col < amountOfRowsT; col++) {
            if (col == 0) { 
                for (int row = 0; row < amountOfColumnsT; row++) {
                    int xCoordinate = startX + row * spacingX;
                    int yCoordinate = startY + col * spacingY;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                } 
            } else {
                for (int row = 0; row < amountOfColumnsT; row++) {
                    int xCoordinate = startX + spacingX;
                    int yCoordinate = startY + col * spacingY;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }
            }
        }

        //generate the U
        for (int row = 0; row < amountOfRowsU; row++) {
            for (int col = 0; col < amountOfColumnsU; col++) {
                if (col == 0 || col ==  5) {
                    int xCoordinate = startX + row * spacingX + 400;
                    int yCoordinate = startY + col * spacingY;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }
            }
        }
        
        gameUI.repaint();   
    }
}
        
    

