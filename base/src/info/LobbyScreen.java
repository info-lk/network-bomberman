package info;

import network.LobbyVariables;
import processing.core.PApplet;
import processing.core.PConstants;
import resources.Resources;

public class LobbyScreen {
    private PApplet canvas;
    private Resources res;
    private float width;
    private float height;
    private boolean isVisible = false;
    private String text;

    public LobbyScreen(PApplet canvas, Resources res, float windowWidth, float windowHeight) {
        this.canvas = canvas;
        this.res = res;
        this.width = windowWidth;
        this.height = windowHeight;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;

        if(isVisible){
            res.music_lobby.play(true);
        }else{
            res.music_lobby.stop();
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void draw(){
        if(isVisible){
            canvas.smooth();
            canvas.image(res.bg_blurry, 0, 0, canvas.width, canvas.height);

            // Hintergrund
            canvas.fill(canvas.color(0, 0, 0xCC, 150));
            canvas.noStroke();
            canvas.rect(0, 0, width, height);

            // Lobby Text
            canvas.fill(255);
            canvas.textSize(32);
            canvas.textAlign(PConstants.CENTER, PConstants.CENTER);
            canvas.text(text, 0, 0, width, height);
        }
    }
}