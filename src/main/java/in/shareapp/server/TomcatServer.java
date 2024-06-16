package in.shareapp.server;

import in.shareapp.Shareapp;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.JarResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

public class TomcatServer implements EmbeddedServer {
    private static final Logger logger = LoggerFactory.getLogger(TomcatServer.class);
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 5500;
    private static final String SHAREAPP_CONTEXT_PATH = "/shareapp";
    private static final String DOC_BASE = ".";

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
                logger.error(e.getMessage(), e);
            }
        }));

        try {
            int port = this.getPort(args);
            logger.info("Setting up Tomcat...");
            this.setupTomcat(tomcat, port);
            tomcat.start();
            logger.info("-----------------------------------------------------------------");
            logger.info("Application started with URL: http://{}:{}/{}", DEFAULT_HOST, port, SHAREAPP_CONTEXT_PATH);
            logger.info("Hit Ctrl + D or C to stop it...");
            logger.info("-----------------------------------------------------------------");
            tomcat.getServer().await();
        } catch (Exception exception) {
            logger.error("Message:{}", exception.getMessage(), exception);
            logger.error("Exit...");
            System.exit(1);
        }
    }

    private int getPort(String[] args) {
        if (args.length > 0) {
            String port = args[0];
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException exception) {
                logger.error("Invalid port number argument {}. ", port, exception);
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
        logger.info("--------------------isJar---------------:{}", isJar());

        String webAppMount = "/WEB-INF/classes";
        if (!isJar()) {
            logger.info("--------------------getResourceFromFs---------------:{}", getResourceFromFs());
            Context context = tomcat.addWebapp(SHAREAPP_CONTEXT_PATH, new File(getResourceFromFs(), "META-INF/resources").getAbsolutePath());
            WebResourceRoot webResourceRoot = new StandardRoot(context);
            WebResourceSet webResourceSet = new DirResourceSet(webResourceRoot, webAppMount, getResourceFromFs(), "/");
            webResourceRoot.addPreResources(webResourceSet);
            context.setResources(webResourceRoot);
        } else {
            logger.info("--------------------getResourceFromJarFile---------------:{}", getResourceFromJarFile());
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
