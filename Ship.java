public class Ship {
    int heightShip = 10;
    int widthShip = 10;
    int xCoordinateShip;
    int yCoordinateShip;
    int screenWidth;
    int screenHeight;

    
    public Ship(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // start centered at bottom
        this.xCoordinateShip = (screenWidth / 2) - (widthShip / 2);
        this.yCoordinateShip = screenHeight - heightShip - 50;
    }

    public void moveRight() {
        if (xCoordinateShip < screenWidth - widthShip) {
            xCoordinateShip += 10;
        }
    }

    public void moveLeft() {
        if (xCoordinateShip > 0) {
            xCoordinateShip -= 10;
        }
    }

    public int getShipX() {
        return xCoordinateShip;
    }

    public int getShipY() {
        return yCoordinateShip;
    }

    public int getWidthShip() {
        return widthShip;
    }

    public int getHeightShip() {
        return heightShip;
    }
}
