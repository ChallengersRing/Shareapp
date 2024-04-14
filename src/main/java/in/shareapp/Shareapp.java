package in.shareapp;

import java.io.File;

import in.shareapp.server.EmbeddedServer;
import in.shareapp.server.TomcatServer;
import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;

public class Shareapp {

    private static Tomcat tomcat = new Tomcat();

    public static void main(String[] args) throws Exception {

//        EmbeddedServer app = new TomcatServer();
//        app.run(args);

        String appName = "shareapp";
        File baseDir = new File("src/main/webapp/");
        System.out.println(baseDir.getAbsolutePath());

        tomcat.setPort(5500);
        Context context = tomcat.addWebapp(tomcat.getHost(), "/" + appName, baseDir.getAbsolutePath());

        // Set the location of the web.xml file
        context.setConfigFile(new File("src/main/webapp/WEB-INF/web.xml").toURI().toURL());

        System.out.println(getApplicationUrl(appName));

        tomcat.start();
        Server server = tomcat.getServer();

        // Add shutdown hook to gracefully stop Tomcat
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutting down gracefully...");
                tomcat.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        server.await();
    }

    public static String getApplicationUrl(String appName) {
        return String.format("http://%s:%d/%s", tomcat.getHost().getName(),
                tomcat.getConnector().getPort(), appName);
    }
}