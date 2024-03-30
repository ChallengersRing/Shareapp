package in.shareapp.dds;

import in.shareapp.utils.PropertyHolder;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseDataSource {
    private static final Logger logger = Logger.getLogger(DatabaseDataSource.class.getName());

    private static final String DB_URL = PropertyHolder.getProperty("datasource.url");
    private static final String DB_USER = PropertyHolder.getProperty("datasource.username");
    private static final String DB_PASSWORD = PropertyHolder.getProperty("datasource.password");

    static {
        try {
            Class.forName(PropertyHolder.getProperty("datasource.driver-class-name"));
        } catch (ClassNotFoundException cnfEx) {
            logger.log(Level.SEVERE, "DB Driver not found", cnfEx);
            cnfEx.printStackTrace();
        }
    }

    public Connection getDbConnection() {
        Connection dbCon = null;

        try {
            logger.info("Connn:::"+DB_URL+ DB_USER + DB_PASSWORD);
            dbCon = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, "DB Connection fails", sqlEx);
            sqlEx.printStackTrace();
        }

        return dbCon;
    }

    public void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, "Fail to close statement", sqlEx);
            sqlEx.printStackTrace();
        }
    }

    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, "Fail to close result set", sqlEx);
            sqlEx.printStackTrace();
        }
    }

    public void closeDbConnection(Connection dbCon) {
        try {
            if (dbCon != null) {
                dbCon.close();
            }
        } catch (SQLException sqlEx) {
            logger.log(Level.SEVERE, "Fail to close db connection", sqlEx);
            sqlEx.printStackTrace();
        }
    }
}