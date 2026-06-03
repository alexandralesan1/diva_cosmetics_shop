package backend.design_patterns.mediator;

public class OrderDTO {

    public int id;
    public String fullName;
    public String phone;
    public String email;
    public String address;
    public String products;
    public double total;
    public String createdAt;

    public OrderDTO(int id, String fullName, String phone, String email,
                    String address, String products, double total, String createdAt) {

        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.products = products;
        this.total = total;
        this.createdAt = createdAt;
    }
}
