package backend.server;

import backend.server.controllers.*;
import backend.design_patterns.singleton.DatabaseConnection;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.sql.Connection;

public class Server {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // 🔥 DB CONNECTION (SINGLETON)
        Connection connection =
                DatabaseConnection.getInstance().getConnection();

        // ===== STATIC / AUTH =====
        server.createContext("/frontend/", new StaticController());

        server.createContext("/login", new LoginController());
        server.createContext("/register", new RegisterApiController());

        server.createContext("/register.html", new RegisterController());
        server.createContext("/google-login", new GoogleLoginController());
        server.createContext("/google-callback", new GoogleCallbackController());

        // ===== MAIN PAGES =====


        server.createContext("/care-product", new CareProductController());
        server.createContext("/accessories-product", new AccessoryController());
        server.createContext("/makeup-product", new MakeupController());

        // ================= ADMIN PRODUCTS =================

        // LIST PRODUCTS
        server.createContext(
                "/admin/products",
                new AdminProductsController(connection)
        );

        server.createContext("/admin/products/update", new ProductUpdateController());
        server.createContext("/images", new StaticImageController());
        server.createContext("/admin/products/delete", new ProductDeleteController());
        server.createContext("/products", new FacadeController());
        server.createContext("/observer/publish", new ProductObserverController());

        server.createContext(
                "/observer/product",
                new ProductObserverController()
        );

        server.createContext(
                "/observer/add-to-cart",
                new AddToCartController()
        );

        server.createContext(
                "/cart",
                new CartController()
        );

        server.createContext("/payment", new PaymentPageController());
        server.createContext("/admin/orders", new AdminOrdersController());
        server.createContext("/admin/orders/update", new AdminOrdersUpdateController());
        server.createContext("/admin/analytics", new AnalyticsController());
        server.createContext("/category-products", new CategoryController());
        server.createContext("/proxy/category", new ProxyCategoryController());
        server.createContext("/", new StaticController());
        server.setExecutor(null);
        server.start();

        System.out.println("Server running on http://localhost:8080");
    }
}