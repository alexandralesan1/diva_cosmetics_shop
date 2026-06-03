package backend.design_patterns.template_method;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;

public class AnalyticsService {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    public String getAnalyticsJson() {

        int pending =
                new StatusAnalytics(connection, "pending")
                        .execute();

        int delivered =
                new StatusAnalytics(connection, "delivered")
                        .execute();

        int monthlyOrders =
                new MonthlyAnalytics(connection)
                        .execute();

        int productsTotal =
                new ProductsPerCategoryAnalytics(connection)
                        .execute();

        int brandCount =
                new BrandDistributionAnalytics(connection)
                        .execute();

        return "{"
                + "\"pending\":" + pending + ","
                + "\"delivered\":" + delivered + ","
                + "\"monthlyOrders\":" + monthlyOrders + ","
                + "\"productsTotal\":" + productsTotal + ","
                + "\"brandCount\":" + brandCount
                + "}";
    }
}