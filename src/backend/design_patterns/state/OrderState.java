package backend.design_patterns.state;

public interface OrderState {
    boolean canEdit();
    boolean canSave();
    String getName();
}
