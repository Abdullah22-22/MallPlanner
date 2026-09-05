package dao;

import model.Mall;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MallDAO {

    public void save(Mall mall) throws SQLException {
        String sql = "INSERT INTO mall (name, total_area) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, mall.getName());
            stmt.setDouble(2, mall.getTotalArea());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    mall.setId(keys.getInt(1));
                }
            }
        }
    }

    public Mall findById(int id) throws SQLException {
        String sql = "SELECT id, name, total_area FROM mall WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Mall(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("total_area"));
                }
            }
        }
        return null;
    }

    public List<Mall> findAll() throws SQLException {
        String sql = "SELECT id, name, total_area FROM mall";
        List<Mall> malls = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                malls.add(new Mall(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("total_area")));
            }
        }
        return malls;
    }

    public void update(Mall mall) throws SQLException {
        String sql = "UPDATE mall SET name = ?, total_area = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mall.getName());
            stmt.setDouble(2, mall.getTotalArea());
            stmt.setInt(3, mall.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM mall WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}