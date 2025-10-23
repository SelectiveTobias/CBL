import java.util.*;

/**
 * Represents bullets fired by the player's ship.
 * Each Bullet keeps track of its position and size. The class also
 * exposes a shared list for active bullets and convenience methods to
 * add/remove/clear bullets.
 */
public class Bullets {
    public static List<Bullets> bulletsList = new ArrayList<>();
    public static int BULLET_SPAWN_DELAY = 500;

    int widthBullet = 5;
    int heightBullet = 10;
    int xCoordinateBullet;
    int yCoordinateBullet;

    /**
     * Create a bullet positioned at the top-center of the ship.
     *
     * @param shipX     the ship's x-coordinate (top-left)
     * @param shipY     the ship's y-coordinate (top-left)
     * @param shipWidth the width of the ship in pixels
     */
    public Bullets(int shipX, int shipY, int shipWidth) {
        this.xCoordinateBullet = shipX + (shipWidth / 2) - (widthBullet / 2);
        this.yCoordinateBullet = shipY - heightBullet;
    }

    /**
     * Move the bullet upwards by a fixed amount (10 pixels).
     */
    public void moveUp() {
        yCoordinateBullet -= 10;
    }

    /**
     * Get the bullet's current x-coordinate.
     *
     * @return x-coordinate in pixels
     */
    public int getBulletX() {
        return xCoordinateBullet;
    }

    /**
     * Get the bullet's current y-coordinate.
     *
     * @return y-coordinate in pixels
     */
    public int getBulletY() {
        return yCoordinateBullet;
    }

    /**
     * Get the bullet's width in pixels.
     *
     * @return bullet width
     */
    public int getWidthBullet() {
        return widthBullet;
    }

    /**
     * Get the bullet's height in pixels.
     *
     * @return bullet height
     */
    public int getHeightBullet() {
        return heightBullet;
    }

    /**
     * Return the global list of active bullets.
     *
     * @return modifiable list of active bullets
     */
    public static List<Bullets> getBulletsList() {
        return bulletsList;
    }

    /**
     * Add a bullet to the global list.
     *
     * @param bullet the bullet to add
     */
    public static void addBullet(Bullets bullet) {
        bulletsList.add(bullet);
    }

    /**
     * Remove a bullet from the global list.
     *
     * @param bullet the bullet to remove
     */
    public static void removeBullet(Bullets bullet) {
        bulletsList.remove(bullet);
    }

    /**
     * Remove all bullets from the global list.
     */
    public static void clearBullets() {
        bulletsList.clear();
    }
}