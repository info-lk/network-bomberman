package item;

import entity.Player;
import processing.core.PApplet;
import processing.core.PImage;
import resources.Resources;

public abstract class Item {
    public abstract String getName();

    public abstract byte getId();

    public abstract void onCollect(Player player);

    public abstract PImage getImage(Resources res);

    public void draw(PApplet canvas, Resources res, float blockWidth, float blockHeight, float x, float y){
        canvas.noSmooth();
        canvas.image(res.item_bg, x, y, blockWidth, blockHeight);
        canvas.image(getImage(res), x + 1, y + 1, blockWidth - 2, blockHeight - 2);
        canvas.smooth();
    }
}
