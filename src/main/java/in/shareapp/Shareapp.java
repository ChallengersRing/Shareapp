package in.shareapp;

import in.shareapp.server.EmbeddedServer;
import in.shareapp.server.TomcatServer;

public class Shareapp {
    public static void main(String[] args) throws Exception {
        EmbeddedServer shareapp = new TomcatServer();
        shareapp.run(args);
    }
}
