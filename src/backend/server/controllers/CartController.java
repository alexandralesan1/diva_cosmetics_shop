package backend.server.controllers;

import backend.design_patterns.observer.ProductFullDTO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CartController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        List<ProductFullDTO> cart =
                AddToCartController.getCart().getCart();

        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < cart.size(); i++) {

            ProductFullDTO p = cart.get(i);

            json.append("{")
                    .append("\"name\":\"").append(p.getName()).append("\",")
                    .append("\"price\":").append(p.getPrice())
                    .append("}");

            if (i < cart.size() - 1) json.append(",");
        }

        json.append("]");

        byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}