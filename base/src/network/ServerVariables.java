package network;


import map.Map;

/**
 * Created by winterj on 26.03.2014.
 */
public class ServerVariables {

    public enum CURRENT_INFORMATION {BOMB_PLAYER, MAP, PLAYER, BOMB, COMMAND};
    public CURRENT_INFORMATION current;
    public Map map;

    public VPlayer player; //The player to update

    public VBomb bomb;

    public byte command = -1;  //-1 = nothing|0 = pause| 1 = kick| 2 = resume|

    public ServerVariables(Map map) {
        this.map = map;
        current = CURRENT_INFORMATION.MAP;
    }

    public ServerVariables(VPlayer player) {
        this.player = player;
        current = CURRENT_INFORMATION.PLAYER;
    }

    public ServerVariables(byte command) {
        this.command = command;
        current = CURRENT_INFORMATION.COMMAND;
    }

    public ServerVariables(VBomb bomb) {
        this.bomb = bomb;
        current = CURRENT_INFORMATION.BOMB;
    }

    public ServerVariables(VBomb bomb, VPlayer player) {
        this.player = player;
        this.bomb = bomb;
        current = CURRENT_INFORMATION.BOMB_PLAYER;
    }
}