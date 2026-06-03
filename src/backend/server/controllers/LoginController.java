package backend.server.controllers;

import backend.services.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LoginController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equals(exchange.getRequestMethod())) {

            String body = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            String email = extract(body, "email");
            String password = extract(body, "password");

            // ✔ SERVICE (business logic)
            LoginService service = new LoginService();
            String result = service.login(email, password);

            // ✔ transformăm status în răspuns
            String response;

            switch (result) {

                case "SUCCESS":
                    response = "LOGIN SUCCESS";
                    break;

                case "USER_NOT_FOUND":
                    response = "USER NOT FOUND";
                    break;

                case "WRONG_PASSWORD":
                    response = "WRONG PASSWORD";
                    break;

                case "DB_ERROR":
                    response = "DATABASE ERROR";
                    break;

                default:
                    response = "ERROR";
                    break;
            }

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private String extract(String json, String key) {
        try {
            String pattern = "\"" + key + "\":\"";
            int start = json.indexOf(pattern) + pattern.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "";
        }
    }
}