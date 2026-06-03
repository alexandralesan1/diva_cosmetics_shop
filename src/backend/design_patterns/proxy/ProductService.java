package backend.design_patterns.proxy;

import backend.design_patterns.composite.CategoryProductService;

import java.util.List;

public class ProductService implements IProductService {

    private final CategoryProductService service =
            new CategoryProductService();

    @Override
    public List<String> getProducts(String category, Double min, Double max) {
        return service.getProducts(category);
    }
}