package backend.design_patterns.factory_method;

public class MenCareProduct implements Product {

    private ProductData data;
    private ProductCareDao dao;

    public MenCareProduct(ProductData data, ProductCareDao dao) {
        this.data = data;
        this.dao = dao;
    }

    @Override
    public void save() {
        dao.insert(data);
    }
}
