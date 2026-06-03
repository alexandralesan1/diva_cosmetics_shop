package backend.design_patterns.proxy;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceProxy {

    private final ProductService service =
            new ProductService();

    public List<String> getProducts(
            String category,
            Double min,
            Double max
    ) {

        List<String> products =
                service.getProducts(category);

        List<String> filtered = new ArrayList<>();

        for (String json : products) {

            double price = extractPrice(json);

            if ((min == null || price >= min) &&
                    (max == null || price <= max)) {

                filtered.add(json);
            }
        }

        return filtered;
    }

    private double extractPrice(String json) {

        try {
            String key = "\"price\":";
            int start = json.indexOf(key) + key.length();
            String sub = json.substring(start);
            String number = sub.split("[,}]")[0].trim();
            return Double.parseDouble(number);
        } catch (Exception e) {
            return 0;
        }
    }
}