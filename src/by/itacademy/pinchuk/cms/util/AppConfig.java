package by.itacademy.pinchuk.cms.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Properties;

@UtilityClass
public class AppConfig {

    private static final String CONFIG_FILE_NAME = "app.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream resourceAsStream = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) throws InvalidParameterException {
        return PROPERTIES.getProperty(key);
    }

    public Properties getAll() {
        return PROPERTIES;
    }
}
