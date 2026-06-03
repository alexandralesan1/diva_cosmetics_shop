package backend.design_patterns.template_method;

import java.sql.Connection;

public class StatusAnalytics extends AnalyticsTemplate {

    private final Connection connection;
    private final String status;

    public StatusAnalytics(Connection connection, String status) {
        this.connection = connection;
        this.status = status;
    }

    @Override
    protected int calculate() {

        try {

            String sql =
                    "SELECT COUNT(*) FROM orders WHERE status=?";

            var ps = connection.prepareStatement(sql);
            ps.setString(1, status);

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