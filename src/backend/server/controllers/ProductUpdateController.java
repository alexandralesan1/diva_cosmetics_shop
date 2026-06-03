package backend.server.controllers;

import backend.design_patterns.command.ProductService;
import backend.design_patterns.singleton.DatabaseConnection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ProductUpdateController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            String body = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            Map<String, String> parsedData = JsonParser.parse(body);

            ProductService service =
                    new ProductService(DatabaseConnection.getInstance().getConnection());

            String table = parsedData.get("table");
            int id = Integer.parseInt(parsedData.get("id"));

            System.out.println("✏ UPDATE REQUEST => " + table + " | ID = " + id);

            switch (table) {

                // ================= FOUNDATION / CONCEALER =================
                case "products_foundation":
                case "products_concealer":

                    System.out.println("➡ UPDATE FOUNDATION / CONCEALER");

                    service.updateProduct(
                            table,
                            id,

                            parsedData.get("name"),
                            parsedData.get("ageCategory"),
                            parsedData.get("brand"),
                            parsedData.get("productSeries"),
                            parsedData.get("productRange"),
                            parsedData.get("purpose"),
                            parsedData.get("volume"),
                            parsedData.get("ingredients"),
                            parsedData.get("imagePath"),
                            parsedData.get("cnCode"),
                            parsedData.get("precautions"),
                            parsedData.get("usageMethod"),
                            parsedData.get("description"),
                            parsedData.get("category"),
                            parsePrice(parsedData),

                            safe(parsedData, "coverage"),
                            safe(parsedData, "skinType"),
                            safe(parsedData, "finish"),
                            safe(parsedData, "color"),
                            safe(parsedData, "waterproof"),

                            "", // volumeEffect
                            "", // brushType
                            "", // matte
                            "", // longLasting
                            "", // powderOrCream
                            ""  // shimmer
                    );
                    break;

                // ================= MASCARA =================
                case "products_mascara":

                    System.out.println("➡ UPDATE MASCARA");

                    service.updateProduct(
                            table,
                            id,

                            parsedData.get("name"),
                            parsedData.get("ageCategory"),
                            parsedData.get("brand"),
                            parsedData.get("productSeries"),
                            parsedData.get("productRange"),
                            parsedData.get("purpose"),
                            parsedData.get("volume"),
                            parsedData.get("ingredients"),
                            parsedData.get("imagePath"),
                            parsedData.get("cnCode"),
                            parsedData.get("precautions"),
                            parsedData.get("usageMethod"),
                            parsedData.get("description"),
                            parsedData.get("category"),
                            parsePrice(parsedData),

                            "", // coverage
                            "", // skinType
                            "", // finish
                            "", // color
                            safe(parsedData, "waterproof"),

                            safe(parsedData, "volumeEffect"),
                            safe(parsedData, "brushType"),

                            "", // matte
                            "", // longLasting
                            "", // powderOrCream
                            ""  // shimmer
                    );
                    break;

                // ================= LIPSTICK =================
                case "products_lipstick":

                    System.out.println("➡ UPDATE LIPSTICK");

                    service.updateProduct(
                            table,
                            id,

                            parsedData.get("name"),
                            parsedData.get("ageCategory"),
                            parsedData.get("brand"),
                            parsedData.get("productSeries"),
                            parsedData.get("productRange"),
                            parsedData.get("purpose"),
                            parsedData.get("volume"),
                            parsedData.get("ingredients"),
                            parsedData.get("imagePath"),
                            parsedData.get("cnCode"),
                            parsedData.get("precautions"),
                            parsedData.get("usageMethod"),
                            parsedData.get("description"),
                            parsedData.get("category"),
                            parsePrice(parsedData),

                            "", // coverage
                            "", // skinType

                            safe(parsedData, "finish"),
                            safe(parsedData, "color"),

                            "", // waterproof
                            "", // volumeEffect
                            "", // brushType

                            safe(parsedData, "matte"),
                            safe(parsedData, "longLasting"),

                            "", // powderOrCream
                            ""  // shimmer
                    );
                    break;

                // ================= BLUSH =================
                case "products_blush":

                    System.out.println("➡ UPDATE BLUSH");

                    service.updateProduct(
                            table,
                            id,

                            parsedData.get("name"),
                            parsedData.get("ageCategory"),
                            parsedData.get("brand"),
                            parsedData.get("productSeries"),
                            parsedData.get("productRange"),
                            parsedData.get("purpose"),
                            parsedData.get("volume"),
                            parsedData.get("ingredients"),
                            parsedData.get("imagePath"),
                            parsedData.get("cnCode"),
                            parsedData.get("precautions"),
                            parsedData.get("usageMethod"),
                            parsedData.get("description"),
                            parsedData.get("category"),
                            parsePrice(parsedData),

                            "", // coverage
                            "", // skinType
                            "", // finish

                            safe(parsedData, "color"),

                            "", // waterproof
                            "", // volumeEffect
                            "", // brushType
                            "", // matte
                            "", // longLasting

                            safe(parsedData, "powderOrCream"),
                            safe(parsedData, "shimmer")
                    );
                    break;

                default:
                    throw new RuntimeException("Unknown table: " + table);
            }

            System.out.println("✔ UPDATE SUCCESS => ID = " + id);

            respond(exchange, 200, "UPDATED");

        } catch (Exception e) {

            System.out.println("❌ UPDATE ERROR");
            e.printStackTrace();

            respond(exchange, 500, e.getMessage());
        }
    }

    private String safe(Map<String, String> parsedData, String key) {

        String value = parsedData.get(key);

        if (value == null || value.equals("undefined")) {
            return "";
        }

        return value;
    }

    private double parsePrice(Map<String, String> parsedData) {

        String value = parsedData.get("price");

        if (value == null || value.isEmpty()) {
            return 0.0;
        }

        return Double.parseDouble(value);
    }

    private void respond(HttpExchange exchange, int code, String message)
            throws IOException {

        exchange.sendResponseHeaders(code, message.getBytes().length);

        exchange.getResponseBody().write(message.getBytes());

        exchange.getResponseBody().close();
    }
}