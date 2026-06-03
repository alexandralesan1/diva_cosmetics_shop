package backend.design_patterns.abstract_factory;

public class FoundationFactory implements MakeupProductFactory {

    @Override
    public MakeupProduct create(ProductDataMakeUp data) {
        return new FoundationProduct(data);
    }
}