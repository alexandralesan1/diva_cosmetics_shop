package backend.design_patterns.composite;

import java.util.Collections;
import java.util.List;

public class ProductLeaf implements ProductComponent {

    private final String name;
    private final String tableName;

    public ProductLeaf(String name, String tableName) {
        this.name = name;
        this.tableName = tableName;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public List<ProductComponent> getChildren() {
        return Collections.emptyList();
    }
}