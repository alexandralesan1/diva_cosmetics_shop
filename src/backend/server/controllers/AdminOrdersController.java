package backend.server.controllers;
import backend.design_patterns.mediator.OrderDTO;
import backend.design_patterns.mediator.OrdersConcreteMediator;
import backend.design_patterns.mediator.OrdersMediator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AdminOrdersController implements HttpHandler {

    private final OrdersMediator mediator = new OrdersConcreteMediator();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        List<OrderDTO> orders = mediator.getAllOrders();

        String json = buildJson(orders);

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.getBytes(StandardCharsets.UTF_8).length);

        exchange.getResponseBody().write(json.getBytes(StandardCharsets.UTF_8));
        exchange.getResponseBody().close();
    }

    // 🔥 MANUAL JSON BUILDER
    private String buildJson(List<OrderDTO> orders) {

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < orders.size(); i++) {

            OrderDTO o = orders.get(i);

            sb.append("{")
                    .append("\"id\":").append(o.id).append(",")
                    .append("\"fullName\":\"").append(escape(o.fullName)).append("\",")
                    .append("\"phone\":\"").append(escape(o.phone)).append("\",")
                    .append("\"email\":\"").append(escape(o.email)).append("\",")
                    .append("\"address\":\"").append(escape(o.address)).append("\",")
                    .append("\"products\":\"").append(escape(o.products)).append("\",")
                    .append("\"total\":").append(o.total).append(",")
                    .append("\"createdAt\":\"").append(escape(o.createdAt)).append("\"")
                    .append("}");

            if (i < orders.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    // 🔥 SAFE STRING ESCAPE
    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\"", "\\\"");
    }
}