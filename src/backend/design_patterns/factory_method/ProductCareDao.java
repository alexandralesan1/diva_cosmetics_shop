package backend.design_patterns.factory_method;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductCareDao {

    public void insert(ProductData p) {

        String sql =
                "INSERT INTO products_care " +
                        "(name, age_category, brand, product_series, product_range, purpose, volume, ingredients, precautions, cn_code, description, usage_method, category, price, image_path) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getName());
            stmt.setString(2, p.getAgeCategory());
            stmt.setString(3, p.getBrand());
            stmt.setString(4, p.getProductSeries());
            stmt.setString(5, p.getProductRange());
            stmt.setString(6, p.getPurpose());
            stmt.setString(7, p.getVolume());
            stmt.setString(8, p.getIngredients());
            stmt.setString(9, p.getPrecautions());
            stmt.setString(10, p.getCnCode());

            stmt.setString(11, p.getDescription());
            stmt.setString(12, p.getUsageMethod());
            stmt.setString(13, p.getCategory());
            stmt.setDouble(14, p.getPrice());
            stmt.setString(15, p.getImagePath());

            int rows = stmt.executeUpdate();

            System.out.println("✔ Insert complet. Rows affected: " + rows);

        } catch (SQLException e) {
            System.out.println("❌ SQL ERROR INSERT products_care:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ GENERAL ERROR:");
            e.printStackTrace();
        }
    }
}