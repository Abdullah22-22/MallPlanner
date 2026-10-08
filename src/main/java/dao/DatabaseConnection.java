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

            if (in != null) {
                props.load(in);
            }

        } catch (Exception e) {
            throw new SQLException("Cannot read db.properties", e);
        }

        String host = value("DB_HOST", props, "db.host");
        String port = value("DB_PORT", props, "db.port");
        String name = value("DB_NAME", props, "db.name");
        String user = value("DB_USER", props, "db.user");
        String pass = value("DB_PASS", props, "db.password");

        if (host == null || port == null || name == null) {
            throw new SQLException(
                    "No database settings found. Add db.properties or set DB_HOST, DB_PORT and DB_NAME.");
        }

        String url = "jdbc:mariadb://" + host + ":" + port + "/" + name;

        return DriverManager.getConnection(url, user, pass);
    }

    /** Environment variable first, then db.properties. */
    private static String value(String env, Properties props, String key) {
        String fromEnv = System.getenv(env);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        return props.getProperty(key);
    }
}