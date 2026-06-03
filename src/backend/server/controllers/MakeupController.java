package backend.server.controllers;

import backend.design_patterns.abstract_factory.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MakeupController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> data = parse(body);

        System.out.println("RAW BODY: " + body);
        System.out.println("PARSED DATA: " + data);

        ProductDataMakeUp dto = new ProductDataMakeUp();

        dto.name = data.get("name");
        dto.ageCategory = data.get("ageCategory");
        dto.brand = data.get("brand");
        dto.productSeries = data.get("productSeries");
        dto.productRange = data.get("productRange");
        dto.purpose = data.get("purpose");
        dto.volume = data.get("volume");
        dto.ingredients = data.get("ingredients");
        dto.imagePath = data.get("imagePath"); // ⚠️ dacă nu trimiți din frontend va fi null
        dto.cnCode = data.get("cnCode");
        dto.precautions = data.get("precautions");
        dto.usageMethod = data.get("usageMethod");
        dto.description = data.get("description");
        dto.category = data.get("category");

        // 🔥 FIX IMPORTANT (evită crash)
        try {
            dto.price = Double.parseDouble(
                    data.getOrDefault("price", "0")
            );
        } catch (Exception e) {
            dto.price = 0;
        }

        dto.coverage = data.get("coverage");
        dto.skinType = data.get("skinType");
        dto.finish = data.get("finish");
        dto.color = data.get("color");
        dto.waterproof = data.get("waterproof");

        dto.volumeEffect = data.get("volumeEffect");
        dto.brushType = data.get("brushType");

        dto.matte = data.get("matte");
        dto.longLasting = data.get("longLasting");

        dto.powderOrCream = data.get("powderOrCream");
        dto.shimmer = data.get("shimmer");

        // 🔥 FIX IMPORTANT: type safety
        String type = data.get("type");

        if (type == null || type.isBlank()) {
            String response = "ERROR: Missing product type";
            exchange.sendResponseHeaders(400, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
            return;
        }

        MakeupProductFactory factory = switch (type) {
            case "foundation" -> new FoundationFactory();
            case "concealer" -> new ConcealerFactory();
            case "mascara" -> new MascaraFactory();
            case "lipstick" -> new LipstickFactory();
            case "blush" -> new BlushFactory();
            default -> throw new RuntimeException("Invalid type: " + type);
        };

        MakeupProduct product = factory.create(dto);

        try {
            product.save();
        } catch (Exception e) {
            e.printStackTrace();
            String response = "ERROR: Database insert failed";
            exchange.sendResponseHeaders(500, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
            return;
        }

        String response = "Product saved successfully";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    private Map<String, String> parse(String body) {
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