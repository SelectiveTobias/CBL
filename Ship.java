

public class Ship {
    int heightShip = 10;
    int widthShip = 10;
    int xCoordinateShip = 540;
    int yCoordinateShip = ; // starts at the bottom of the screen

    public void moveRight() {
        if (xCoordinateShip < 1920 - widthShip) {
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
