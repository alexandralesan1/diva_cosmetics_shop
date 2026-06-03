package backend.design_patterns.strategy;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class GoogleRegisterStrategy implements RegisterStrategy {

    @Override
    public String register(Map<String, String> data) {

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            // 🔥 FIX: check duplicate email first
            String checkSql = "SELECT COUNT(*) FROM Users WHERE email = ?";
            PreparedStatement check = conn.prepareStatement(checkSql);
            check.setString(1, data.get("email"));

            ResultSet rs = check.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                return "SUCCESS"; // already exists
            }

            // INSERT
            String sql = """
                INSERT INTO Users
                (first_name, last_name, email, password, age, phone, gender, role)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, data.getOrDefault("firstName", ""));
            ps.setString(2, data.getOrDefault("lastName", ""));
            ps.setString(3, data.get("email"));
            ps.setString(4, "GOOGLE_AUTH");

            ps.setObject(5, null);
            ps.setObject(6, null);
            ps.setObject(7, null);

            ps.setString(8, "user");

            ps.executeUpdate();

            return "SUCCESS";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}