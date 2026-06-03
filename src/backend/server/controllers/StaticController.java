package backend.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;

public class StaticController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String projectRoot = System.getProperty("user.dir");
        String path = exchange.getRequestURI().getPath();

        // ✔ FIX: homepage redirect
        if (path.equals("/")) {
            path = "/frontend/html/main_page.html";
        }

        File file = new File(projectRoot + "/src" + path);

        if (!file.exists()) {
            String response = "NOT FOUND: " + path;
            exchange.sendResponseHeaders(404, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
            return;
        }

        byte[] data = Files.readAllBytes(file.toPath());

        // ✔ FIX: correct MIME types
        if (path.endsWith(".css")) {
            exchange.getResponseHeaders().add("Content-Type", "text/css");
        } else if (path.endsWith(".js")) {
            exchange.getResponseHeaders().add("Content-Type", "application/javascript");
        } else if (path.endsWith(".html")) {
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        }

        exchange.sendResponseHeaders(200, data.length);

        OutputStream os = exchange.getResponseBody();
        os.write(data);
        os.close();
    }
}