package map;

import entity.Player;
import processing.core.PApplet;
import processing.core.PImage;
import resources.Resources;

public class Tile {

    private boolean destructable;
    private boolean passable;
    private boolean hasPlayer;
    private boolean needsRedraw = true;
    private boolean isDestructing = false;
    private int currentDestruction = 0;
    private Player player;
    private int x, y = 0;

    public Tile(int x, int y, boolean destructable, boolean passable) {
        this.x = x;
        this.y = y;
        this.destructable = destructable;
        this.passable = passable;
        hasPlayer = false;
        player = null;
    }

    public void destroyTile() {
        if (destructable) {
            isDestructing = true;
            currentDestruction = 0;
            needsRedraw = true;
        }
    }

    public boolean isWall() {
        return !destructable & !passable;
    }

    public boolean isDestructableWall() {
        return destructable & !passable;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public String toString() {
        return "[" + destructable + "][" + passable + "][" + hasPlayer + "]";
    }

    public void draw(PApplet canvas, Resources res, float x, float y, float width, float height, boolean force) {
        if (needsRedraw || force) {
            PImage img = res.grass;
            if (isDestructableWall()) {
                img = res.cobblestone;
            } else if (isWall()) {
                img = res.bedrock;
            }


            canvas.image(img, x, y, width, height);

            if (isDestructing) {
                currentDestruction += 8;

                if (currentDestruction <= 200) {
                    PImage color = canvas.createImage(1, 1, PApplet.RGB);
                    color.loadPixels();
                    color.pixels[0] = canvas.color(currentDestruction, currentDestruction / 2, 0);
                    canvas.blend(color, 0, 0, 1, 1, (int) x, (int) y, (int) width, (int) height, 4096);
                } else {
                    isDestructing = false;
                    destructable = false;
                    passable = true;
                }
            } else {
                needsRedraw = false;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
