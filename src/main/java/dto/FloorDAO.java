package dao;

import model.Floor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FloorDAO {

    public void save(Floor floor) throws SQLException {
        String sql = "INSERT INTO floor "
                + "(mall_id, floor_number, area, rent_price, cost) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, floor.getMallId());
            stmt.setInt(2, floor.getFloorNumber());
            stmt.setDouble(3, floor.getArea());
            stmt.setDouble(4, floor.getRentPrice());
            stmt.setDouble(5, floor.getCost());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    floor.setId(keys.getInt(1));
                }
            }
        }
    }

    public List<Floor> findByMallId(int mallId) throws SQLException {
        String sql = "SELECT id, mall_id, floor_number, area, rent_price, cost "
                + "FROM floor WHERE mall_id = ? ORDER BY floor_number";
        List<Floor> floors = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mallId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    floors.add(new Floor(
                            rs.getInt("id"),
                            rs.getInt("mall_id"),
                            rs.getInt("floor_number"),
                            rs.getDouble("area"),
                            rs.getDouble("rent_price"),
                            rs.getDouble("cost")));
                }
            }
        }
        return floors;
    }

    public void update(Floor floor) throws SQLException {
        String sql = "UPDATE floor SET mall_id = ?, floor_number = ?, "
                + "area = ?, rent_price = ?, cost = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, floor.getMallId());
            stmt.setInt(2, floor.getFloorNumber());
            stmt.setDouble(3, floor.getArea());
            stmt.setDouble(4, floor.getRentPrice());
            stmt.setDouble(5, floor.getCost());
            stmt.setInt(6, floor.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM floor WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}