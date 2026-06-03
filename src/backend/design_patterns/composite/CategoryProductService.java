package backend.design_patterns.composite;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryProductService {

    private final Connection connection =
            DatabaseConnection
                    .getInstance()
                    .getConnection();

    public List<String> getProducts(
            String tableName
    ) {

        List<String> result =
                new ArrayList<>();

        try {

            String sql =
                    "SELECT * FROM "
                            + tableName;

            PreparedStatement stmt =
                    connection.prepareStatement(sql);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                String json =
                        "{"
                                + "\"id\":" + rs.getInt("id") + ","
                                + "\"name\":\"" + rs.getString("name") + "\","
                                + "\"brand\":\"" + rs.getString("brand") + "\","
                                + "\"image_path\":\"" + rs.getString("image_path") + "\","
                                + "\"price\":" + rs.getDouble("price")
                                + "}";

                result.add(json);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}