package game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import entity.Bomb;
import entity.Player;
import graphics.Effects;
import graphics.Explosion;
import map.Map;
import map.Tile;
import network.ClientVariables;
import network.ServerVariables;
import processing.core.PApplet;
import resources.Resources;

import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 26.03.14
 * Time: 15:35
 */
public class Controller {
    Client client;
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
        client = new Client();
    }

    public void setup() {
        client.getKryo().register(ServerVariables.class);
        client.getKryo().register(ClientVariables.class);
        map = new Map(canvas, res, 16, 16, 0, 50); //Nur zum testen nötig

        try {
            client.connect(5000,"127.0.0.1",22222,11111);

            client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if(object instanceof ServerVariables) {
                        ServerVariables sV = (ServerVariables) object;
                        if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.MAP)) {
                            map = sV.map;
                        }
                        else if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.PLAYER)) {
                            Player enemy = new Player(canvas,res);
                            enemy.setxPosition(sV.player.xPosition);
                            enemy.setyPosition(sV.player.yPosition);
                            enemy.setHasShield(sV.player.hasShield);
                            enemy.setLookDirection(sV.player.lookDirection);
                            enemy.draw(map.getBlockWidth(),map.getBlockHeight());
                        }
                        else if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.BOMB)) {
                            bomb.setXPos((float) sV.bomb.layBombAtX * map.getBlockWidth());
                            bomb.setYPos((float) sV.bomb.layBombAtY * map.getBlockHeight());
                            bomb.draw();
                        }
                        else if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.BOMB_PLAYER)) {
                            Player enemy = new Player(canvas,res);
                            enemy.setxPosition(sV.player.xPosition);
                            enemy.setyPosition(sV.player.yPosition);
                            enemy.setHasShield(sV.player.hasShield);
                            enemy.setLookDirection(sV.player.lookDirection);
                            enemy.draw(map.getBlockWidth(), map.getBlockHeight());

                            bomb.setXPos((float) sV.bomb.layBombAtX * map.getBlockWidth());
                            bomb.setYPos((float) sV.bomb.layBombAtY * map.getBlockHeight());
                            bomb.draw();
                        }
                        else if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.COMMAND)) {
                            switch(sV.command) {
                                case -1:
                                    break;
                                case 0: //pause
                                    canvas.pause();
                                    System.out.println("PAUSE");
                                    break;
                                case 1:
                                    System.out.println("You were kicked from game");
                                    client.close();
                                    System.exit(1);
                                    break;
                                case 2:
                                    canvas.resume();
                                    break;
                                default :
                                    System.out.println("Unknown server command");
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        //int xMin = (int) Math.ceil(player.getXPosition());
        //int xMax = (int) Math.ceil(player.getXPosition() + 0.8);
        int xMin = (int) Math.floor((player.getXPosition() + (0.25 * map.getBlockWidth())) / map.getBlockWidth());
        int yMin = (int) Math.floor((player.getYPosition() + (0.25 * map.getBlockHeight())) / map.getBlockHeight());

        //int yMin = (int) Math.ceil(player.getYPosition());
        //int yMax = (int) Math.ceil(player.getYPosition() + 0.8);
        int xMax = (int) Math.floor((player.getXPosition() + (0.75 * map.getBlockWidth())) / map.getBlockWidth());
        int yMax = (int) Math.floor((player.getYPosition() + (0.75 * map.getBlockWidth())) / map.getBlockHeight());

        canvas.stroke(255);
        canvas.fill(canvas.color(255, 0, 0));
        canvas.rect(xMin, yMin, xMax-xMin, yMax-yMin);

        switch (canvas.keyCode) {
            case KeyEvent.VK_UP:
                if (map.getTile(xMin, yMin).isPassable() && map.getTile(xMax,yMin).isPassable()) {
                    player.move(Player.DIRECTION.UP);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (map.getTile(xMin, yMax).isPassable() && map.getTile(xMax,yMax).isPassable()) {
                    player.move(Player.DIRECTION.DOWN);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (map.getTile(xMin, yMin).isPassable() && map.getTile(xMin,yMax).isPassable()) {
                    player.move(Player.DIRECTION.LEFT);
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (map.getTile(xMax, yMin).isPassable() && map.getTile(xMax,yMax).isPassable()) {
                    player.move(Player.DIRECTION.RIGHT);
                }
                break;

            case KeyEvent.VK_ALT:
                if(player.canLayBomb()) {
                    player.layBomb();
                    effects.addEffect(new Bomb(player, map, effects, map.getBlockWidth(), map.getBlockHeight(), xMin, yMin), xMin * map.getBlockWidth(), yMin * map.getBlockHeight());
                }
                break;

            case KeyEvent.VK_ESCAPE:
                //openMenu();
                break;
        }
    }
}
