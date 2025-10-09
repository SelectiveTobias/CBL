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

    /**
     * generates 9 asteroids in a rectangular shape.
     */
    public void rectangleGenerator() {
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
                int width = screenwidth / 25;
                int height = screenheight / 15;
                asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
            }
        }
        gameUI.repaint();
        
    }

    /**
    * generates 9 asteroids in a circular shape.
    */
    public void circleGenerator() {
        int amountOfRowsCircle = 3;   //increase amount of asteroids per call with this.
        int amountOfColumnsCircle = 3;

        int startX = random.nextInt(screenwidth - 400);
        int startY = 0;
        int spacingX = 100;
        int spacingY = 100;
        
        for (int row = 0; row < amountOfRowsCircle; row++) {
            for (int col = 0; col < amountOfColumnsCircle; col++) {
                int xCoordinate = startX + row * spacingX;
                int yCoordinate = startY + col * spacingY;
                int width = screenwidth / 25;
                int height = screenheight / 15;
                asteroids.add(new Asteroid(xCoordinate, yCoordinate, width, height));
            }
        }
        gameUI.repaint();
        
    }
}
