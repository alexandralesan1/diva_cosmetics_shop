package backend.design_patterns.composite;

import java.util.List;

public interface ProductComponent {

    String getName();

    List<ProductComponent> getChildren();

    default boolean isComposite() {
        return false;
    }
}