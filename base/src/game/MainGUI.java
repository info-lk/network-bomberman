package game;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: leobernard
 * Date: 24.03.14
 * Time: 21:11
 */
public class MainGUI extends Frame {
    public MainGUI(int width, int height) {
        super("Networked Bomberman");

        MainApplet app = new MainApplet();

        add(app);
        app.init();
    }

    public static void main(String[] args) {
        new MainGUI(512, 512);
    }
}
