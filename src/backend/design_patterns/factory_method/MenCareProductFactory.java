package backend.design_patterns.factory_method;

public class MenCareProductFactory extends CareProductFactory {

    public MenCareProductFactory(ProductData data, ProductCareDao dao) {
        super(data, dao);
    }

    @Override
    public Product createProduct() {
        return new MenCareProduct(data, dao);
    }
}