package backend.design_patterns.facade;

import backend.design_patterns.facade.ProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final Connection connection;

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    public List<ProductDTO> getFromTable(String tableName) {

        List<ProductDTO> list = new ArrayList<>();

        String sql = "SELECT id, name, brand, price, image_path FROM " + tableName;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ProductDTO(
                        tableName,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("image_path")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}