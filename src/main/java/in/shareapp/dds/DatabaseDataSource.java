package in.shareapp.dds;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import in.shareapp.utils.PropertyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseDataSource {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDataSource.class);
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(PropertyHolder.getProperty("datasource.url"));
        config.setUsername(PropertyHolder.getProperty("datasource.username"));
        config.setPassword(PropertyHolder.getProperty("datasource.password"));

        config.setMaximumPoolSize(10); // Maximum number of connections in the pool
        config.setConnectionTimeout(30000); // Maximum time to wait for a connection
        config.setMinimumIdle(5); // Minimum number of idle connections in the pool
        config.setRegisterMbeans(true); // Enable JMX monitoring for the connection pool

        dataSource = new HikariDataSource(config);
    }

    public Connection getDbConnection() throws SQLException {
        logger.info("Get connection from pool. Active:{} , Idle:{} ", dataSource.getHikariPoolMXBean().getActiveConnections(),
                dataSource.getHikariPoolMXBean().getIdleConnections());
        return dataSource.getConnection();
    }

    public void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
