package backend.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;

public class StaticImageController implements HttpHandler {

    private final String BASE_PATH = "C:/Users/alexa/Desktop/poze_tmppp/";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String uri = exchange.getRequestURI().getPath();
        String fileName = uri.replace("/images/", "");

        File file = new File(BASE_PATH + fileName);

        if (!file.exists()) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        byte[] bytes = Files.readAllBytes(file.toPath());

        exchange.getResponseHeaders().add("Content-Type", "image/jpeg");
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}