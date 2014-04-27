package info;

import entity.Player;
import processing.core.PApplet;
import processing.core.PConstants;

public class DeathScreen {
    private PApplet canvas;
    private float width;
    private float height;
    private int step;

    public DeathScreen(PApplet canvas, float windowWidth, float windowHeight) {
        this.canvas = canvas;
        this.width = windowWidth;
        this.height = windowHeight;
    }

    public void draw(){
        if(step < 100) step++;

        // Hintergrund
        canvas.fill(canvas.color(0xFF, 0, 0, step));
        canvas.noStroke();
        canvas.rect(0, 0, width, height);

        // FPS
        canvas.fill(255);
        canvas.textSize(64);
        canvas.textAlign(PConstants.CENTER, PConstants.CENTER);
        canvas.text("You died.", 0, 0, width, height);
    }
}