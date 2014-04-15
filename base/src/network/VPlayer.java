package network;

import entity.Player;

public class VPlayer {

    public double xPosition;
    public double yPosition;
    public Player.DIRECTION lookDirection;


    //private double health;

    public boolean hasShield;

    public VPlayer(double xPosition, double yPosition, boolean hasShield, Player.DIRECTION lookDirection) {
        this.hasShield = hasShield;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.lookDirection = lookDirection;
    }

}
