package backend.server.controllers;

import backend.design_patterns.observer.ProductFullDTO;
import backend.design_patterns.observer.ProductService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ProductObserverController implements HttpHandler {

    private static final ProductService productService = new ProductService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String query = exchange.getRequestURI().getQuery();

        int id = extractId(query);

        if (id <= 0) {
            sendText(exchange, 400, "Invalid product id");
            return;
        }

        String table = extractTable(query);

        ProductFullDTO product;

        if (table != null && !table.isEmpty()) {
            // ✅ Observer caută direct în tabelul corect
            product = productService.getProductByIdFromTable(id, table);
        } else {
            // fallback: caută în toate tabelele
            product = productService.getProductById(id);
        }

        if (product == null) {
            sendText(exchange, 404, "Product not found");
            return;
        }

        String json = buildFullJson(product);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.getBytes(StandardCharsets.UTF_8).length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }

    private String buildFullJson(ProductFullDTO p) {

        return "{"
                // ✅ source_table inclus — frontend îl pasează la addToCart
                + "\"source_table\":\"" + safe(p.getSource_table()) + "\","
                + "\"id\":" + p.getId() + ","
                + "\"name\":\"" + safe(p.getName()) + "\","
                + "\"brand\":\"" + safe(p.getBrand()) + "\","
                + "\"product_range\":\"" + safe(p.getProduct_range()) + "\","
                + "\"product_series\":\"" + safe(p.getProduct_series()) + "\","
                + "\"purpose\":\"" + safe(p.getPurpose()) + "\","
                + "\"image_path\":\"" + safe(p.getImage_path()) + "\","
                + "\"cn_code\":\"" + safe(p.getCn_code()) + "\","
                + "\"usage_method\":\"" + safe(p.getUsage_method()) + "\","
                + "\"description\":\"" + safe(p.getDescription()) + "\","
                + "\"category\":\"" + safe(p.getCategory()) + "\","
                + "\"price\":" + p.getPrice() + ","
                + "\"quantity\":\"" + safe(p.getQuantity()) + "\","
                + "\"age_category\":\"" + safe(p.getAge_category()) + "\","
                + "\"volume\":\"" + safe(p.getVolume()) + "\","
                + "\"ingredients\":\"" + safe(p.getIngredients()) + "\","
                + "\"precautions\":\"" + safe(p.getPrecautions()) + "\","
                + "\"coverage\":\"" + safe(p.getCoverage()) + "\","
                + "\"skin_type\":\"" + safe(p.getSkin_type()) + "\","
                + "\"finish\":\"" + safe(p.getFinish()) + "\","
                + "\"color\":\"" + safe(p.getColor()) + "\","
                + "\"waterproof\":\"" + safe(p.getWaterproof()) + "\","
                + "\"volume_effect\":\"" + safe(p.getVolume_effect()) + "\","
                + "\"brush_type\":\"" + safe(p.getBrush_type()) + "\","
                + "\"matte\":\"" + safe(p.getMatte()) + "\","
                + "\"long_lasting\":\"" + safe(p.getLong_lasting()) + "\","
                + "\"powder_or_cream\":\"" + safe(p.getPowder_or_cream()) + "\","
                + "\"shimmer\":\"" + safe(p.getShimmer()) + "\""
                + "}";
    }

    private String safe(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void sendText(HttpExchange exchange, int code, String msg) throws IOException {
        exchange.sendResponseHeaders(code, msg.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(msg.getBytes(StandardCharsets.UTF_8));
        }
    }

    private int extractId(String query) {
        if (query == null) return -1;
        for (String param : query.split("&")) {
            String[] kv = param.split("=");
            if (kv.length == 2 && kv[0].equals("id")) {
                try { return Integer.parseInt(kv[1]); }
                catch (NumberFormatException e) { return -1; }
            }
        }
        return -1;
    }

    private String extractTable(String query) {
        if (query == null) return null;
        for (String param : query.split("&")) {
            String[] kv = param.split("=");
            if (kv.length == 2 && kv[0].equals("table")) {
                return kv[1];
            }
        }
        return null;
    }
}