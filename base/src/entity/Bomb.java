package entity;

import graphics.Drawable;
import graphics.Effects;
import graphics.Explosion;
import map.Map;
import map.Tile;
import processing.core.PApplet;
import resources.Resources;

public class Bomb implements Drawable {
    private PApplet canvas;
    private Resources res;
    private Map map;
    private Player player;
    private Effects effects;
    private int xMin, yMin;
    private float x, y = 0;
    private float tileWidth, tileHeight = 0;
    private int step;

    public Bomb(Player player, Map map, Effects effects, float tileWidth, float tileHeight, int x, int y) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.player = player;
        this.map = map;
        this.effects = effects;
        this.xMin = x;
        this.yMin = y;
    }

    @Override
    public void setup(PApplet canvas, Resources res, float x, float y) {
        this.canvas = canvas;
        this.res = res;
        this.x = x;
        this.y = y;
    }

    public void draw() {
        step++;
        int time = 3;

        if(step < (time * 60)){
            float tmpWidthPulse = (float) step / (time * 60) * tileWidth; //(amplitude * Math.sin((double) step / 10));
            float tmpHeightPulse = (float) step / (time * 60) * tileHeight; //(amplitude * Math.sin((double) step / 10));

            canvas.noSmooth();
            canvas.image(res.egg, x - tmpWidthPulse / 2 + tileWidth / 2, y - tmpHeightPulse / 2 + tileHeight / 2, tmpWidthPulse, tmpHeightPulse);
            canvas.smooth();
        }else if(step == (time * 60)){
            Tile[] t = new Tile[5];
            t[0] = map.getTile(xMin, yMin); //Eigentlich nicht nötig?
            t[1] = map.getTile(xMin, yMin - 1);
            t[2] = map.getTile(xMin, yMin + 1);
            t[3] = map.getTile(xMin - 1, yMin);
            t[4] = map.getTile(xMin + 1, yMin);

            effects.addEffect(new Explosion(map.getBlockWidth(), map.getBlockHeight()), (float) (xMin + 0.5) * map.getBlockWidth(), (float) (yMin + 0.5) * map.getBlockHeight());

            for(Tile tile : t) {
                tile.destroyTile();
                if(tile.hasPlayer()) {
                    tile.getPlayer().hurt(1);
                }
            }

            player.bombExploded();

            System.out.println("Bomb exploded at (" + xMin + ", " + yMin + ")");
        }
    }

    @Override
    public boolean isRemovable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setXPos(float XPos) {
        this.x = XPos;
    }

    public void setYPos(float YPos) {
        this.y = YPos;
    }
}