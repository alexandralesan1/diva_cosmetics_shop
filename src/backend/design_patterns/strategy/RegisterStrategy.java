package backend.design_patterns.strategy;

import java.util.Map;

public interface RegisterStrategy {
    String register(Map<String, String> data);
}