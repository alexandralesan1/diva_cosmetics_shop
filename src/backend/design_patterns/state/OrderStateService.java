package backend.design_patterns.state;

import backend.design_patterns.singleton.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderStateService {

    private final Connection connection =
            DatabaseConnection.getInstance().getConnection();

    public void updateOrder(
            int id,
            String fullName,
            String phone,
            String email,
            String address,
            String products,
            double total,
            String status
    ) {

        // 🔥 STATE DECISION (fara factory)
        OrderState state;

        if ("delivered".equalsIgnoreCase(status)) {
            state = new DeliveredState();
        } else {
            state = new PendingState();
        }

        OrderContext context = new OrderContext(state);

        // 🔥 STATE CONTROLEAZA LOGICA
        if (!context.canSave()) {
            throw new RuntimeException("Save not allowed in this state");
        }

        try {
            String sql =
                    "UPDATE orders SET full_name=?, phone=?, email=?, address=?, products=?, total=?, status=? WHERE id=?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setString(5, products);
            ps.setDouble(6, total);
            ps.setString(7, context.getStateName());
            ps.setInt(8, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}