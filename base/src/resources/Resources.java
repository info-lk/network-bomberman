package resources;

import processing.core.PImage;
import processing.core.PApplet;
import kuusisto.tinysound.*;

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
    public PImage bg_blurry;
    public Sound sound_explode;
    public Sound sound_layBomb;
    public Sound sound_hurt;
    public Sound sound_die;
    public Music music_lobby;


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
        this.bg_blurry = canvas.loadImage("resources/bg_blurry.png");

        TinySound.init();
        this.sound_explode = TinySound.loadSound("resources/explode1.ogg");
        this.sound_layBomb = TinySound.loadSound("resources/fizz.ogg");
        this.sound_hurt = TinySound.loadSound("resources/hit.ogg");
        this.sound_die = TinySound.loadSound("resources/death.ogg");
        this.music_lobby = TinySound.loadMusic("resources/lobby_music.ogg");
    }
}
