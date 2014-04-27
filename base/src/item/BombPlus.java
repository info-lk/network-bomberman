package item;

import entity.Player;

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

}
