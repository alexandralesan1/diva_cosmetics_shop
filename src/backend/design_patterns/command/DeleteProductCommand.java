package backend.design_patterns.command;

public class DeleteProductCommand implements Command {

    private final ProductService service;
    private final String table;
    private final int id;

    public DeleteProductCommand(ProductService service, String table, int id) {
        this.service = service;
        this.table = table;
        this.id = id;
    }

    @Override
    public void execute() throws Exception {
        service.deleteProduct(table, id);
    }
}