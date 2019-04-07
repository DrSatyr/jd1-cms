package by.itacademy.pinchuk.cms.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;

@UtilityClass
public class ConnectionManager {

    public static final DataSource DATA_SOURCE;
    public static final String URL_KEY = "db.url";
    public static final String USERNAME_KEY = "db.username";
    public static final String PASSWORD_KEY = "db.password";
    public static final String DRIVER_KEY = "db.driver";
    public static final String POOL_SIZE_KEY = "db.pool.size";

    static {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(AppConfig.get(DRIVER_KEY));
        poolProperties.setUrl(AppConfig.get(URL_KEY));
        poolProperties.setUsername(AppConfig.get(USERNAME_KEY));
        poolProperties.setPassword(AppConfig.get(PASSWORD_KEY));
        poolProperties.setMaxIdle(Integer.parseInt(AppConfig.get(POOL_SIZE_KEY)));
        poolProperties.setMaxActive(Integer.parseInt(AppConfig.get(POOL_SIZE_KEY)));
        DATA_SOURCE = new DataSource(poolProperties);
    }

    @SneakyThrows
    public Connection get() {
        return DATA_SOURCE.getConnection();
    }
}
