package backend.design_patterns.command;

public class UpdateProductCommand implements Command {

    private final ProductService service;
    private final String table;

    private final int id;
    private final String name;
    private final String ageCategory;
    private final String brand;
    private final String productSeries;
    private final String productRange;
    private final String purpose;
    private final String volume;
    private final String ingredients;
    private final String imagePath;
    private final String cnCode;
    private final String precautions;
    private final String usageMethod;
    private final String description;
    private final String category;
    private final double price;

    private final String coverage;
    private final String skinType;
    private final String finish;
    private final String color;
    private final String waterproof;

    private final String volumeEffect;
    private final String brushType;

    private final String matte;
    private final String longLasting;

    private final String powderOrCream;
    private final String shimmer;

    public UpdateProductCommand(
            ProductService service,
            String table,
            int id,
            String name,
            String ageCategory,
            String brand,
            String productSeries,
            String productRange,
            String purpose,
            String volume,
            String ingredients,
            String imagePath,
            String cnCode,
            String precautions,
            String usageMethod,
            String description,
            String category,
            double price,
            String coverage,
            String skinType,
            String finish,
            String color,
            String waterproof,
            String volumeEffect,
            String brushType,
            String matte,
            String longLasting,
            String powderOrCream,
            String shimmer
    ) {
        this.service = service;
        this.table = table;
        this.id = id;
        this.name = name;
        this.ageCategory = ageCategory;
        this.brand = brand;
        this.productSeries = productSeries;
        this.productRange = productRange;
        this.purpose = purpose;
        this.volume = volume;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
        this.cnCode = cnCode;
        this.precautions = precautions;
        this.usageMethod = usageMethod;
        this.description = description;
        this.category = category;
        this.price = price;

        this.coverage = coverage;
        this.skinType = skinType;
        this.finish = finish;
        this.color = color;
        this.waterproof = waterproof;

        this.volumeEffect = volumeEffect;
        this.brushType = brushType;

        this.matte = matte;
        this.longLasting = longLasting;

        this.powderOrCream = powderOrCream;
        this.shimmer = shimmer;
    }

    @Override
    public void execute() throws Exception {
        service.updateProduct(
                table,
                id,
                name,
                ageCategory,
                brand,
                productSeries,
                productRange,
                purpose,
                volume,
                ingredients,
                imagePath,
                cnCode,
                precautions,
                usageMethod,
                description,
                category,
                price,
                coverage,
                skinType,
                finish,
                color,
                waterproof,
                volumeEffect,
                brushType,
                matte,
                longLasting,
                powderOrCream,
                shimmer
        );
    }
}