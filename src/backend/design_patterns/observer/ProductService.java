package backend.design_patterns.observer;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ProductService {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    // ===== CAUTĂ ÎN TOATE TABELELE (fallback) =====
    public ProductFullDTO getProductById(int id) {

        String[] tables = {
                "dbo.products_accessories",
                "dbo.products_blush",
                "dbo.products_care",
                "dbo.products_concealer",
                "dbo.products_foundation",
                "dbo.products_lipstick",
                "dbo.products_mascara"
        };

        for (String table : tables) {
            ProductFullDTO p = getProductByIdFromTable(id, table);
            if (p != null) return p;
        }

        return null;
    }

    // ===== CAUTĂ DIRECT ÎN TABELUL SPECIFICAT =====
    public ProductFullDTO getProductByIdFromTable(int id, String tableName) {

        String fullTableName = tableName.startsWith("dbo.")
                ? tableName
                : "dbo." + tableName;

        try {
            String sql = "SELECT * FROM " + fullTableName + " WHERE id = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ProductFullDTO p = mapToDTO(rs);

                // ✅ Setăm source_table după mapare — folosit de Observer și Cart
                p.setSource_table(fullTableName);

                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===== MAPARE ResultSet → ProductFullDTO =====
    private ProductFullDTO mapToDTO(ResultSet rs) throws Exception {

        ProductFullDTO p = new ProductFullDTO();
        ResultSetMetaData meta = rs.getMetaData();

        // ===== COMMON =====
        if (hasColumn(meta, "id"))             p.setId(rs.getInt("id"));
        if (hasColumn(meta, "name"))           p.setName(rs.getString("name"));
        if (hasColumn(meta, "brand"))          p.setBrand(rs.getString("brand"));
        if (hasColumn(meta, "product_range"))  p.setProduct_range(rs.getString("product_range"));
        if (hasColumn(meta, "product_series")) p.setProduct_series(rs.getString("product_series"));
        if (hasColumn(meta, "purpose"))        p.setPurpose(rs.getString("purpose"));
        if (hasColumn(meta, "image_path"))     p.setImage_path(rs.getString("image_path"));
        if (hasColumn(meta, "cn_code"))        p.setCn_code(rs.getString("cn_code"));
        if (hasColumn(meta, "usage_method"))   p.setUsage_method(rs.getString("usage_method"));
        if (hasColumn(meta, "description"))    p.setDescription(rs.getString("description"));
        if (hasColumn(meta, "category"))       p.setCategory(rs.getString("category"));
        if (hasColumn(meta, "price"))          p.setPrice(rs.getDouble("price"));

        // ===== OPTIONAL =====
        if (hasColumn(meta, "quantity"))        p.setQuantity(rs.getString("quantity"));
        if (hasColumn(meta, "age_category"))    p.setAge_category(rs.getString("age_category"));
        if (hasColumn(meta, "volume"))          p.setVolume(rs.getString("volume"));
        if (hasColumn(meta, "ingredients"))     p.setIngredients(rs.getString("ingredients"));
        if (hasColumn(meta, "precautions"))     p.setPrecautions(rs.getString("precautions"));
        if (hasColumn(meta, "coverage"))        p.setCoverage(rs.getString("coverage"));
        if (hasColumn(meta, "skin_type"))       p.setSkin_type(rs.getString("skin_type"));
        if (hasColumn(meta, "finish"))          p.setFinish(rs.getString("finish"));
        if (hasColumn(meta, "color"))           p.setColor(rs.getString("color"));
        if (hasColumn(meta, "waterproof"))      p.setWaterproof(rs.getString("waterproof"));
        if (hasColumn(meta, "volume_effect"))   p.setVolume_effect(rs.getString("volume_effect"));
        if (hasColumn(meta, "brush_type"))      p.setBrush_type(rs.getString("brush_type"));
        if (hasColumn(meta, "matte"))           p.setMatte(rs.getString("matte"));
        if (hasColumn(meta, "long_lasting"))    p.setLong_lasting(rs.getString("long_lasting"));
        if (hasColumn(meta, "powder_or_cream")) p.setPowder_or_cream(rs.getString("powder_or_cream"));
        if (hasColumn(meta, "shimmer"))         p.setShimmer(rs.getString("shimmer"));

        return p;
    }

    private boolean hasColumn(ResultSetMetaData meta, String columnName) {

        try {
            int count = meta.getColumnCount();
            for (int i = 1; i <= count; i++) {
                if (meta.getColumnName(i).equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}