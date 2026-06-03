package backend.design_patterns.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import backend.design_patterns.singleton.DatabaseConnection;

public class ManualRegisterStrategy implements RegisterStrategy {

    @Override
    public String register(Map<String, String> data) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            String sql = """
                INSERT INTO Users
                (first_name, last_name, email, password, age, phone, gender, role)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, data.get("firstName"));
            ps.setString(2, data.get("lastName"));
            ps.setString(3, data.get("email"));
            ps.setString(4, data.get("password"));

            ps.setObject(5, data.get("age"));      // poate fi NULL
            ps.setString(6, data.get("phone"));
            ps.setString(7, data.get("gender"));

            ps.setString(8, "user");

            ps.executeUpdate();

            return "SUCCESS";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}