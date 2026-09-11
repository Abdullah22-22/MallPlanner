package dao;

import model.ServiceArea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAreaDAO {
    public void save(ServiceArea area) throws SQLException {
        String sql = "INSERT INTO service_area (floor_id, type, size) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        )
    }

}
