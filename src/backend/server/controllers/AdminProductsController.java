package backend.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminProductsController implements HttpHandler {

    private final Connection connection;

    public AdminProductsController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String query = exchange.getRequestURI().getQuery();
        String type = getParam(query, "type");

        String sql = buildQuery(type);

        StringBuilder json = new StringBuilder();
        json.append("[");

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean first = true;

            while (rs.next()) {

                if (!first) json.append(",");
                first = false;

                json.append("{");

                // ===== COMMON FIELDS (ALL TABLES HAVE THEM) =====
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"name\":\"").append(escape(rs.getString("name"))).append("\",");
                json.append("\"age_category\":\"").append(escape(rs.getString("age_category"))).append("\",");
                json.append("\"brand\":\"").append(escape(rs.getString("brand"))).append("\",");
                json.append("\"product_series\":\"").append(escape(rs.getString("product_series"))).append("\",");
                json.append("\"product_range\":\"").append(escape(rs.getString("product_range"))).append("\",");
                json.append("\"purpose\":\"").append(escape(rs.getString("purpose"))).append("\",");
                json.append("\"volume\":\"").append(escape(rs.getString("volume"))).append("\",");
                json.append("\"ingredients\":\"").append(escape(rs.getString("ingredients"))).append("\",");
                json.append("\"image_path\":\"").append(escape(rs.getString("image_path"))).append("\",");
                json.append("\"cn_code\":\"").append(escape(rs.getString("cn_code"))).append("\",");
                json.append("\"precautions\":\"").append(escape(rs.getString("precautions"))).append("\",");
                json.append("\"usage_method\":\"").append(escape(rs.getString("usage_method"))).append("\",");
                json.append("\"description\":\"").append(escape(rs.getString("description"))).append("\",");
                json.append("\"category\":\"").append(escape(rs.getString("category"))).append("\",");
                json.append("\"price\":").append(rs.getBigDecimal("price"));

                // ===== EXTRA FIELDS (DEPENDENT ON TYPE) =====
                switch (type) {

                    case "foundation", "concealer" -> {
                        json.append(",");
                        json.append("\"coverage\":\"").append(escape(rs.getString("coverage"))).append("\",");
                        json.append("\"skin_type\":\"").append(escape(rs.getString("skin_type"))).append("\",");
                        json.append("\"finish\":\"").append(escape(rs.getString("finish"))).append("\",");
                        json.append("\"color\":\"").append(escape(rs.getString("color"))).append("\",");
                        json.append("\"waterproof\":\"").append(escape(rs.getString("waterproof"))).append("\"");
                    }

                    case "mascara" -> {
                        json.append(",");
                        json.append("\"volume_effect\":\"").append(escape(rs.getString("volume_effect"))).append("\",");
                        json.append("\"brush_type\":\"").append(escape(rs.getString("brush_type"))).append("\",");
                        json.append("\"waterproof\":\"").append(escape(rs.getString("waterproof"))).append("\"");
                    }

                    case "lipstick" -> {
                        json.append(",");
                        json.append("\"color\":\"").append(escape(rs.getString("color"))).append("\",");
                        json.append("\"finish\":\"").append(escape(rs.getString("finish"))).append("\",");
                        json.append("\"matte\":\"").append(escape(rs.getString("matte"))).append("\",");
                        json.append("\"long_lasting\":\"").append(escape(rs.getString("long_lasting"))).append("\"");
                    }

                    case "blush" -> {
                        json.append(",");
                        json.append("\"color\":\"").append(escape(rs.getString("color"))).append("\",");
                        json.append("\"powder_or_cream\":\"").append(escape(rs.getString("powder_or_cream"))).append("\",");
                        json.append("\"shimmer\":\"").append(escape(rs.getString("shimmer"))).append("\"");
                    }
                }

                json.append("}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");

        byte[] responseBytes = json.toString().getBytes();

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    private String buildQuery(String type) {

        return switch (type) {
            case "foundation" -> "SELECT * FROM products_foundation";
            case "concealer" -> "SELECT * FROM products_concealer";
            case "mascara" -> "SELECT * FROM products_mascara";
            case "lipstick" -> "SELECT * FROM products_lipstick";
            case "blush" -> "SELECT * FROM products_blush";
            default -> "SELECT * FROM products_foundation";
        };
    }

    private String getParam(String query, String key) {

        if (query == null) return null;

        for (String part : query.split("&")) {

            String[] kv = part.split("=");

            if (kv.length == 2 && kv[0].equals(key)) {
                return kv[1];
            }
        }

        return null;
    }

    private String escape(String value) {

        if (value == null) return "";

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "")
                .replace("\t", "\\t");
    }
}