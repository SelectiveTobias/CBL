import java.awt.*;
import java.util.Iterator;

/**
 * detects whether an asteroid hits the ground or is hit by a bullet.
 */
public class Hitdetection {
    int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private AsteroidFormation asteroidFormation;
    int score = 0;
    
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

    /**
     * removes bulllets when they touch the top of the screen.
     */
    public void bulletTopDetector() {
        Iterator<Bullets> iterator = Bullets.bulletsList.iterator();
        while (iterator.hasNext()) {
            Bullets b = iterator.next();
            if (b.yCoordinateBullet + b.heightBullet < 0) {
                iterator.remove();
            }
        }
    }

    /**
     * removes bulllets and asteroid when they touch each other.
     */
    public void bulletAsteroidDetector() {
        Iterator<Bullets> bulletIterator = Bullets.bulletsList.iterator();

        while (bulletIterator.hasNext()) {
            Bullets bullet = bulletIterator.next();

            Iterator<Asteroid> asteroidIterator = asteroidFormation.asteroids.iterator();

            while (asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();

                boolean yOverlap = bullet.yCoordinateBullet < asteroid.yCoordinate + asteroid.height
                    && bullet.yCoordinateBullet + bullet.heightBullet > asteroid.yCoordinate;

                boolean xOverlap = bullet.xCoordinateBullet < asteroid.xCoordinate + asteroid.width
                    && bullet.xCoordinateBullet + bullet.widthBullet > asteroid.xCoordinate;

                if (xOverlap && yOverlap) {
                    asteroidIterator.remove();
                    bulletIterator.remove();
                    score += 1;
                    break; // move to next bullet
                }
            }
        }
    }
}
