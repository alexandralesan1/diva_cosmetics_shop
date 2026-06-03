package backend.services;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {

    public String login(String email, String password) {

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            if (conn == null) {
                return "DB_ERROR";
            }

            // 1. căutăm user după email
            String sql = "SELECT password FROM Users WHERE email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            // 2. dacă nu există email
            if (!rs.next()) {
                return "USER_NOT_FOUND";
            }

            // 3. verificăm parola
            String dbPassword = rs.getString("password");

            if (!dbPassword.equals(password)) {
                return "WRONG_PASSWORD";
            }

            // 4. succes
            return "SUCCESS";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}