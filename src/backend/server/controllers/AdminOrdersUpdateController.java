package backend.server.controllers;

import backend.design_patterns.state.OrderStateService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class AdminOrdersUpdateController implements HttpHandler {

    private final OrderStateService service = new OrderStateService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        int id = Integer.parseInt(extract(body, "id"));
        String fullName = extract(body, "fullName");
        String phone = extract(body, "phone");
        String email = extract(body, "email");
        String address = extract(body, "address");
        String products = extract(body, "products");
        double total = Double.parseDouble(extract(body, "total"));
        String status = extract(body, "status");

        service.updateOrder(id, fullName, phone, email, address, products, total, status);

        exchange.sendResponseHeaders(200, -1);
        exchange.close();
    }

    private String extract(String body, String key) {
        for (String part : body.split("&")) {
            String[] kv = part.split("=");
            if (kv.length == 2 && kv[0].equals(key)) {
                return URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
            }
        }
        return "";
    }
}