package backend.design_patterns.proxy;

import backend.design_patterns.composite.CategoryProductService;

import java.util.List;

public class ProductService {

    private final CategoryProductService service =
            new CategoryProductService();

    public List<String> getProducts(String category) {
        return service.getProducts(category);
    }
}