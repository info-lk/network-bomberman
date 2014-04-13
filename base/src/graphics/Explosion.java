package graphics;

import processing.core.PApplet;
import resources.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 26.03.14
 * Time: 16:15
 */
public class Explosion implements Drawable {
    private int progress = 0;
    private PApplet canvas;
    private Resources res;
    private float x;
    private float y;
    private float width;
    private float height;

    public Explosion(float blockWidth, float blockHeight) {
        this.width = blockWidth;
        this.height = blockHeight;
    }

    @Override
    public void setup(PApplet canvas, Resources res, float x, float y) {
        this.canvas = canvas;
        this.res = res;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        progress += 8;

        if (progress >= 50 && progress <= 200) {
            canvas.smooth();
            canvas.blend(res.explosion, 0, 0, 256, 256, (int) x - (int) (width * 2), (int) y - (int) (height * 2), (int) width * 4, (int) height * 4, PApplet.DODGE);
            canvas.noSmooth();
        }
    }

    @Override
    public boolean isRemovable() {
        return progress <= 200;
    }
}
