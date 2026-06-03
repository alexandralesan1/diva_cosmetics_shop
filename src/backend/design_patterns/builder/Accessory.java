package backend.design_patterns.builder;

public class Accessory {

    private String name;
    private String brand;
    private String productRange;
    private String productSeries;
    private String purpose;
    private String quantity;
    private String imagePath;
    private String cnCode;
    private String usageMethod;
    private String description;
    private String category;
    private double price;

    // SETTERS
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setProductRange(String productRange) { this.productRange = productRange; }
    public void setProductSeries(String productSeries) { this.productSeries = productSeries; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setCnCode(String cnCode) { this.cnCode = cnCode; }
    public void setUsageMethod(String usageMethod) { this.usageMethod = usageMethod; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }

    // GETTERS
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getProductRange() { return productRange; }
    public String getProductSeries() { return productSeries; }
    public String getPurpose() { return purpose; }
    public String getQuantity() { return quantity; }
    public String getImagePath() { return imagePath; }
    public String getCnCode() { return cnCode; }
    public String getUsageMethod() { return usageMethod; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
}