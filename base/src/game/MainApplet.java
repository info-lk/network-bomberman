package game;

import processing.core.*;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 24.03.14
 * Time: 16:12
 */
public class MainApplet extends PApplet {
    Controller cont;
    int width;
    int height;

    public MainApplet() {
        super();
        this.width = 512;
        this.height = 512;
        cont = new Controller(this);
    }

    public void setup() {
        size(width, height);
        cont.setup();
    }

    public void draw() {
        cont.draw();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
