package backend.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;

public class RegisterController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String filePath = System.getProperty("user.dir")
                + "/src/frontend/html/register.html";

        File file = new File(filePath);

        if (!file.exists()) {
            String response = "register.html NOT FOUND";
            exchange.sendResponseHeaders(404, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
            return;
        }

        byte[] response = Files.readAllBytes(file.toPath());

        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.length);

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }
}