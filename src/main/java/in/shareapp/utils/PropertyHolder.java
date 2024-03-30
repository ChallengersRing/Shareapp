package in.shareapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertyHolder {
    private static Properties properties;

    // Private constructor to prevent instantiation
    private PropertyHolder() {
    }

    // Static block to load properties when the class is initialized
    static {
        loadProperties();
        System.out.println("Size::::"+properties.size());
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = PropertyHolder.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the value of a property by its key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultVal) {
        return Optional.ofNullable(properties.getProperty(key))
                .orElse(defaultVal);
    }
}
