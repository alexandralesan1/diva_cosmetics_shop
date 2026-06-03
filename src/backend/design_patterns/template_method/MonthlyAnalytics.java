package backend.design_patterns.template_method;

import java.sql.Connection;

public class MonthlyAnalytics extends AnalyticsTemplate {

    private final Connection connection;

    public MonthlyAnalytics(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected int calculate() {

        try {

            String sql =
                    """
                    SELECT COUNT(*)
                    FROM orders
                    WHERE MONTH(created_at)=MONTH(GETDATE())
                    AND YEAR(created_at)=YEAR(GETDATE())
                    """;

            var ps = connection.prepareStatement(sql);

            var rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}