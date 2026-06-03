package backend.design_patterns.abstract_factory;

public interface MakeupProductFactory {
    MakeupProduct create(ProductDataMakeUp data);
}