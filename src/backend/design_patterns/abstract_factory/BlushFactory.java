package backend.design_patterns.abstract_factory;

public class BlushFactory implements MakeupProductFactory {

    @Override
    public MakeupProduct create(ProductDataMakeUp data) {
        return new BlushProduct(data);
    }
}