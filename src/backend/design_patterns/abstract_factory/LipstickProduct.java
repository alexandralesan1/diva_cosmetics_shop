package backend.design_patterns.abstract_factory;

import backend.design_patterns.singleton.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LipstickProduct implements MakeupProduct {

    private final ProductDataMakeUp data;

    public LipstickProduct(ProductDataMakeUp data) {
        this.data = data;
    }

    @Override
    public void save() {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            String sql = """
                INSERT INTO products_lipstick
                (name, age_category, brand, product_series, product_range, purpose,
                 volume, ingredients, image_path, cn_code, precautions, usage_method,
                 description, category, price,
                 color, finish, matte, long_lasting)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, data.name);
            ps.setString(2, data.ageCategory);
            ps.setString(3, data.brand);
            ps.setString(4, data.productSeries);
            ps.setString(5, data.productRange);
            ps.setString(6, data.purpose);
            ps.setString(7, data.volume);
            ps.setString(8, data.ingredients);
            ps.setString(9, data.imagePath);
            ps.setString(10, data.cnCode);
            ps.setString(11, data.precautions);
            ps.setString(12, data.usageMethod);
            ps.setString(13, data.description);
            ps.setString(14, data.category);
            ps.setDouble(15, data.price);

            ps.setString(16, data.color);
            ps.setString(17, data.finish);
            ps.setString(18, data.matte);
            ps.setString(19, data.longLasting);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}