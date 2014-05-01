package entity;

import item.Item;
import processing.core.PApplet;
import resources.Resources;

public class Player {
    private DIRECTION lookDirection;
    private int bombsCurrentlyLaid;

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
        lookDirection = DIRECTION.DOWN;

        this.canvas = canvas;
        this.res = res;
    }

    public void hurt(double damage) {
        if (!hasShield) health -= damage;
        if(health <= 0) {
            health = 0;
            res.sound_die.play();
        }else{
            res.sound_hurt.play();
        }

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

    public boolean canLayBomb() {
        return (bombs - bombsCurrentlyLaid > 0);
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setHasShield(boolean hasShield) {
        this.hasShield = hasShield;
    }

    public void setLookDirection(DIRECTION lookDirection) {
        this.lookDirection = lookDirection;
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
        lookDirection = dir;
    }

    public void draw(float width, float height) {
        canvas.noSmooth();
        if(health <= 0) canvas.tint(255, 127);
        canvas.image(res.skull_creeper, (float) this.xPosition, (float) this.yPosition, width, height);
        canvas.smooth();

        canvas.noStroke();
        canvas.fill(canvas.color(0xFF, 0, 0));
        canvas.rect((float) this.xPosition, (float) this.yPosition - 4, width, 2);
        canvas.fill(canvas.color(0, 0xFF, 0));
        canvas.rect((float) this.xPosition, (float) this.yPosition - 4, (float) (health*width/100), 2);
    }

    public void layBomb() {
        if(canLayBomb()) {
            bombsCurrentlyLaid++;
        }
    }

    public void bombExploded() {
        bombsCurrentlyLaid--;
    }
}
