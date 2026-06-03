package backend.server.controllers;

import backend.design_patterns.strategy.GoogleRegisterStrategy;
import backend.design_patterns.strategy.RegisterStrategy;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GoogleCallbackController implements HttpHandler {

    private static final String CLIENT_ID =
            "" +
                    "";

    private static final String CLIENT_SECRET =
            "";

    private static final String REDIRECT_URI =
            "http://localhost:8080/google-callback";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String code = extractCode(query);

        if (code == null) {
            send(exchange, "ERROR: no code");
            return;
        }

        try {
            // 1. Exchange code -> access token
            String tokenResponse = requestAccessToken(code);
            String accessToken = extract(tokenResponse, "access_token");

            System.out.println("ACCESS TOKEN OK");

            // 2. Get user info (FIXED)
            String userInfo = requestUserInfo(accessToken);

            String email = extract(userInfo, "email");
            String firstName = extract(userInfo, "given_name");
            String lastName = extract(userInfo, "family_name");

            System.out.println("EMAIL = " + email);

            // 3. Build data for Strategy
            Map<String, String> data = new HashMap<>();
            data.put("type", "google");
            data.put("email", email);
            data.put("firstName", firstName);
            data.put("lastName", lastName);

            // 4. Strategy insert
            RegisterStrategy strategy = new GoogleRegisterStrategy();
            strategy.register(data);

            // 5. Redirect to dashboard
            exchange.getResponseHeaders().add(
                    "Location",
                    "http://localhost:8080/frontend/html/admin_main_page.html"
            );

            exchange.sendResponseHeaders(302, -1);
            exchange.close();

        } catch (Exception e) {
            e.printStackTrace();
            send(exchange, "ERROR GOOGLE CALLBACK");
        }
    }

    // ---------------- TOKEN ----------------

    private String requestAccessToken(String code) throws Exception {

        URL url = new URL("https://oauth2.googleapis.com/token");

        String params =
                "code=" + code +
                        "&client_id=" + CLIENT_ID +
                        "&client_secret=" + CLIENT_SECRET +
                        "&redirect_uri=" + REDIRECT_URI +
                        "&grant_type=authorization_code";

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        os.write(params.getBytes(StandardCharsets.UTF_8));
        os.close();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        System.out.println("TOKEN RESPONSE = " + response);
        return response.toString();
    }

    // ---------------- USER INFO (FIXED) ----------------

    private String requestUserInfo(String accessToken) throws Exception {

        URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept", "application/json");

        int status = conn.getResponseCode();
        System.out.println("USERINFO STATUS = " + status);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        status >= 200 && status < 300
                                ? conn.getInputStream()
                                : conn.getErrorStream()
                )
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        System.out.println("USER INFO = " + response);

        return response.toString();
    }

    // ---------------- HELPERS ----------------

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

    private String extractCode(String query) {
        if (query == null) return null;

        for (String part : query.split("&")) {
            if (part.startsWith("code=")) {
                return part.substring(5);
            }
        }
        return null;
    }

    private void send(HttpExchange ex, String msg) throws IOException {
        ex.sendResponseHeaders(200, msg.length());
        OutputStream os = ex.getResponseBody();
        os.write(msg.getBytes());
        os.close();
    }
}