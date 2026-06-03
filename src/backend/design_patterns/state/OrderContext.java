package backend.design_patterns.state;

public class OrderContext {

    private OrderState state;

    public OrderContext(OrderState state) {
        this.state = state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public boolean canEdit() {
        return state.canEdit();
    }

    public boolean canSave() {
        return state.canSave();
    }

    public String getStateName() {
        return state.getName();
    }
}