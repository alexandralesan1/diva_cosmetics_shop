package backend.design_patterns.builder;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AccessoryDao {

    public void insert(Accessory a) {

        String sql = """
            INSERT INTO products_accessories
            (name, brand, product_range, product_series, purpose,
             quantity, image_path, cn_code, usage_method,
             description, category, price)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getName());
            ps.setString(2, a.getBrand());
            ps.setString(3, a.getProductRange());
            ps.setString(4, a.getProductSeries());
            ps.setString(5, a.getPurpose());
            ps.setString(6, a.getQuantity());
            ps.setString(7, a.getImagePath());
            ps.setString(8, a.getCnCode());
            ps.setString(9, a.getUsageMethod());
            ps.setString(10, a.getDescription());
            ps.setString(11, a.getCategory());
            ps.setDouble(12, a.getPrice());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}