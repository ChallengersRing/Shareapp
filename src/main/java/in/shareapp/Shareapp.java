package in.shareapp;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class Shareapp {

    private static Tomcat tomcat = new Tomcat();

    public static void main(String[] args) throws Exception {
        String appName = "shareapp";
        File baseDir = new File("src/main/webapp/");
        System.out.println(baseDir.getAbsolutePath());

        tomcat.setPort(5500);
        Context context = tomcat.addWebapp(tomcat.getHost(),"/"+appName, baseDir.getAbsolutePath());

        // Set the location of the web.xml file
        context.setConfigFile(new File("src/main/webapp/WEB-INF/web.xml").toURI().toURL());

        System.out.println(getApplicationUrl(appName));

        tomcat.start();
        tomcat.getServer().await();
    }

    public static String getApplicationUrl(String appName) {
        return String.format("http://%s:%d/%s", tomcat.getHost().getName(),
                tomcat.getConnector().getPort(), appName);
    }
}