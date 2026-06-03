package backend.design_patterns.composite;

public class CompositeFactory {

    public static ProductCategory buildTree() {

        ProductCategory root = new ProductCategory("Products");

        ProductCategory cosmetics = new ProductCategory("Cosmetics");

        cosmetics.add(new ProductLeaf("Foundation", "products_foundation"));
        cosmetics.add(new ProductLeaf("Concealer", "products_concealer"));
        cosmetics.add(new ProductLeaf("Mascara", "products_mascara"));
        cosmetics.add(new ProductLeaf("Lipstick", "products_lipstick"));
        cosmetics.add(new ProductLeaf("Blush", "products_blush"));

        root.add(cosmetics);

        root.add(new ProductLeaf("Care", "products_care"));
        root.add(new ProductLeaf("Accessories", "products_accessories"));

        return root;
    }
}