package backend.server.controllers;

import backend.design_patterns.builder.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AccessoryController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> form = parse(body);

        AccessoryBuilder builder = new ConcreteAccessoryBuilder();
        AccessoryDirector director = new AccessoryDirector(builder);

        director.construct(form);

        Accessory accessory = director.getProduct();

        AccessoryDao dao = new AccessoryDao();
        dao.insert(accessory);

        String response = "Accessory created successfully";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }

    private Map<String, String> parse(String body) {

        Map<String, String> map = new HashMap<>();

        for (String pair : body.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                map.put(kv[0], kv[1]);
            }
        }
        return map;
    }
}