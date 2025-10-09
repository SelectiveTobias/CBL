import java.awt.*;


/**
 * creates the asteroid that fall down in the game.
 */
public class Asteroid {

    public Color colorOfAsteroid = null; //new Color(153, 140, 137);
    int height;
    int width;
    int xCoordinate;
    int yCoordinate;

    /**
     * generates the asteroid on the top of the screen.
     * @param xCoordinate the x coordinate where the asteroid is generated.
     * @param width width of the asteroid.
     * @param height height of the asteroid.
     */
    public Asteroid(int xCoordinate, int yCoordinate, int width, int height) {
        this.xCoordinate = xCoordinate;
        this.width = width;
        this.height = height;
        this.yCoordinate = yCoordinate;
    }

}
