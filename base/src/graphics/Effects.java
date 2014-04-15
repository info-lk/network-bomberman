package graphics;

import processing.core.PApplet;
import resources.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 26.03.14
 * Time: 15:52
 */
public class Effects {
    PApplet canvas;
    Resources res;
    ArrayList<Drawable> effects = new ArrayList<Drawable>();

    public Effects(PApplet canvas, Resources res) {
        this.canvas = canvas;
        this.res = res;
    }

    public void addEffect(Drawable effect, float x, float y) {
        effects.add(effect);
        effect.setup(canvas, res, x, y);
    }

    public void drawEffects() {
        for (Drawable effect : effects) {
            effect.draw();
        }
    }
}
