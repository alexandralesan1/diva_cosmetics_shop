package backend.design_patterns.state;

public class PendingState implements OrderState {

    @Override
    public boolean canEdit() {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String getName() {
        return "pending";
    }
}
