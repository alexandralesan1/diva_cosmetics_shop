package backend.design_patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class CartSubscriber implements ProductSubscriber {

    private final List<ProductFullDTO> cart = new ArrayList<>();

    @Override
    public void update(ProductFullDTO product) {
        if (product == null) return;

        cart.add(product);
        System.out.println("🛒 Added to cart: " + product.getName());
    }

    public List<ProductFullDTO> getCart() {
        return new ArrayList<>(cart); // safe copy
    }

    public void clear() {
        cart.clear();
    }
}