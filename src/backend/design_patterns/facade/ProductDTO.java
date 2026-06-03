package backend.design_patterns.facade;

public class ProductDTO {
    private String sourceTable;
    private int id;
    private String name;
    private String brand;
    private double price;
    private String image_path;

    public ProductDTO(String sourceTable, int id, String name, String brand, double price, String image_path) {
        this.sourceTable = sourceTable;
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.image_path = image_path;
    }
    public String getSourceTable() {
        return sourceTable;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public String getImage_path() { return image_path; }
}