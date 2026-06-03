package backend.server.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class GoogleLoginController implements HttpHandler {

    private static final String CLIENT_ID =
            "809672191700-ncibmhdupt3n0ilv0mi3a0h83n0r1v8e.apps.googleusercontent.com";

    private static final String REDIRECT_URI =
            "http://localhost:8080/google-callback";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String url =
                "https://accounts.google.com/o/oauth2/v2/auth"
                        + "?client_id=" + CLIENT_ID
                        + "&redirect_uri=" + REDIRECT_URI
                        + "&response_type=code"
                        + "&scope=openid%20email%20profile";

        exchange.getResponseHeaders().add("Location", url);
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }
}