package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();

        try (InputStream in = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (in == null) {
                throw new SQLException("db.properties not found");
            }
            props.load(in);

        } catch (Exception e) {
            throw new SQLException("Cannot read db.properties", e);
        }

        String url = "jdbc:mariadb://"
                + props.getProperty("db.host") + ":"
                + props.getProperty("db.port") + "/"
                + props.getProperty("db.name");

        return DriverManager.getConnection(
                url,
                props.getProperty("db.user"),
                props.getProperty("db.password"));
    }
}