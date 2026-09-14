package dao;

import model.ServiceArea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAreaDAO {
    public void save(ServiceArea area) throws SQLException {
        String sql = "INSERT INTO service_area (floor_id, type, size) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, area.getFloorId());
            stmt.setString(2, area.getType());
            stmt.setDouble(3, area.getSize());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                area.setId(keys.getInt(1));
            }
        }

    }

    public List<ServiceArea> findByFloorId(int floorId) throws SQLException {
        String sql = "SELECT * FROM service_area WHERE floor_id = ?";
        List<ServiceArea> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, floorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ServiceArea area = new ServiceArea(
                    rs.getInt("id"),
                    rs.getInt("floor_id"),
                    rs.getString("type"),
                    rs.getDouble("size")
                );
                result.add(area);
            }
        }
        return result;
    }

    public void update(ServiceArea area) throws SQLException {
        String sql = "UPDATE service_area SET type = ?, size = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, area.getType());
            stmt.setDouble(2, area.getSize());
            stmt.setInt(3, area.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM service_area WHERE Id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
