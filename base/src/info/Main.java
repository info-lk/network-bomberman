package info;

import com.esotericsoftware.kryonet.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();
        server.bind(54555, 54777);

    }
}
