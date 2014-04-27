package info;

import entity.Player;
import processing.core.PApplet;
import processing.core.PConstants;

public class InformationBar {
    private PApplet canvas;
    private Player player;
    private float width;

    public InformationBar(PApplet canvas, Player player, float windowWidth) {
        this.canvas = canvas;
        this.player = player;
        this.width = windowWidth;
    }

    public void draw(){
        // Hintergrund
        canvas.fill(0);
        canvas.noStroke();
        canvas.rect(0, 0, width, 32);

        // Life bar Hintergrund
        canvas.fill(canvas.color(0xFF, 0, 0));
        canvas.rect(12, 12, 200, 8);

        // Life bar Vordergrund
        canvas.fill(canvas.color(0, 0xFF, 0));
        canvas.rect(12, 12, (float) player.getHealth() * 2, 8);

        // FPS
        canvas.stroke(255);
        canvas.fill(255);
        canvas.textSize(16);
        canvas.textAlign(PConstants.RIGHT, PConstants.CENTER);
        canvas.text(Math.round(canvas.frameRate) + " FPS", 0, 0, width, 32);
    }
}