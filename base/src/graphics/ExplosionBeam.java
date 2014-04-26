package graphics;

import processing.core.PApplet;
import resources.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 26.03.14
 * Time: 16:15
 */
public class ExplosionBeam implements Drawable {
    private int progress = 0;
    private PApplet canvas;
    private Resources res;
    private float x;
    private float y;
    private float width;
    private float height;
    public enum Orientation {HORIZONTAL, VERTICAL}
    private Orientation orientation;

    public ExplosionBeam(float width, float height, Orientation orientation) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
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
            if(this.orientation == Orientation.VERTICAL){
                canvas.blend(res.beam_vert, 0, 0, 16, 1, (int) x, (int) y, (int) width, (int) height, PApplet.DODGE);
            }else{
                canvas.blend(res.beam_horiz, 0, 0, 1, 16, (int) x, (int) y, (int) width, (int) height, PApplet.DODGE);
            }
            canvas.noSmooth();
        }
    }
}
