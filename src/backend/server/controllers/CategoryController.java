package backend.server.controllers;

import backend.design_patterns.composite.*;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CategoryController implements HttpHandler {

    private final CategoryProductService service =
            new CategoryProductService();

    private final ProductCategory root =
            CompositeFactory.buildTree();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();

        String category = null;

        if (query != null) {
            for (String p : query.split("&")) {
                String[] kv = p.split("=");
                if (kv[0].equals("category")) {
                    category = kv[1];
                }
            }
        }

        if (category == null) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        ProductLeaf leaf = findLeaf(root, category);

        if (leaf == null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        List<String> products =
                service.getProducts(leaf.getTableName());

        String json = "[" + String.join(",", products) + "]";

        exchange.getResponseHeaders().add(
                "Content-Type",
                "application/json"
        );

        exchange.sendResponseHeaders(200, json.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private ProductLeaf findLeaf(ProductComponent node, String name) {

        if (!node.isComposite()) {

            if (node.getName().equalsIgnoreCase(name)) {
                return (ProductLeaf) node;
            }

            return null;
        }

        for (ProductComponent child : node.getChildren()) {

            ProductLeaf result = findLeaf(child, name);

            if (result != null) return result;
        }

        return null;
    }
}