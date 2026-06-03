package backend.design_patterns.builder;

public class ConcreteAccessoryBuilder implements AccessoryBuilder {

    private Accessory accessory;

    public ConcreteAccessoryBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        accessory = new Accessory();
    }

    @Override
    public void buildName(String name) {
        accessory.setName(name);
    }

    @Override
    public void buildBrand(String brand) {
        accessory.setBrand(brand);
    }

    @Override
    public void buildProductRange(String range) {
        accessory.setProductRange(range);
    }

    @Override
    public void buildProductSeries(String series) {
        accessory.setProductSeries(series);
    }

    @Override
    public void buildPurpose(String purpose) {
        accessory.setPurpose(purpose);
    }

    @Override
    public void buildQuantity(String quantity) {
        accessory.setQuantity(quantity);
    }

    @Override
    public void buildImagePath(String imagePath) {
        accessory.setImagePath(imagePath);
    }

    @Override
    public void buildCnCode(String cnCode) {
        accessory.setCnCode(cnCode);
    }

    @Override
    public void buildUsageMethod(String usageMethod) {
        accessory.setUsageMethod(usageMethod);
    }

    @Override
    public void buildDescription(String description) {
        accessory.setDescription(description);
    }

    @Override
    public void buildCategory(String category) {
        accessory.setCategory(category);
    }

    @Override
    public void buildPrice(double price) {
        accessory.setPrice(price);
    }

    @Override
    public Accessory getResult() {
        return accessory;
    }
}