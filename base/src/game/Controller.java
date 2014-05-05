package game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import entity.Bomb;
import entity.Player;
import graphics.Effects;
import info.DeathScreen;
import info.InformationBar;
import info.LobbyScreen;
import map.Map;
import network.ClientVariables;
import network.LobbyVariables;
import network.ServerVariables;
import network.VPlayer;
import processing.core.PApplet;
import resources.Resources;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

class Controller {
    private Client client; // Der Client
    private PApplet canvas; // Die Zeichenfläche für alle Grafiken
    private Map map; // Die Karte, auf der die Spieler spielen können
    private Resources res; // Die Resourcen, werden nur einmal initialisiert und dann an alle weiteren Klassen weitergegeben
    private Effects effects; // Die auf der Zeichenfläche angezeigten Effekte, laufen unabhängig vom Rest des Spiels
    private Player player; // Der aktuelle, lokale Spieler
    private InformationBar infoBar; // Die schwarze Info-Bar oben im Spielfenster
    private DeathScreen deathScreen; //
    private LobbyScreen lobbyScreen;
    private Player[] enemies;
    private int ID;
    private int step;

    public Controller(PApplet canvas) {
        this.canvas = canvas;
        res = new Resources(canvas);
        effects = new Effects(canvas, res);
        client = new Client(32768, 8192);
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
        map = new Map(canvas, res, 16, 16, 20, 50);

        ID = -1;

        player = new Player(canvas, res);
        player.setxPosition(map.getBlockWidth());
        player.setyPosition(map.getBlockHeight());

        infoBar = new InformationBar(canvas, player);
        deathScreen = new DeathScreen(canvas, res);

        try {
            lobbyScreen.setVisible(true);
            lobbyScreen.setText("Searching for the server...");
            String serveraddr = JOptionPane.showInputDialog(null, "Please enter the server's IP address or press 'Cancel' to play in single player mode", "Connect to server", JOptionPane.QUESTION_MESSAGE);

            if (serveraddr == null) {
                lobbyScreen.setVisible(false);
                return;
            }

            System.out.println("Connecting to Host...");
            lobbyScreen.setText("Connecting to server at " + serveraddr);
            client.connect(5000, serveraddr, 11111, 22222);

            client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof ServerVariables) {
                        ServerVariables sV = (ServerVariables) object;
                        if (sV.current.equals(ID)) {
                            ID = sV.ID;
                            System.out.println("Your Id is: " + ID);
                        }
                        if (sV.current.equals(ServerVariables.CURRENT_INFORMATION.MAP)) {
                            System.out.println(sV.map);
                            map = new Map(canvas, res, sV.map);
                            lobbyScreen.setVisible(false);
                            player.setyPosition(32);
                            player.setxPosition(32);
<<<<<<< HEAD
                        }
                        else if(sV.current.equals(ServerVariables.CURRENT_INFORMATION.ID)) {
                            System.out.println("HIIIII");
=======
>>>>>>> 3dda1b053c0dc45c0128a49b2ecce9048c88a2a5
                        } else if (sV.current.equals(ServerVariables.CURRENT_INFORMATION.PLAYERS)) {
                            enemies = new Player[sV.players.length - 1];
                            VPlayer[] copy = sV.players;
                            int pos = 0;
                            for (int i = 0; i < copy.length; i++) {
                                if (i != ID) {
                                    enemies[pos] = new Player(canvas, res);
                                    enemies[pos].createPlayerFromVPlayer(copy[i]);
                                    pos++;
                                }
                            }
                            player.createPlayerFromVPlayer(copy[ID]);
                            System.out.println("Players received");
                        } else if (sV.current.equals(ServerVariables.CURRENT_INFORMATION.PLAYER)) {
                            enemies[sV.player.ID].setxPosition(sV.player.xPosition);
                            enemies[sV.player.ID].setyPosition(sV.player.yPosition);
                            enemies[sV.player.ID].setHasShield(sV.player.hasShield);
                            enemies[sV.player.ID].setLookDirection(sV.player.lookDirection);
                        } else if (sV.current.equals(ServerVariables.CURRENT_INFORMATION.BOMB_PLAYER)) {
                            Player enemy = new Player(canvas, res);
                            enemy.setxPosition(sV.player.xPosition);
                            enemy.setyPosition(sV.player.yPosition);
                            enemy.setHasShield(sV.player.hasShield);
                            enemy.setLookDirection(sV.player.lookDirection);
                            enemy.draw(map.getBlockWidth(), map.getBlockHeight());
                        } else if (sV.current.equals(ServerVariables.CURRENT_INFORMATION.COMMAND)) {
                            switch (sV.command) {
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
                                default:
                                    System.out.println("Unknown server command");
                            }
                        }
                    } else if (object instanceof LobbyVariables) {
                        LobbyVariables lobby = (LobbyVariables) object;

                        if (!lobby.error && !lobby.isReady) {
                            lobbyScreen.setText("Waiting in the lobby\n(" + (30 - lobby.seconds) + " sec. left).\n\nPlayers: " + lobby.connectedClients);
                        } else if (lobby.error) {
                            lobbyScreen.setText("Server-side Error:\n" + lobby.errorMsg);
                        } else if (lobby.isReady) {
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

        if (player.getHealth() <= 0) {
            deathScreen.draw();
        } else if (!lobbyScreen.isVisible()) {
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

        map.resetPlayerBooleans();
        map.getTile(xMin, yMin).setPlayer(this.player, this.res);

        switch (canvas.keyCode) {
            case KeyEvent.VK_UP:
                if (map.getTile(xMin, yMin).isPassable() && map.getTile(xMax, yMin).isPassable()) {
                    player.move(VPlayer.DIRECTION.UP);
                } else {
                    player.setyPosition((yMax - 0.2) * map.getBlockHeight());
                }
                break;

            case KeyEvent.VK_DOWN:
                if (map.getTile(xMin, yMax).isPassable() && map.getTile(xMax, yMax).isPassable()) {
                    player.move(VPlayer.DIRECTION.DOWN);
                } else {
                    player.setyPosition((yMin + 0.2) * map.getBlockHeight());
                }
                break;

            case KeyEvent.VK_LEFT:
                if (map.getTile(xMin, yMin).isPassable() && map.getTile(xMin, yMax).isPassable()) {
                    player.move(VPlayer.DIRECTION.LEFT);
                } else {
                    player.setxPosition((xMax - 0.2) * map.getBlockWidth());
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (map.getTile(xMax, yMin).isPassable() && map.getTile(xMax, yMax).isPassable()) {
                    player.move(VPlayer.DIRECTION.RIGHT);
                } else {
                    player.setxPosition((xMin + 0.2) * map.getBlockWidth());
                }
                break;

            case 0:
                if (player.canLayBomb() && !map.getTile(xMin, yMin).hasBomb()) {
                    step = 0;
                    player.layBomb();
                    res.sound_layBomb.play();
                    if (xMin < 1) xMin = 1;
                    if (yMin < 1) yMin = 1;
                    effects.addEffect(new Bomb(player, map, effects, map.getBlockWidth(), map.getBlockHeight(), xMin, yMin, 10, 25), xMin * map.getBlockWidth(), yMin * map.getBlockHeight());
                }
                break;
            default:
                System.out.println(canvas.keyCode);
        }
    }
}
