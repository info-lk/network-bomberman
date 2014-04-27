package network;

//import java.util.Arrays;

public class Map {
    public int width;
    public int height;
    public String tiles;

    public Map() {
    }

    @Override
    public String toString() {
        return "Map{" +
                "width=" + width +
                ", height=" + height +
                ", tiles=" + tiles +
                '}';
    }
}
