package game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import entity.Bomb;
import entity.Player;
import graphics.Effects;
import graphics.Explosion;
import info.DeathScreen;
import info.InformationBar;
import info.LobbyScreen;
import map.Map;
import map.Tile;
import network.ClientVariables;
import network.LobbyVariables;
import network.ServerVariables;
import processing.core.PApplet;
import resources.Resources;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

class Controller {
    private Client client; // Der Client
    private PApplet canvas;
    private Map map;
    private Resources res;
    private Effects effects;
    private Player player;
    private InformationBar infoBar;
    private DeathScreen deathScreen;
    private LobbyScreen lobbyScreen;
    private ArrayList<Player> enemies = new ArrayList<Player>();
    private int step;

    public Controller(PApplet canvas) {
        this.canvas = canvas;
        res = new Resources(canvas);
        effects = new Effects(canvas, res);
        client = new Client();
    }

    public void setup() {
        System.out.println("setup");
        lobbyScreen = new LobbyScreen(canvas, res, canvas.width, canvas.height);

        client.start();
        client.getKryo().register(ServerVariables.class);
        client.getKryo().register(ServerVariables.CURRENT_INFORMATION.class);
        client.getKryo().register(ClientVariables.class);
        client.getKryo().register(LobbyVariables.class);
        client.getKryo().register(network.Map.class);
        map = new Map(canvas, res, 128, 128, 20, 50);

        player = new Player(canvas, res);
        player.setxPosition(map.getBlockWidth());
        player.setyPosition(map.getBlockHeight());

        infoBar = new InformationBar(canvas, player, canvas.width);
        deathScreen = new DeathScreen(canvas, canvas.width, canvas.height);

        try {
            lobbyScreen.setVisible(true);
            lobbyScreen.setText("Searching for the server...");
            String serveraddr = JOptionPane.showInputDialog(null, "Please enter the server's IP address", "Connect to server", JOptionPane.QUESTION_MESSAGE);

            if(serveraddr == null){
                lobbyScreen.setVisible(false);
                return;
            }

            System.out.println("Connecting to Host...");
            lobbyScreen.setText("Connecting to server at " + serveraddr);
            client.connect(5000, serveraddr, 11111, 22222);

            client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if(object instanceof ServerVariables) {
                        ServerVariables sV = (ServerVariables) object;
                        if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.MAP)) {
                            System.out.println(sV.map);
                            map = new Map(canvas, res, sV.map);
                            lobbyScreen.setVisible(false);
                            player.setyPosition(32);
                            player.setxPosition(32);
                        }
                        else if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.PLAYER)) {
                            Player enemy = new Player(canvas,res);
                            enemy.setxPosition(sV.player.xPosition);
                            enemy.setyPosition(sV.player.yPosition);
                            enemy.setHasShield(sV.player.hasShield);
                            enemy.setLookDirection(sV.player.lookDirection);
                            enemy.draw(map.getBlockWidth(),map.getBlockHeight());
                        }
                        else if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.BOMB_PLAYER)) {
                            Player enemy = new Player(canvas,res);
                            enemy.setxPosition(sV.player.xPosition);
                            enemy.setyPosition(sV.player.yPosition);
                            enemy.setHasShield(sV.player.hasShield);
                            enemy.setLookDirection(sV.player.lookDirection);
                            enemy.draw(map.getBlockWidth(), map.getBlockHeight());
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
                    }else if(object instanceof LobbyVariables){
                        LobbyVariables lobby = (LobbyVariables) object;

                        if(!lobby.error && !lobby.isReady){
                            lobbyScreen.setText("Waiting in the lobby\n(" + (30 - lobby.seconds) + " sec. left).\n\nPlayers: " + lobby.connectedClients);
                        }else if(lobby.error){
                            lobbyScreen.setText("Server-side Error:\n" + lobby.errorMsg);
                        }else if(lobby.isReady){
                            lobbyScreen.setText("Waiting for the map...");
                        }
                    }
                }
            });
        } catch (IOException e) {
            lobbyScreen.setText("Something went wrong.\nPlease try again.");
            e.printStackTrace();
        }
    }

    public void draw() {
        step++;
        map.redrawTiles(true);
        player.draw(map.getBlockWidth(), map.getBlockHeight());
        effects.drawEffects();
        infoBar.draw();
        lobbyScreen.draw();

        if(player.getHealth() <= 0) {
            deathScreen.draw();
        }else if(!lobbyScreen.isVisible()){
            if (canvas.keyPressed) {
                keyEvent();
            }
        }
    }

    private void keyEvent() {
        int xMin = (int) Math.floor((player.getXPosition() + (0.25 * map.getBlockWidth())) / map.getBlockWidth());
        int yMin = (int) Math.floor((player.getYPosition() + (0.25 * map.getBlockHeight())) / map.getBlockHeight());

        int xMax = (int) Math.floor((player.getXPosition() + (0.75 * map.getBlockWidth())) / map.getBlockWidth());
        int yMax = (int) Math.floor((player.getYPosition() + (0.75 * map.getBlockWidth())) / map.getBlockHeight());

        int xMinS = (int) Math.floor((player.getXPosition() + player.getSwiftness() + (0.25 * map.getBlockWidth())) / map.getBlockWidth());
        int yMinS = (int) Math.floor((player.getYPosition() + player.getSwiftness() + (0.25 * map.getBlockHeight())) / map.getBlockHeight());

        int xMaxS = (int) Math.floor((player.getXPosition() + player.getSwiftness() + (0.75 * map.getBlockWidth())) / map.getBlockWidth());
        int yMaxS = (int) Math.floor((player.getYPosition() + player.getSwiftness() + (0.75 * map.getBlockWidth())) / map.getBlockHeight());

        map.resetPlayerBooleans();
        map.getTile(xMin, yMin).setPlayer(this.player, this.res);

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

            case 0:
                if(player.canLayBomb() && !map.getTile(xMin, yMin).hasBomb()) {
                    step = 0;
                    player.layBomb();
                    res.sound_layBomb.play();
                    if(xMin < 1) xMin = 1;
                    if(yMin < 1) yMin = 1;
                    effects.addEffect(new Bomb(player, map, effects, map.getBlockWidth(), map.getBlockHeight(), xMin, yMin, 10, 25), xMin * map.getBlockWidth(), yMin * map.getBlockHeight());
                }
                break;
            default:
                System.out.println(canvas.keyCode);
        }
    }
}
