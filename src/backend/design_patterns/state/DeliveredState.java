package backend.design_patterns.state;

public class DeliveredState implements OrderState {

    @Override
    public boolean canEdit() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String getName() {
        return "delivered";
    }
}
