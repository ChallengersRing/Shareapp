package in.shareapp.server;

import in.shareapp.Shareapp;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.JarResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class TomcatServer implements EmbeddedServer {
    private static final Logger logger = Logger.getLogger(TomcatServer.class.getName());
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 5500;
    private static final String SHAREAPP_CONTEXT_PATH = "/shareapp";
    private static final String DOC_BASE = ".";
    private static final String ADDITION_WEB_INF_CLASSES = "target/classes";
    private static final String WEB_APP_MOUNT = "/WEB-INF/classes";
    private static final String INTERNAL_PATH = "/";

    @Override
    public void run(String[] args) {
        Tomcat tomcat = new Tomcat();

        // Add shutdown hook to gracefully stop Tomcat
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("Shutting down gracefully...");
                tomcat.stop();
                tomcat.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        try {
            int port = this.getPort(args);
            logger.info("Setting up Tomcat...");
            this.setupTomcat(tomcat, port);
            logger.info("Starting Tomcat...");
            tomcat.start();
            logger.info("-----------------------------------------------------------------");
            logger.info("Application started with URL: http://" + DEFAULT_HOST + ":" + port + SHAREAPP_CONTEXT_PATH);
            logger.info("Hit Ctrl + D or C to stop it...");
            logger.info("-----------------------------------------------------------------");
            tomcat.getServer().await();
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.severe(exception.getMessage());
            logger.severe("Exit...");
            System.exit(1);
        }
    }

    private int getPort(String[] args) {
        if (args.length > 0) {
            String port = args[0];
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException exception) {
                logger.severe("Invalid port number argument {}" + port + exception);
            }
        }
        return DEFAULT_PORT;
    }

    private void setupTomcat(Tomcat tomcat, int port) {
        tomcat.setHostname(DEFAULT_HOST);
        tomcat.getHost().setAppBase(DOC_BASE);
        tomcat.setPort(port);
        tomcat.getConnector();
        this.addContext(tomcat);
    }

    private void addContext(Tomcat tomcat) {
        logger.info("--------------------isJar---------------:" + isJar());

        String webAppMount = "/WEB-INF/classes";
        if (!isJar()) {
            logger.info("--------------------getResourceFromFs---------------:" + getResourceFromFs());
            Context context = tomcat.addWebapp(SHAREAPP_CONTEXT_PATH, new File(getResourceFromFs(), "META-INF/resources").getAbsolutePath());
            WebResourceRoot webResourceRoot = new StandardRoot(context);
            WebResourceSet webResourceSet = new DirResourceSet(webResourceRoot, webAppMount, getResourceFromFs(), "/");
            webResourceRoot.addPreResources(webResourceSet);
            context.setResources(webResourceRoot);
        } else {
            logger.info("--------------------getResourceFromJarFile---------------:" + getResourceFromJarFile());
            Context context = tomcat.addWebapp(SHAREAPP_CONTEXT_PATH, "/");
            WebResourceRoot webResourceRoot = new StandardRoot(context);
            WebResourceSet webResourceSet = new JarResourceSet(webResourceRoot, webAppMount, getResourceFromJarFile(), "/");
            webResourceRoot.addJarResources(webResourceSet);
            context.setResources(webResourceRoot);
        }
    }

    public static boolean isJar() {
        URL resource = Shareapp.class.getResource("/");
        return resource == null;
    }

    public static String getResourceFromJarFile() {
        File jarFile = new File(System.getProperty("java.class.path"));
        return jarFile.getAbsolutePath();
    }

    public static String getResourceFromFs() {
        URL resource = Shareapp.class.getResource("/");
        return resource.getFile();
    }
}
