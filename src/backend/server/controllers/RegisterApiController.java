package backend.server.controllers;

import backend.design_patterns.strategy.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterApiController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // CORS (important pentru browser)
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equals(exchange.getRequestMethod())) {

            String body = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            // JSON simplu parsing (fără librării)
            Map<String, String> data = parseJson(body);

            String type = data.getOrDefault("type", "manual");

            RegisterStrategy strategy;

            if ("google".equalsIgnoreCase(type)) {
                strategy = new GoogleRegisterStrategy();
            } else {
                strategy = new ManualRegisterStrategy();
            }

            String result = strategy.register(data);

            String response = switch (result) {
                case "SUCCESS" -> "REGISTER SUCCESS";
                default -> "REGISTER FAILED";
            };

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // JSON parser simplu (pentru proiectul tău)
    private Map<String, String> parseJson(String json) {

        Map<String, String> map = new HashMap<>();

        json = json.replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":");

            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }

        return map;
    }
}