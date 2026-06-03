package backend.design_patterns.factory_method;

public class WomenCareProductFactory extends CareProductFactory {

    public WomenCareProductFactory(ProductData data, ProductCareDao dao) {
        super(data, dao);
    }

    @Override
    public Product createProduct() {
        return new WomenCareProduct(data, dao);
    }
}