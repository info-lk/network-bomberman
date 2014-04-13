package entity;

import processing.core.PApplet;
import resources.Resources;

public class Bomb {
    private PApplet canvas;
    private Resources res;
    private float x, y = 0;
    private float tileWidth, tileHeight = 0;
    private int step;

    public Bomb(PApplet canvas, Resources res, float x, float y, float tileWidth, float tileHeight) {
        this.canvas = canvas;
        this.res = res;
        this.x = x;
        this.y = y;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void draw() {
        step++;
        int time = 3;
        float tmpWidthPulse = (float) step / (time * 60) * tileWidth; //(amplitude * Math.sin((double) step / 10));
        float tmpHeightPulse = (float) step / (time * 60) * tileHeight; //(amplitude * Math.sin((double) step / 10));

        canvas.noSmooth();
        canvas.image(res.egg, x - tmpWidthPulse / 2 + tileWidth / 2, y - tmpHeightPulse / 2 + tileHeight / 2, tmpWidthPulse, tmpHeightPulse);
        canvas.smooth();
    }
}