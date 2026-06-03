package backend.server.controllers;

import backend.design_patterns.proxy.ProductServiceProxy;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProxyCategoryController implements HttpHandler {

    private final ProductServiceProxy proxy =
            new ProductServiceProxy();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();

        String category = null;
        Double min = null;
        Double max = null;

        if (query != null) {

            for (String p : query.split("&")) {

                String[] kv = p.split("=");

                if (kv.length < 2) continue;

                switch (kv[0]) {
                    case "category" -> category = kv[1];
                    case "min" -> min = Double.parseDouble(kv[1]);
                    case "max" -> max = Double.parseDouble(kv[1]);
                }
            }
        }

        List<String> products =
                proxy.getProducts(category, min, max);

        String json = "[" + String.join(",", products) + "]";

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders()
                .add("Content-Type", "application/json");

        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}