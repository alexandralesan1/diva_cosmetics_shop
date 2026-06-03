package backend.design_patterns.facade;

import backend.design_patterns.singleton.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;

public class ProductFacade {

    private final ProductRepository repo;

    public ProductFacade() {
        this.repo = new ProductRepository(
                DatabaseConnection.getInstance().getConnection()
        );
    }

    public List<ProductDTO> getAllProducts() {

        List<ProductDTO> products = new ArrayList<>();

        products.addAll(repo.getFromTable("products_accessories"));
        products.addAll(repo.getFromTable("products_foundation"));
        products.addAll(repo.getFromTable("products_concealer"));
        products.addAll(repo.getFromTable("products_mascara"));
        products.addAll(repo.getFromTable("products_lipstick"));
        products.addAll(repo.getFromTable("products_blush"));
        products.addAll(repo.getFromTable("products_care"));

        return products;
    }
}