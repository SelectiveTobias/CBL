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
        int amountOfRowsT = 3; //increase amount of asteroids per call with this.
        int amountOfColumns = 4;
        int amountOfRowsU = 3;
        int amountOfRowsSlash = 4;
        int amountOfColumnse = 6;

        int startX = random.nextInt(screenwidth - 1300);
        int startY = 0;
        int startXe = startX + 850;
        int spacingX = 100;
        int spacingYForT = 90;
        int spacingYForU = 100;
        int spacingXSlash = 40;
        int spacingYSlash = 90;
        int spacingXFore = 25;
        int spacingYFore = 80;
        
        //generate the T
        for (int col = 0; col < amountOfColumns; col++) {
            if (col == 0) { 
                for (int row = 0; row < amountOfRowsT; row++) {
                    int xCoordinate = startX + row * spacingX;
                    int yCoordinate = startY + col * spacingYForT;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                } 
            } else {
                for (int row = 0; row < amountOfColumns; row++) {
                    int xCoordinate = startX + spacingX;
                    int yCoordinate = startY + col * spacingYForT;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }
            }
        }

        //generate the U
        for (int col = 0; col < amountOfColumns; col++) {
            for (int row = 0; row < amountOfRowsU; row++) {
                if (col == 0) {
                    int xCoordinate = startX + 350;    
                    int yCoordinate = startY + row * spacingYForU;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                } else if (col == 3) {
                    int xCoordinate = startX + spacingX + 420;
                    int yCoordinate = startY + row * spacingYForU;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }
            } if (col == 1) {
                    int xCoordinate = startX + 400;
                    int yCoordinate = startY + 270;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                } else if (col == 2) {
                    int xCoordinate = startX + 470;
                    int yCoordinate = startY + 270;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
        }
    }

        //generate the /
        for (int row = 0; row < amountOfRowsSlash; row++) {
            int xCoordinate = startX + (3 - row) * spacingXSlash + 650;
            int yCoordinate = startY + row * spacingYSlash;
            asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
        }

        //generate the e
        for (int col = 0; col < amountOfColumnse; col++) {
            if (col == 0) {
                for (int i = 0; i < 2; i++) {
                    int xCoordinate = startXe + 3 * i * spacingXFore + 100;
                    int yCoordinate = startY;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height)); 
                }
            } else if (col == 1) {
                for (int i = 0; i < 2; i++) {
                    int xCoordinate = startXe + 8 * i * spacingXFore + 35;
                    int yCoordinate = startY + spacingYFore;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                } 
            } else if (col == 2) { 
                for (int i = 0; i < 3; i++) {
                    int xCoordinate = startXe + 3 * i * spacingXFore + 90;
                    int yCoordinate = startY + 2 * spacingYFore;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }
            } else if (col == 3) {
                for (int i = 0; i < 2; i++) {
                    int xCoordinate = startXe + i * spacingXFore + spacingXFore;
                    int yCoordinate = startY + i * spacingYFore + 2 * spacingYFore;
                    asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
                }
            } else if (col == 4) {
                int xCoordinate = startXe + 5 * spacingXFore;
                int yCoordinate = startY + 3 * spacingYFore + 20;
                asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
            } else {
                int xCoordinate = startXe + 8 * spacingXFore;
                int yCoordinate = startY + 3 * spacingYFore + 25;
                asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
            }
        } 
        gameUI.repaint();   
    }
}
        
    

