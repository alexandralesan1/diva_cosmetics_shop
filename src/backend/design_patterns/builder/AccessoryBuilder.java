package backend.design_patterns.builder;

public interface AccessoryBuilder {

    void reset();

    void buildName(String name);
    void buildBrand(String brand);
    void buildProductRange(String range);
    void buildProductSeries(String series);
    void buildPurpose(String purpose);
    void buildQuantity(String quantity);
    void buildImagePath(String imagePath);
    void buildCnCode(String cnCode);
    void buildUsageMethod(String usageMethod);
    void buildDescription(String description);
    void buildCategory(String category);
    void buildPrice(double price);

    Accessory getResult();
}