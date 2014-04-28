package map;

import processing.core.PApplet;
import resources.Resources;

import java.util.Random;

public class Map {

    private int width;
    private int height;
    private float blockWidth;
    private float blockHeight;
    private Tile[][] tile;

    private double wallChance = 10;
    private double destructableWallChance = 15;
    private PApplet canvas;
    private Resources res;

    private Random r;

    public Map(){

    }

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tile = new Tile[width][height];

        r = new Random();

        initialize();
    }

    public Map(PApplet canvas, Resources res, network.Map transmittedMap){
        System.out.println("Transmitted map:");
        System.out.println(transmittedMap);

        this.canvas = canvas;
        this.res = res;
        this.width = transmittedMap.width;
        this.height = transmittedMap.height;

        this.blockWidth = canvas.width / width;
        this.blockHeight = canvas.width / height;

        tile = new Tile[width][height];
        String[] tileRows = transmittedMap.tiles.split("\n");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char currentTile = tileRows[y].charAt(x);

                switch(currentTile){
                    case '0':
                        tile[x][y] = new Tile(x, y, false, false);
                        break;
                    case '1':
                        tile[x][y] = new Tile(x, y, true, false);
                        break;
                    case '2':
                        tile[x][y] = new Tile(x, y, false, true);
                        break;
                }
            }
        }
    }

    public Map(PApplet canvas, Resources res, int width, int height, double wallChance, double destructableWallChance) {
        this.canvas = canvas;
        this.res = res;
        this.width = width;
        this.height = height;

        this.blockWidth = canvas.width / width;
        this.blockHeight = canvas.width / height;

        tile = new Tile[width][height];

        this.destructableWallChance = destructableWallChance;
        this.wallChance = wallChance;

        r = new Random();

        initialize();
    }

    private void initialize() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) { //Wall
                    tile[x][y] = new Tile(x, y, false, false);
                } else {
                    tile[x][y] = generateRandomTile(x, y);
                }
            }
        }
    }

    public void redrawTiles(boolean force) {
        canvas.noSmooth();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tile[x][y].draw(canvas, res, x * blockWidth, y * blockHeight, blockWidth, blockHeight, force);
            }
        }
        canvas.smooth();
    }

    private Tile generateRandomTile(int x, int y) {
        int random = r.nextInt(100);
        if (random <= wallChance) {
            return new Tile(x, y, false, false); // Wall
        } else if (random <= destructableWallChance) {
            return new Tile(x, y, true, false); // Destructable Wall
        } else {
            return new Tile(x, y, false, true); // Ground
        }
    }

    public Tile getRandomTile() {
        int x = r.nextInt(width - 2) + 1;
        int y = r.nextInt(height - 2) + 1;

        return tile[x][y];
    }

    public Tile getTile(int x, int y) {
        return tile[x][y];
    }

    public void resetPlayerBooleans(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).setPlayer(null);
            }
        }
    }

    public float getBlockWidth() {
        return blockWidth;
    }

    public float getBlockHeight() {
        return blockHeight;
    }
}
