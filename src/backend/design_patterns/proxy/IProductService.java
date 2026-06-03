package backend.design_patterns.proxy;

import java.util.List;

public interface IProductService {
    List<String> getProducts(String category, Double min, Double max);
}