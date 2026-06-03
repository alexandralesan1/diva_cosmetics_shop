package backend.server.controllers;

import backend.design_patterns.observer.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AddToCartController implements HttpHandler {

    private static final ProductPublisher publisher = new ProductPublisher();
    private static final CartSubscriber cart = new CartSubscriber();
    private static final ProductService service = new ProductService();

    static {
        publisher.subscribe(cart);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String query = exchange.getRequestURI().getQuery();

        int id = extractId(query);

        if (id <= 0) {
            send(exchange, 400, "Invalid product id");
            return;
        }

        // ✅ Extragem tabelul din query — același tabel din care s-a deschis modalul
        String table = extractTable(query);

        ProductFullDTO product;

        if (table != null && !table.isEmpty()) {
            // ✅ Caută direct în tabelul corect — nu mai amestecă produse
            product = service.getProductByIdFromTable(id, table);
        } else {
            // fallback
            product = service.getProductById(id);
        }

        if (product == null) {
            send(exchange, 404, "Product not found");
            return;
        }

        // ✅ Observer declanșat cu produsul corect
        publisher.publish(product);

        send(exchange, 200, product.getName() + " adăugat în coș");
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

    // ✅ NOU: extrage tabelul din query string
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

    private void send(HttpExchange ex, int code, String msg) throws IOException {
        ex.sendResponseHeaders(code, msg.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(msg.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static CartSubscriber getCart() {
        return cart;
    }
}