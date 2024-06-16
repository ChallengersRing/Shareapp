package in.shareapp;

import in.shareapp.server.EmbeddedServer;
import in.shareapp.server.TomcatServer;
import in.shareapp.utils.LogoPrinter;

public class Shareapp {

    public static void main(String[] args) throws Exception {
        LogoPrinter.printLogo();
        EmbeddedServer shareapp = new TomcatServer();
        shareapp.run(args);
    }
}
