import java.awt.*;
import java.util.Iterator;

/**
 * detects whether an asteroid hits the ground or is hit by a bullet.
 */
public class Hitdetection {
    int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;


    private AsteroidFormation asteroidFormation;
    private GameUI gameUI;

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }
    
    public void setAsteroidFormation(AsteroidFormation asteroidFormation) {
        this.asteroidFormation = asteroidFormation;
    }
    
    /**
     * removes asteroids if they hit the ground.
     */
    public void asteroidGroundDetecter() {
        Iterator<Asteroid> iterator = asteroidFormation.asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid a = iterator.next();
            if (a.yCoordinate + a.height >= screenheight) {
                iterator.remove();
            }
        }
    }
}
