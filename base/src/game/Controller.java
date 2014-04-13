package game;

import entity.Bomb;
import entity.Player;
import graphics.Effects;
import graphics.Explosion;
import map.Map;
import map.Tile;
import processing.core.PApplet;
import resources.Resources;

import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 26.03.14
 * Time: 15:35
 */
public class Controller {
    PApplet canvas;
    Map map;
    Resources res;
    Effects effects;
    Bomb bomb;
    Player player;

    public Controller(PApplet canvas) {
        this.canvas = canvas;
        res = new Resources(canvas);
        effects = new Effects(canvas, res);
    }

    public void setup() {
        map = new Map(canvas, res, 16, 16, 0, 50);
        bomb = new Bomb(canvas, res, 64, 64, map.getBlockWidth(), map.getBlockHeight());
        player = new Player(canvas, res);
        player.setxPosition(map.getBlockWidth());
        player.setyPosition(map.getBlockHeight());
    }

    public void draw() {
        map.redrawTiles(true);
        player.draw(map.getBlockWidth(), map.getBlockHeight());
        effects.drawEffects();


        if (canvas.keyPressed) {
            keyEvent();
        }

        if (canvas.mousePressed) {
            int x = (int) Math.floor(canvas.mouseX / map.getBlockWidth());
            int y = (int) Math.floor(canvas.mouseY / map.getBlockHeight());

            Tile t = map.getTile(x, y);
            effects.addEffect(new Explosion(map.getBlockWidth(), map.getBlockHeight()), (float) (t.getX() + 0.5) * map.getBlockWidth(), (float) (t.getY() + 0.5) * map.getBlockHeight());
            t.destroyTile();
        }
    }

    public void keyEvent() {
        int xMin = (int) Math.floor((player.getXPosition() + (0.25 * map.getBlockWidth())) / map.getBlockWidth());
        int yMin = (int) Math.floor((player.getYPosition() + (0.25 * map.getBlockHeight())) / map.getBlockHeight());

        int xMax = (int) Math.floor((player.getXPosition() + (0.75 * map.getBlockWidth())) / map.getBlockWidth());
        int yMax = (int) Math.floor((player.getYPosition() + (0.75 * map.getBlockWidth())) / map.getBlockHeight());

        System.out.println("xMin: " + xMin + "; yMin: " + yMin + "; xMax: " + xMax + "; yMax: " + yMax);

        switch (canvas.keyCode) {
            case KeyEvent.VK_UP:
                if (map.getTile(xMin, yMin).isPassable()) {
                    player.move(Player.DIRECTION.UP);
                } else {
                    player.setyPosition((yMax) * map.getBlockHeight());
                }
                break;
            case KeyEvent.VK_DOWN:
                if (map.getTile(xMax, yMax).isPassable()) {
                    player.move(Player.DIRECTION.DOWN);
                } else {
                    player.setyPosition((yMin) * map.getBlockHeight());
                }
                break;
            case KeyEvent.VK_LEFT:
                if (map.getTile(xMin, yMin).isPassable()) {
                    player.move(Player.DIRECTION.LEFT);
                } else {
                    player.setxPosition((xMax) * map.getBlockHeight());
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (map.getTile(xMax, yMax).isPassable()) {
                    player.move(Player.DIRECTION.RIGHT);
                } else {
                    player.setxPosition((xMin) * map.getBlockHeight());
                }
                break;
            default:
                player.setxPosition(map.getBlockWidth());
                player.setyPosition(map.getBlockHeight());
                break;
        }
    }
}
