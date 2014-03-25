package item;

import entity.Player;

public abstract class Item {

    public abstract String getName();

    public abstract byte getId();

    public abstract void onCollect(Player player);

}
