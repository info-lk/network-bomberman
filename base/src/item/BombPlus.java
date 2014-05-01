package item;

import entity.Player;
import processing.core.PImage;
import resources.Resources;

public class BombPlus extends Item {

    @Override
    public String getName() {
        return "Bomb plus";
    }

    @Override
    public byte getId() {
        return 1;
    }

    public void onCollect(Player player) {
        player.addBombs(1);
    }

    @Override
    public PImage getImage(Resources res) {
        return res.egg;
    }
}
