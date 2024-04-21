package in.shareapp.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.util.logging.Logger;

public class TomcatServer implements EmbeddedServer {
    private static final Logger LOGGER = Logger.getLogger(TomcatServer.class.getName());
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 5500;
    private static final String DEFAULT_CONTEXT_PATH = "/shareapp";
    private static final String DOC_BASE = ".";
    private static final String ADDITION_WEB_INF_CLASSES = "target/classes";
    private static final String WEB_APP_MOUNT = "/WEB-INF/classes";
    private static final String INTERNAL_PATH = "/";

    @Override
    public void run(String[] args) {
        int port = this.getPort(args);
        Tomcat tomcat = this.setupTomcat(port);

        // Add shutdown hook to gracefully stop Tomcat
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LOGGER.info("Shutting down gracefully...");
                tomcat.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        try {
            tomcat.start();
            LOGGER.info("Application started with URL: http://" + DEFAULT_HOST + ":" + port + DEFAULT_CONTEXT_PATH);
            LOGGER.info("Hit Ctrl + D or C to stop it...");
            tomcat.getServer().await();
        } catch (LifecycleException exception) {
            LOGGER.severe("{}" + exception.getMessage());
            LOGGER.severe("Exit...");
            System.exit(1);
        }
    }

    private int getPort(String[] args) {
        if (args.length > 0) {
            String port = args[0];
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException exception) {
                LOGGER.severe("Invalid port number argument {}" + port + exception);
            }
        }

        return DEFAULT_PORT;
    }

    private Tomcat setupTomcat(int port) {
        Tomcat tomcat = new Tomcat();
        tomcat.setHostname(DEFAULT_HOST);
        tomcat.getHost().setAppBase(DOC_BASE);
        tomcat.setPort(port);
        tomcat.getConnector();
        addContext(tomcat);

        return tomcat;
    }

    private void addContext(Tomcat tomcat) {
        File baseDir = new File("src/main/webapp/");
        System.out.println(baseDir.getAbsolutePath());
        Context context = tomcat.addWebapp(DEFAULT_CONTEXT_PATH, baseDir.getAbsolutePath());

        File classes = new File(ADDITION_WEB_INF_CLASSES);
        String base = classes.getAbsolutePath();
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, WEB_APP_MOUNT, base, INTERNAL_PATH));
        context.setResources(resources);
    }
}
