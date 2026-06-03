package backend.design_patterns.factory_method;

public abstract class CareProductFactory {

    protected final ProductData data;
    protected final ProductCareDao dao;

    public CareProductFactory(ProductData data, ProductCareDao dao) {
        this.data = data;
        this.dao = dao;
    }

    // factory method
    public abstract Product createProduct();

    public Product process() {
        Product product = createProduct();
        product.save();
        return product;
    }

    // 🔥 NEW: FACTORY SELECTOR (DECIZIA AICI)
    public static CareProductFactory getFactory(ProductData data, ProductCareDao dao) {

        String category = data.getCategory();

        if ("Women Care".equalsIgnoreCase(category)) {
            return new WomenCareProductFactory(data, dao);
        }

        return new MenCareProductFactory(data, dao);
    }
}