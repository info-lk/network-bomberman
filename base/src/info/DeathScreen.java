package info;

import processing.core.PApplet;
import processing.core.PConstants;
import resources.Resources;

import java.util.Random;

/**
 * Diese Klasse stellt den Text dar, der angezeigt wird, wenn der lokale Spieler stirbt.
 */
public class DeathScreen {
    private PApplet canvas; // Die Zeichenfläche
    private float width; // Die Breite der Zeichenfläche
    private float height; // Die Höhe der Zeichenfläche
    private int step; // Die Anzahl der bereits angezeigten Frames, verwendet für die Animation
    private String deathText; // Der angezeigte Text

    public DeathScreen(PApplet canvas, Resources res) {
        this.canvas = canvas;
        this.width = canvas.width;
        this.height = canvas.height;
        Random r = new Random();
        this.deathText = res.deathMessages[r.nextInt(res.deathMessages.length)];
    }

    public void draw() {
        if (step < 100) step++;

        // Hintergrund
        canvas.fill(canvas.color(0xFF, 0, 0, step));
        canvas.noStroke();
        canvas.rect(0, 0, width, height);

        // FPS
        canvas.fill(255);
        canvas.textSize(64);
        canvas.textAlign(PConstants.CENTER, PConstants.CENTER);

        // Death Text
        canvas.text(deathText, 0, 0, width, height);
    }
}