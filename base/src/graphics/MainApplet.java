package graphics;

import processing.core.*;
import map.Map;
import resources.Resources;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 24.03.14
 * Time: 16:12
 */
public class MainApplet extends PApplet {
    Map map;
    Resources res;
    int width;
    int height;

    public MainApplet() {
        super();
        this.width = 512;
        this.height = 512;
        res = new Resources(this);
    }

    public void setup() {
        size(width, height);

        map = new Map(this, res, 16, 16, 10, 70);
    }

    public void draw() {
        map.redrawTiles(false);

        if (keyPressed) {
            map.getRandomTile().destroyTile();
        }
    }
}
