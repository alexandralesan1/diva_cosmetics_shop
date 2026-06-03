package backend.design_patterns.adapter;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter implements PaymentService {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    @Override
    public void saveOrder(String body) {

        String fullName = extract(body, "fullName");
        String phone = extract(body, "phone");
        String email = extract(body, "email");
        String address = extract(body, "address");
        double total = Double.parseDouble(extract(body, "total"));

        List<String> products = extractProducts(body);

        String productsStr = String.join(", ", products);

        saveToDatabase(fullName, phone, email, address, productsStr, total);
    }

    // =========================
    // JSON PARSER (ADAPTER PART)
    // =========================
    private String extract(String json, String key) {

        String pattern = "\"" + key + "\":";

        int start = json.indexOf(pattern);
        if (start == -1) return "";

        start += pattern.length();

        char first = json.charAt(start);

        if (first == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return json.substring(start, end).replace("}", "").trim();
        }
    }

    private List<String> extractProducts(String json) {

        List<String> products = new ArrayList<>();

        String key = "\"products\":[";

        int start = json.indexOf(key);
        if (start == -1) return products;

        start += key.length();
        int end = json.indexOf("]", start);

        String array = json.substring(start, end);

        String[] items = array.split("\\{");

        for (String item : items) {
            if (item.contains("name")) {
                String name = extract(item, "name");
                if (!name.isEmpty()) {
                    products.add(name);
                }
            }
        }

        return products;
    }

    // =========================
    // DB SAVE
    // =========================
    private void saveToDatabase(
            String fullName,
            String phone,
            String email,
            String address,
            String products,
            double total
    ) {
        try {

            String sql =
                    "INSERT INTO orders(full_name, phone, email, address, products, total) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setString(5, products);
            ps.setDouble(6, total);

            ps.executeUpdate();

            System.out.println("ORDER SAVED!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}