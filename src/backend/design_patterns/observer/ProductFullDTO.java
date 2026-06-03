package backend.design_patterns.observer;

public class ProductFullDTO {

    // ===== COMMON FIELDS (există în toate tabelele) =====
    private String source_table;
    private int id;
    private String name;
    private String brand;
    private String product_range;
    private String product_series;
    private String purpose;
    private String image_path;
    private String cn_code;
    private String usage_method;
    private String description;
    private String category;
    private double price;

    // ===== ACCESSORIES ONLY =====
    private String quantity;

    // ===== FOUNDATION / CONCEALER =====
    private String age_category;
    private String volume;
    private String ingredients;
    private String precautions;
    private String coverage;
    private String skin_type;
    private String finish;
    private String color;
    private String waterproof;

    // ===== MASCARA ONLY =====
    private String volume_effect;
    private String brush_type;

    // ===== LIPSTICK ONLY =====
    private String matte;
    private String long_lasting;

    // ===== BLUSH ONLY =====
    private String powder_or_cream;
    private String shimmer;

    public ProductFullDTO() {}
    public String getSource_table(){
        return source_table;
    }
    // ===== GETTERS =====
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getProduct_range() { return product_range; }
    public String getProduct_series() { return product_series; }
    public String getPurpose() { return purpose; }
    public String getImage_path() { return image_path; }
    public String getCn_code() { return cn_code; }
    public String getUsage_method() { return usage_method; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }

    public String getQuantity() { return quantity; }
    public String getAge_category() { return age_category; }
    public String getVolume() { return volume; }
    public String getIngredients() { return ingredients; }
    public String getPrecautions() { return precautions; }
    public String getCoverage() { return coverage; }
    public String getSkin_type() { return skin_type; }
    public String getFinish() { return finish; }
    public String getColor() { return color; }
    public String getWaterproof() { return waterproof; }

    public String getVolume_effect() { return volume_effect; }
    public String getBrush_type() { return brush_type; }

    public String getMatte() { return matte; }
    public String getLong_lasting() { return long_lasting; }

    public String getPowder_or_cream() { return powder_or_cream; }
    public String getShimmer() { return shimmer; }

    // ===== SETTERS =====
    public void setSource_table(String source_table){
        this.source_table = source_table;
    }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setProduct_range(String product_range) { this.product_range = product_range; }
    public void setProduct_series(String product_series) { this.product_series = product_series; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setImage_path(String image_path) { this.image_path = image_path; }
    public void setCn_code(String cn_code) { this.cn_code = cn_code; }
    public void setUsage_method(String usage_method) { this.usage_method = usage_method; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }

    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setAge_category(String age_category) { this.age_category = age_category; }
    public void setVolume(String volume) { this.volume = volume; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public void setPrecautions(String precautions) { this.precautions = precautions; }
    public void setCoverage(String coverage) { this.coverage = coverage; }
    public void setSkin_type(String skin_type) { this.skin_type = skin_type; }
    public void setFinish(String finish) { this.finish = finish; }
    public void setColor(String color) { this.color = color; }
    public void setWaterproof(String waterproof) { this.waterproof = waterproof; }

    public void setVolume_effect(String volume_effect) { this.volume_effect = volume_effect; }
    public void setBrush_type(String brush_type) { this.brush_type = brush_type; }

    public void setMatte(String matte) { this.matte = matte; }
    public void setLong_lasting(String long_lasting) { this.long_lasting = long_lasting; }

    public void setPowder_or_cream(String powder_or_cream) { this.powder_or_cream = powder_or_cream; }
    public void setShimmer(String shimmer) { this.shimmer = shimmer; }
}