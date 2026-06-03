package backend.design_patterns.command;

public enum ProductTable {

    FOUNDATION("products_foundation"),
    MASCARA("products_mascara"),
    LIPSTICK("products_lipstick"),
    CONCEALER("products_concealer"),
    BLUSH("products_blush");

    private final String tableName;

    ProductTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public static ProductTable from(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("Table is null");
        }

        return switch (raw.toLowerCase()) {
            case "foundation", "products_foundation" -> FOUNDATION;
            case "mascara", "products_mascara" -> MASCARA;
            case "lipstick", "products_lipstick" -> LIPSTICK;
            case "concealer", "products_concealer" -> CONCEALER;
            case "blush", "products_blush" -> BLUSH;
            default -> throw new IllegalArgumentException("Invalid table: " + raw);
        };
    }
}