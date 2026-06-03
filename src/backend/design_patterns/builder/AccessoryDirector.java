package backend.design_patterns.builder;

import java.util.Map;

public class AccessoryDirector {

    private AccessoryBuilder builder;

    public AccessoryDirector(AccessoryBuilder builder) {
        this.builder = builder;
    }

    public void construct(Map<String, String> data) {

        builder.reset();

        builder.buildName(data.get("name"));
        builder.buildBrand(data.get("brand"));
        builder.buildProductRange(data.get("product_range"));
        builder.buildProductSeries(data.get("product_series"));
        builder.buildPurpose(data.get("purpose"));
        builder.buildQuantity(data.get("quantity"));
        builder.buildImagePath(data.get("image_path"));
        builder.buildCnCode(data.get("cn_code"));
        builder.buildUsageMethod(data.get("usage_method"));
        builder.buildDescription(data.get("description"));
        builder.buildCategory(data.get("category"));
        builder.buildPrice(Double.parseDouble(data.getOrDefault("price", "0")));
    }

    public Accessory getProduct() {
        return builder.getResult();
    }
}