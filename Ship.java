/**
 * Represents the player's ship in the game.
 * The ship has a fixed size and keeps track of its position on the screen.
 * Movement operations update the x-coordinate by a fixed amount and are
 * clamped to remain inside the horizontal bounds of the screen.
 */
public class Ship {
    int heightShip = 10;
    int widthShip = 10;
    int xCoordinateShip;
    int yCoordinateShip;
    int screenWidth;
    int screenHeight;

    /**
     * Create a new Ship positioned near the bottom center of the screen.
     *
     * @param screenWidth  the width of the game screen in pixels
     * @param screenHeight the height of the game screen in pixels
     */
    public Ship(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.xCoordinateShip = (screenWidth / 2) - (widthShip / 2);
        this.yCoordinateShip = screenHeight - heightShip - 50;
    }

    /**
     * Move the ship to the right by 10 pixels, stopping at the screen's right edge.
     */
    public void moveRight() {
        if (xCoordinateShip < screenWidth - widthShip) {
            xCoordinateShip += 10;
        }
    }

    /**
     * Move the ship to the left by 10 pixels, stopping at the screen's left edge.
     */
    public void moveLeft() {
        if (xCoordinateShip > 0) {
            xCoordinateShip -= 10;
        }
    }

    /**
     * Get the current x-coordinate of the ship.
     *
     * @return the x-coordinate in pixels of the ship's top-left corner
     */
    public int getShipX() {
        return xCoordinateShip;
    }

    /**
     * Get the current y-coordinate of the ship.
     *
     * @return the y-coordinate in pixels of the ship's top-left corner
     */
    public int getShipY() {
        return yCoordinateShip;
    }

    /**
     * Get the ship's width in pixels.
     *
     * @return the width of the ship
     */
    public int getWidthShip() {
        return widthShip;
    }

    /**
     * Get the ship's height in pixels.
     *
     * @return the height of the ship
     */
    public int getHeightShip() {
        return heightShip;
    }
}
