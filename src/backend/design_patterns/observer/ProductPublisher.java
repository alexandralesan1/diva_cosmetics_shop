package backend.design_patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class ProductPublisher {

    private final List<ProductSubscriber> subscribers = new ArrayList<>();
    private ProductFullDTO lastPublishedProduct;

    public void subscribe(ProductSubscriber s) {
        if (!subscribers.contains(s)) {
            subscribers.add(s);
        }
    }

    public void unsubscribe(ProductSubscriber s) {
        subscribers.remove(s);
    }

    public void publish(ProductFullDTO product) {
        this.lastPublishedProduct = product;
        notifySubscribers();
    }

    private void notifySubscribers() {
        for (ProductSubscriber s : subscribers) {
            s.update(lastPublishedProduct);
        }
    }
}