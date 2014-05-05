package resources;

import processing.core.PImage;
import processing.core.PApplet;
import kuusisto.tinysound.*;
import tools.FileArrayReader;

/**
 * Diese Klasse enthält alle benötigten Ressourcen für das Spiel.
 */
public class Resources {
    public PImage bedrock;
    public PImage dirt;
    public PImage grass;
    public PImage item_bg;
    public PImage cobblestone;
    public PImage explosion;
    public PImage egg;
    public PImage beam_horiz;
    public PImage beam_vert;
    public PImage SpeedItem;
    public PImage eggItem;
    public PImage superbombItem;
    public PImage waterbombItem;
    public PImage shieldItem;
    public PImage skull_creeper;
    public PImage bg_blurry;

    public Sound sound_explode;
    public Sound sound_layBomb;
    public Sound sound_hurt;
    public Sound sound_die;
    public Sound item_collect;
    public Music music_lobby;

    public String[] deathMessages;

    public Resources(PApplet canvas) {
        this.bedrock = canvas.loadImage("resources/bedrock.png");
        this.dirt = canvas.loadImage("resources/dirt.png");
        this.grass = canvas.loadImage("resources/grass.png");
        this.item_bg = canvas.loadImage("resources/item_bg.png");
        this.cobblestone = canvas.loadImage("resources/cobblestone.png");
        this.explosion = canvas.loadImage("resources/explosion.png");
        this.beam_horiz = canvas.loadImage("resources/strahl.horiz.png");
        this.beam_vert = canvas.loadImage("resources/strahl.vert.png");
        this.egg = canvas.loadImage("resources/egg.png");
        this.skull_creeper = canvas.loadImage("resources/skull_creeper.png");
        this.bg_blurry = canvas.loadImage("resources/bg_blurry.png");
        this.eggItem = canvas.loadImage("resources/egg.png");
        this.SpeedItem = canvas.loadImage("resources/gold_boots.png");
        this.superbombItem = canvas.loadImage("resources/apple_golden.png");
        this.waterbombItem = canvas.loadImage("resources/ender_pearl.png");
        this.shieldItem = canvas.loadImage("resources/leather_chestplate.png");

        TinySound.init();
        this.sound_explode = TinySound.loadSound("resources/explode1.ogg");
        this.sound_layBomb = TinySound.loadSound("resources/fizz.ogg");
        this.sound_hurt = TinySound.loadSound("resources/hit.ogg");
        this.sound_die = TinySound.loadSound("resources/death.ogg");
        this.music_lobby = TinySound.loadMusic("resources/lobby_music.ogg");
        this.item_collect = TinySound.loadSound("resources/eat.ogg");

        this.deathMessages = new String[]{
                "You just died, bitch.",
                "You is dead.",
                "Death is just the beginning.",
                "Dead.",
                "You should take Revenge.",
                "One bomb too much.",
                "They killed you.",
                "You can't do shit anymore."
        };
    }
}
