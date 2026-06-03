package backend.design_patterns.template_method;

public abstract class AnalyticsTemplate {

    public final int execute() {
        return calculate();
    }

    protected abstract int calculate();
}