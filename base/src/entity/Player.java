package entity;

import item.Item;
import processing.core.PApplet;
import resources.Resources;

public class Player {
    public static enum DIRECTION {UP, DOWN, LEFT, RIGHT}

    private double xPosition;
    private double yPosition;

    private int bombs;
    private double swiftness;
    private double health;

    private boolean hasShield;

    private PApplet canvas;
    private Resources res;

    public Player(PApplet canvas, Resources res) {
        xPosition = -1D;
        yPosition = -1D;

        health = 100;
        swiftness = 1D;
        bombs = 1;
        hasShield = false;

        this.canvas = canvas;
        this.res = res;
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

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void move(DIRECTION dir) {
        switch (dir) {
            case DOWN:
                this.yPosition += this.swiftness;
                break;
            case UP:
                this.yPosition -= this.swiftness;
                break;
            case RIGHT:
                this.xPosition += this.swiftness;
                break;
            case LEFT:
                this.xPosition -= this.swiftness;
        }
    }

    public void draw(float width, float height) {
        canvas.noSmooth();
        canvas.image(res.skull_creeper, (float) this.xPosition, (float) this.yPosition, width, height);
        canvas.smooth();
    }
}
