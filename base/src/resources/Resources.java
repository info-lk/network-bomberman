package resources;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 24.03.14
 * Time: 19:53
 */
public class Resources {
    public PImage bedrock;
    public PImage dirt;
    public PImage grass;
    public PImage cobblestone;
    public PImage explosion;
    public PImage egg;
    public PImage beam_horiz;
    public PImage beam_vert;
    public PImage skull_creeper;


    public Resources(PApplet canvas) {
        this.bedrock = canvas.loadImage("resources/bedrock.png");
        this.dirt = canvas.loadImage("resources/dirt.png");
        this.grass = canvas.loadImage("resources/grass.png");
        this.cobblestone = canvas.loadImage("resources/cobblestone.png");
        this.explosion = canvas.loadImage("resources/explosion.png");
        this.beam_horiz = canvas.loadImage("resources/strahl.horiz.png");
        this.beam_vert = canvas.loadImage("resources/strahl.vert.png");
        this.egg = canvas.loadImage("resources/egg.png");
        this.skull_creeper = canvas.loadImage("resources/skull_creeper.png");
    }
}
