package backend.server.controllers;

import backend.design_patterns.template_method.AnalyticsService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AnalyticsController implements HttpHandler {

    private final AnalyticsService analyticsService =
            new AnalyticsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String json = analyticsService.getAnalyticsJson();

        exchange.getResponseHeaders().add(
                "Content-Type",
                "application/json"
        );

        exchange.sendResponseHeaders(
                200,
                json.getBytes(StandardCharsets.UTF_8).length
        );

        exchange.getResponseBody().write(
                json.getBytes(StandardCharsets.UTF_8)
        );

        exchange.getResponseBody().close();
    }
}