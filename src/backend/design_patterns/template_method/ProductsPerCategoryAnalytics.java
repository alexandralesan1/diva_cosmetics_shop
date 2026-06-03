package backend.design_patterns.template_method;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductsPerCategoryAnalytics extends AnalyticsTemplate {

    private final Connection connection;

    public ProductsPerCategoryAnalytics(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected int calculate() {

        try {

            String sql = """
                SELECT COUNT(*) as total
                FROM (
                    SELECT category FROM products_accessories
                    UNION ALL
                    SELECT category FROM products_foundation
                    UNION ALL
                    SELECT category FROM products_concealer
                    UNION ALL
                    SELECT category FROM products_mascara
                    UNION ALL
                    SELECT category FROM products_lipstick
                    UNION ALL
                    SELECT category FROM products_blush
                ) as all_products
            """;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}