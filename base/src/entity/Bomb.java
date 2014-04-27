package entity;

import graphics.Drawable;
import graphics.Effects;
import graphics.Explosion;
import graphics.ExplosionBeam;
import map.Map;
import map.Tile;
import processing.core.PApplet;
import resources.Resources;
import java.util.ArrayList;

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
    private int strength;
    private int range = 10;

    public Bomb(Player player, Map map, Effects effects, float tileWidth, float tileHeight, int x, int y, int range, int strength) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.player = player;
        this.map = map;
        this.effects = effects;
        this.xMin = x;
        this.yMin = y;
        this.range = range;
        this.strength = strength;
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
            ArrayList<Tile> destroyedTiles = new ArrayList<Tile>();
            int minX = 1, maxX = 1, minY = 1, maxY = 1;

            for(int j = 0; j < 4; j++){
                for(int i = 0; i < range; i++){
                    Tile tile = null;
                    switch(j){
                        case 0:
                            maxX = xMin + i;
                            tile = map.getTile(maxX, yMin);
                            break;
                        case 1:
                            minX = xMin - i;
                            tile = map.getTile(minX, yMin);
                            break;
                        case 2:
                            maxY = yMin + i;
                            tile = map.getTile(xMin, maxY);
                            break;
                        case 3:
                            minY = yMin - i;
                            tile = map.getTile(xMin, minY);
                            break;
                    }

                    if(tile.hasPlayer()) {
                        tile.getPlayer().hurt(strength);
                    }

                    if(!tile.isPassable()){
                        if(tile.isDestructableWall()) destroyedTiles.add(tile);
                        break;
                    }
                }
            }

            effects.addEffect(new Explosion(map.getBlockWidth(), map.getBlockHeight()), (float) (xMin + 0.5) * map.getBlockWidth(), (float) (yMin + 0.5) * map.getBlockHeight());
            effects.addEffect(new ExplosionBeam((maxX-minX-1) * map.getBlockWidth(), map.getBlockHeight(), ExplosionBeam.Orientation.HORIZONTAL), (minX+1) * map.getBlockWidth(), yMin * map.getBlockHeight());
            effects.addEffect(new ExplosionBeam(map.getBlockWidth(), (maxY-minY-1) * map.getBlockHeight(), ExplosionBeam.Orientation.VERTICAL), xMin * map.getBlockWidth(), (minY+1) * map.getBlockHeight());

            for(Tile tile : destroyedTiles) {
                tile.destroyTile();
            }

            player.bombExploded();
        }
    }

    public void setXPos(float XPos) {
        this.x = XPos;
    }

    public void setYPos(float YPos) {
        this.y = YPos;
    }
}