package network;

/**
 * Created by Julian on 07.04.2014.
 */
public class VBomb {
    public double layBombAtX, layBombAtY;

    public int kind; //1 = Water, 2 = ?... rest = normal

    public VBomb(double layBombAtX, double layBombAtY, int kind) {
        this.layBombAtX = layBombAtX;
        this.layBombAtY = layBombAtY;
        this.kind = kind;
    }
}
