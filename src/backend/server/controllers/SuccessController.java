package backend.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SuccessController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        byte[] html = Files.readAllBytes(
                Paths.get("frontend/html/success.html")
        );

        exchange.getResponseHeaders().add("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, html.length);
        exchange.getResponseBody().write(html);
        exchange.getResponseBody().close();
    }
}