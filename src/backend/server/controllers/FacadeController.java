package backend.server.controllers;

import backend.design_patterns.facade.ProductFacade;
import backend.design_patterns.facade.ProductDTO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FacadeController implements HttpHandler {

    private final ProductFacade facade = new ProductFacade();

    @Override
    public void handle(HttpExchange exchange) {

        try {
            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            List<ProductDTO> products = facade.getAllProducts();

            String json = toJson(products);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes(StandardCharsets.UTF_8).length);

            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toJson(List<ProductDTO> products) {

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < products.size(); i++) {

            ProductDTO p = products.get(i);

            sb.append("{")
                    // ✅ sourceTable inclus — Observer știe exact din ce tabel vine produsul
                    .append("\"sourceTable\":\"").append(safe(p.getSourceTable())).append("\",")
                    .append("\"id\":").append(p.getId()).append(",")
                    .append("\"name\":\"").append(safe(p.getName())).append("\",")
                    .append("\"brand\":\"").append(safe(p.getBrand())).append("\",")
                    .append("\"price\":").append(p.getPrice()).append(",")
                    .append("\"image_path\":\"").append(safe(p.getImage_path())).append("\"")
                    .append("}");

            if (i < products.size() - 1) sb.append(",");
        }

        sb.append("]");
        return sb.toString();
    }

    // ✅ Escaped corect — protejează la caractere speciale în nume/brand
    private String safe(String value) {
        if (value == null) return "";
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}