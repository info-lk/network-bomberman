package item;

import entity.Player;

public class BombPlus extends Item {

    private final String name = "Bombe plus";
    private final String description = "With this item you can use one more bomb at the same time.";
    private byte id = 01;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte getId() {
        return id;
    }

    public void onCollect(Player player) {
        player.addBombs(1);
    }

}
