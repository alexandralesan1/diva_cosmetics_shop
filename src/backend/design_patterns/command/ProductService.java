package backend.design_patterns.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;

public class ProductService {

    private final Connection connection;

    private static final Set<String> ALLOWED_TABLES = Set.of(
            "products_foundation",
            "products_concealer",
            "products_mascara",
            "products_lipstick",
            "products_blush"
    );

    public ProductService(Connection connection) {
        this.connection = connection;
    }

    private void validateTable(String table) {

        if (!ALLOWED_TABLES.contains(table)) {
            throw new IllegalArgumentException("Invalid table: " + table);
        }
    }

    // ================= UPDATE =================

    public void updateProduct(
            String table,
            int id,

            String name,
            String ageCategory,
            String brand,
            String productSeries,
            String productRange,
            String purpose,
            String volume,
            String ingredients,
            String imagePath,
            String cnCode,
            String precautions,
            String usageMethod,
            String description,
            String category,
            double price,

            String coverage,
            String skinType,
            String finish,
            String color,
            String waterproof,

            String volumeEffect,
            String brushType,

            String matte,
            String longLasting,

            String powderOrCream,
            String shimmer

    ) throws Exception {

        validateTable(table);

        switch (table) {

            // =========================================================
            // FOUNDATION + CONCEALER
            // =========================================================

            case "products_foundation":
            case "products_concealer":

                updateFoundationOrConcealer(
                        table,
                        id,
                        name,
                        ageCategory,
                        brand,
                        productSeries,
                        productRange,
                        purpose,
                        volume,
                        ingredients,
                        imagePath,
                        cnCode,
                        precautions,
                        usageMethod,
                        description,
                        category,
                        price,
                        coverage,
                        skinType,
                        finish,
                        color,
                        waterproof
                );

                break;

            // =========================================================
            // MASCARA
            // =========================================================

            case "products_mascara":

                updateMascara(
                        table,
                        id,
                        name,
                        ageCategory,
                        brand,
                        productSeries,
                        productRange,
                        purpose,
                        volume,
                        ingredients,
                        imagePath,
                        cnCode,
                        precautions,
                        usageMethod,
                        description,
                        category,
                        price,
                        volumeEffect,
                        brushType,
                        waterproof
                );

                break;

            // =========================================================
            // LIPSTICK
            // =========================================================

            case "products_lipstick":

                updateLipstick(
                        table,
                        id,
                        name,
                        ageCategory,
                        brand,
                        productSeries,
                        productRange,
                        purpose,
                        volume,
                        ingredients,
                        imagePath,
                        cnCode,
                        precautions,
                        usageMethod,
                        description,
                        category,
                        price,
                        color,
                        finish,
                        matte,
                        longLasting
                );

                break;

            // =========================================================
            // BLUSH
            // =========================================================

            case "products_blush":

                updateBlush(
                        table,
                        id,
                        name,
                        ageCategory,
                        brand,
                        productSeries,
                        productRange,
                        purpose,
                        volume,
                        ingredients,
                        imagePath,
                        cnCode,
                        precautions,
                        usageMethod,
                        description,
                        category,
                        price,
                        color,
                        powderOrCream,
                        shimmer
                );

                break;

            default:
                throw new RuntimeException("Unknown table: " + table);
        }
    }

    // =========================================================
    // FOUNDATION / CONCEALER
    // =========================================================

    private void updateFoundationOrConcealer(
            String table,
            int id,

            String name,
            String ageCategory,
            String brand,
            String productSeries,
            String productRange,
            String purpose,
            String volume,
            String ingredients,
            String imagePath,
            String cnCode,
            String precautions,
            String usageMethod,
            String description,
            String category,
            double price,

            String coverage,
            String skinType,
            String finish,
            String color,
            String waterproof

    ) throws Exception {

        String sql = """
            UPDATE %s
            SET
                name=?,
                age_category=?,
                brand=?,
                product_series=?,
                product_range=?,
                purpose=?,
                volume=?,
                ingredients=?,
                image_path=?,
                cn_code=?,
                precautions=?,
                usage_method=?,
                description=?,
                category=?,
                price=?,
                coverage=?,
                skin_type=?,
                finish=?,
                color=?,
                waterproof=?
            WHERE id=?
        """.formatted(table);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, ageCategory);
            ps.setString(3, brand);
            ps.setString(4, productSeries);
            ps.setString(5, productRange);
            ps.setString(6, purpose);
            ps.setString(7, volume);
            ps.setString(8, ingredients);
            ps.setString(9, imagePath);
            ps.setString(10, cnCode);
            ps.setString(11, precautions);
            ps.setString(12, usageMethod);
            ps.setString(13, description);
            ps.setString(14, category);
            ps.setDouble(15, price);
            ps.setString(16, coverage);
            ps.setString(17, skinType);
            ps.setString(18, finish);
            ps.setString(19, color);
            ps.setString(20, waterproof);
            ps.setInt(21, id);

            int rows = ps.executeUpdate();

            System.out.println("✔ UPDATED " + rows + " ROWS IN " + table);
        }
    }

    // =========================================================
    // MASCARA
    // =========================================================

    private void updateMascara(
            String table,
            int id,

            String name,
            String ageCategory,
            String brand,
            String productSeries,
            String productRange,
            String purpose,
            String volume,
            String ingredients,
            String imagePath,
            String cnCode,
            String precautions,
            String usageMethod,
            String description,
            String category,
            double price,

            String volumeEffect,
            String brushType,
            String waterproof

    ) throws Exception {

        String sql = """
            UPDATE %s
            SET
                name=?,
                age_category=?,
                brand=?,
                product_series=?,
                product_range=?,
                purpose=?,
                volume=?,
                ingredients=?,
                image_path=?,
                cn_code=?,
                precautions=?,
                usage_method=?,
                description=?,
                category=?,
                price=?,
                volume_effect=?,
                brush_type=?,
                waterproof=?
            WHERE id=?
        """.formatted(table);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, ageCategory);
            ps.setString(3, brand);
            ps.setString(4, productSeries);
            ps.setString(5, productRange);
            ps.setString(6, purpose);
            ps.setString(7, volume);
            ps.setString(8, ingredients);
            ps.setString(9, imagePath);
            ps.setString(10, cnCode);
            ps.setString(11, precautions);
            ps.setString(12, usageMethod);
            ps.setString(13, description);
            ps.setString(14, category);
            ps.setDouble(15, price);
            ps.setString(16, volumeEffect);
            ps.setString(17, brushType);
            ps.setString(18, waterproof);
            ps.setInt(19, id);

            int rows = ps.executeUpdate();

            System.out.println("✔ UPDATED " + rows + " ROWS IN " + table);
        }
    }

    // =========================================================
    // LIPSTICK
    // =========================================================

    private void updateLipstick(
            String table,
            int id,

            String name,
            String ageCategory,
            String brand,
            String productSeries,
            String productRange,
            String purpose,
            String volume,
            String ingredients,
            String imagePath,
            String cnCode,
            String precautions,
            String usageMethod,
            String description,
            String category,
            double price,

            String color,
            String finish,
            String matte,
            String longLasting

    ) throws Exception {

        String sql = """
            UPDATE %s
            SET
                name=?,
                age_category=?,
                brand=?,
                product_series=?,
                product_range=?,
                purpose=?,
                volume=?,
                ingredients=?,
                image_path=?,
                cn_code=?,
                precautions=?,
                usage_method=?,
                description=?,
                category=?,
                price=?,
                color=?,
                finish=?,
                matte=?,
                long_lasting=?
            WHERE id=?
        """.formatted(table);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, ageCategory);
            ps.setString(3, brand);
            ps.setString(4, productSeries);
            ps.setString(5, productRange);
            ps.setString(6, purpose);
            ps.setString(7, volume);
            ps.setString(8, ingredients);
            ps.setString(9, imagePath);
            ps.setString(10, cnCode);
            ps.setString(11, precautions);
            ps.setString(12, usageMethod);
            ps.setString(13, description);
            ps.setString(14, category);
            ps.setDouble(15, price);
            ps.setString(16, color);
            ps.setString(17, finish);
            ps.setString(18, matte);
            ps.setString(19, longLasting);
            ps.setInt(20, id);

            int rows = ps.executeUpdate();

            System.out.println("✔ UPDATED " + rows + " ROWS IN " + table);
        }
    }

    // =========================================================
    // BLUSH
    // =========================================================

    private void updateBlush(
            String table,
            int id,

            String name,
            String ageCategory,
            String brand,
            String productSeries,
            String productRange,
            String purpose,
            String volume,
            String ingredients,
            String imagePath,
            String cnCode,
            String precautions,
            String usageMethod,
            String description,
            String category,
            double price,

            String color,
            String powderOrCream,
            String shimmer

    ) throws Exception {

        String sql = """
            UPDATE %s
            SET
                name=?,
                age_category=?,
                brand=?,
                product_series=?,
                product_range=?,
                purpose=?,
                volume=?,
                ingredients=?,
                image_path=?,
                cn_code=?,
                precautions=?,
                usage_method=?,
                description=?,
                category=?,
                price=?,
                color=?,
                powder_or_cream=?,
                shimmer=?
            WHERE id=?
        """.formatted(table);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, ageCategory);
            ps.setString(3, brand);
            ps.setString(4, productSeries);
            ps.setString(5, productRange);
            ps.setString(6, purpose);
            ps.setString(7, volume);
            ps.setString(8, ingredients);
            ps.setString(9, imagePath);
            ps.setString(10, cnCode);
            ps.setString(11, precautions);
            ps.setString(12, usageMethod);
            ps.setString(13, description);
            ps.setString(14, category);
            ps.setDouble(15, price);
            ps.setString(16, color);
            ps.setString(17, powderOrCream);
            ps.setString(18, shimmer);
            ps.setInt(19, id);

            int rows = ps.executeUpdate();

            System.out.println("✔ UPDATED " + rows + " ROWS IN " + table);
        }
    }

    // ================= DELETE =================

    public void deleteProduct(String table, int id) throws Exception {

        validateTable(table);

        String sql = "DELETE FROM " + table + " WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            System.out.println("✔ DELETED " + rows + " ROWS FROM " + table);
        }
    }
}