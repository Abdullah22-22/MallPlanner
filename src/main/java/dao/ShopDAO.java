package dao;

import model.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShopDAO {

    public void save(Shop shop) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet keys = null;

        try {
            conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO shop (floor_id, name, area, category) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, shop.getFloorId());
            stmt.setString(2, shop.getName());
            stmt.setDouble(3, shop.getArea());
            stmt.setString(4, shop.getCategory());

            stmt.executeUpdate();

            keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                shop.setId(keys.getInt(1));
            }

        }
        finally {
            if (keys != null) keys.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    public List<Shop> findByFloorId(int floorId) throws SQLException {
        List<Shop> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM shop WHERE floor_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, floorId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int floorIdFromDb = rs.getInt("floor_id");
                String name = rs.getString("name");
                double area = rs.getDouble("area");
                String category = rs.getString("category");

                Shop shop = new Shop(id, floorIdFromDb, name, area, category);
                result.add(shop);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return result;

    }

    public void update(Shop shop) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();

            String sql = "UPDATE shop SET name = ?, area = ?, category = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, shop.getName());
            stmt.setDouble(2, shop.getArea());
            stmt.setString(3, shop.getCategory());
            stmt.setInt(4, shop.getId());

            stmt.executeUpdate();


        }
        finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    public void delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();

            String sql = "DELETE FROM shop WHERE id = ?";
            stmt = conn.prepareStatement (sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

    }
}
