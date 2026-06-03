package backend.server.controllers;

import backend.design_patterns.adapter.PaymentService;
import backend.design_patterns.adapter.PaymentAdapter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PaymentPageController implements HttpHandler {

    private final PaymentService paymentService = new PaymentAdapter();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        paymentService.saveOrder(body);

        String response = "{\"status\":\"success\"}";

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);

        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}