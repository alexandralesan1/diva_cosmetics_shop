package backend.design_patterns.mediator;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdersConcreteMediator implements OrdersMediator {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    @Override
    public List<OrderDTO> getAllOrders() {

        List<OrderDTO> orders = new ArrayList<>();

        try {

            String sql = "SELECT id, full_name, phone, email, address, products, total, created_at FROM orders";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrderDTO order = new OrderDTO(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("products"),
                        rs.getDouble("total"),
                        rs.getString("created_at")
                );

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
}
