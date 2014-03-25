package entity;

import item.Item;

public class Player {

    private double xPosition;
    private double yPosition;

    private int bombs;
    private double swiftness;
    private double health;

    private boolean hasShield;


    public Player() {
        xPosition = -1D;
        yPosition = -1D;

        health = 100;
        swiftness = 1D;
        bombs = 1;
        hasShield = false;
    }

    public void hurt(double damage) {
        if (!hasShield) health -= health;
    }

    public void collectItem(Item item) {
        item.onCollect(this);
    }

    public void addBombs(int i) {
        bombs += i;
    }

    public int getBombCount() {
        return bombs;
    }

    public double getSwiftness() {
        return swiftness;
    }

    public double getHealth() {
        return health;
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

}
