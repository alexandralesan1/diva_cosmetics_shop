package backend.design_patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory implements ProductComponent {

    private final String name;
    private final List<ProductComponent> children = new ArrayList<>();

    public ProductCategory(String name) {
        this.name = name;
    }

    public void add(ProductComponent component) {
        children.add(component);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ProductComponent> getChildren() {
        return children;
    }

    @Override
    public boolean isComposite() {
        return true;
    }
}