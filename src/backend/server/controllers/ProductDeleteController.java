package backend.server.controllers;

import backend.design_patterns.command.*;
import backend.design_patterns.singleton.DatabaseConnection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDeleteController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            String query = exchange.getRequestURI().getQuery();

            if (query == null || query.isEmpty()) {
                respond(exchange, 400, "Missing query params");
                return;
            }

            Map<String, String> params = parseQuery(query);

            String table = params.get("table");
            int id = Integer.parseInt(params.get("id"));

            ProductService service =
                    new ProductService(DatabaseConnection.getInstance().getConnection());

            System.out.println("🗑 DELETE REQUEST => table=" + table + ", id=" + id);

            service.deleteProduct(table, id);

            System.out.println("✔ DELETE SUCCESS => 1 row expected for id=" + id);

            respond(exchange, 200, "DELETED");

        } catch (Exception e) {
            System.out.println("❌ DELETE ERROR");
            e.printStackTrace();
            respond(exchange, 500, e.getMessage());
        }
    }

    private Map<String, String> parseQuery(String query) {
        return java.util.Arrays.stream(query.split("&"))
                .map(p -> p.split("="))
                .filter(arr -> arr.length == 2)
                .collect(Collectors.toMap(
                        a -> URLDecoder.decode(a[0], StandardCharsets.UTF_8),
                        a -> URLDecoder.decode(a[1], StandardCharsets.UTF_8)
                ));
    }

    private void respond(HttpExchange ex, int code, String msg) throws IOException {
        ex.sendResponseHeaders(code, msg.getBytes().length);
        ex.getResponseBody().write(msg.getBytes());
        ex.getResponseBody().close();
    }
}