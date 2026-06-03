package backend.design_patterns.observer;

public interface ProductSubscriber {
    void update(ProductFullDTO product);
}