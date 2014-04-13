package graphics;

import processing.core.PApplet;
import resources.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 26.03.14
 * Time: 15:57
 */
public interface Drawable {
    public void setup(PApplet canvas, Resources res, float x, float y);

    public void draw();

    public boolean isRemovable();
}
