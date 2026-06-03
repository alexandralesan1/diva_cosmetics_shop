package backend.server.controllers;

import backend.design_patterns.factory_method.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CareProductController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "Method not allowed");
            return;
        }

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> form = parseForm(body);

        ProductData data = new ProductData();

        data.setName(form.get("name"));
        data.setAgeCategory(form.get("age_category"));
        data.setBrand(form.get("brand"));
        data.setProductSeries(form.get("product_series"));
        data.setProductRange(form.get("product_range"));
        data.setPurpose(form.get("purpose"));
        data.setVolume(form.get("volume"));
        data.setIngredients(form.get("ingredients"));
        data.setPrecautions(form.get("precautions"));
        data.setCnCode(form.get("cn_code"));
        data.setDescription(form.get("description"));
        data.setUsageMethod(form.get("usage_method"));
        data.setCategory(form.get("category"));

        double price = 0;
        try {
            price = Double.parseDouble(form.get("price"));
        } catch (Exception ignored) {}

        data.setPrice(price);
        data.setImagePath(form.get("image_path"));

        ProductCareDao dao = new ProductCareDao();

        // 🔥 ONLY FACTORY USAGE (NO DECISION HERE)
        CareProductFactory factory =
                CareProductFactory.getFactory(data, dao);

        factory.process();

        send(exchange, 200, "Product inserted successfully");
    }

    private void send(HttpExchange exchange, int code, String response) throws IOException {
        exchange.sendResponseHeaders(code, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> parseForm(String body) {

        Map<String, String> map = new HashMap<>();

        for (String pair : body.split("&")) {
            String[] kv = pair.split("=");

            if (kv.length == 2) {
                map.put(
                        URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(kv[1], StandardCharsets.UTF_8)
                );
            }
        }

        return map;
    }
}