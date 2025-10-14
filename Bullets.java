import java.util.*;

public class Bullets {
    private static List<Bullets> bulletsList = new ArrayList<>();
    public static final int BULLET_SPAWN_DELAY = 500;

    int widthBullet = 5;
    int heightBullet = 10;
    int xCoordinateBullet;
    int yCoordinateBullet;

    public Bullets(int shipX, int shipY, int shipWidth) {
        this.xCoordinateBullet = shipX + (shipWidth / 2) - (widthBullet / 2);
        this.yCoordinateBullet = shipY - heightBullet;
    }

    public void moveUp() {
        yCoordinateBullet -= 10;
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

    public static List<Bullets> getBulletsList() {
        return bulletsList;
    }

    public static void addBullet(Bullets bullet) {
        bulletsList.add(bullet);
    }

    public static void removeBullet(Bullets bullet) {
        bulletsList.remove(bullet);
    }
}