public class Bullets {
    int widthBullet = 5;
    int heightBullet = 10;
    int xCoordinateBullet;
    int yCoordinateBullet;

    public Bullets(int shipX, int shipY, int shipWidth) {
        this.xCoordinateBullet = shipX + (shipWidth / 2) - (widthBullet / 2);
        this.yCoordinateBullet = shipY - heightBullet; // starts just above the ship
    }

    public void moveUp() {
        yCoordinateBullet -= 10; // move the bullet up by 10 units
    }

    public int getBulletX() {
        return xCoordinateBullet;
    }

    public int getBulletY() {
        return yCoordinateBullet;
    }

    public int getWidthBullet() {
        return widthBullet;
    }

    public int getHeightBullet() {
        return heightBullet;
    }
}

