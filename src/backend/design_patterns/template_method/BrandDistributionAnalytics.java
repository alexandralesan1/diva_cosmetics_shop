package backend.design_patterns.template_method;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BrandDistributionAnalytics extends AnalyticsTemplate {

    private final Connection connection;

    public BrandDistributionAnalytics(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected int calculate() {

        try {

            String sql = """
                SELECT COUNT(DISTINCT brand) as total
                FROM (
                    SELECT brand FROM products_accessories
                    UNION ALL
                    SELECT brand FROM products_foundation
                    UNION ALL
                    SELECT brand FROM products_concealer
                    UNION ALL
                    SELECT brand FROM products_mascara
                    UNION ALL
                    SELECT brand FROM products_lipstick
                    UNION ALL
                    SELECT brand FROM products_blush
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