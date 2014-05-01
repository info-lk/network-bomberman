package game;

import processing.core.*;

public class MainApplet extends PApplet {
    private Controller cont;
    private int width;
    private int height;

    public MainApplet() {
        super();
        this.width = 512;
        this.height = 512;
        cont = new Controller(this);
    }

    @Override
    public boolean sketchFullScreen() {
        return true;
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

    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "game.MainApplet" });
    }
}
