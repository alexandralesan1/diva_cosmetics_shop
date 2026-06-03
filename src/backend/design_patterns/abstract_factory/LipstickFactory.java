package backend.design_patterns.abstract_factory;

public class LipstickFactory implements MakeupProductFactory {

    @Override
    public MakeupProduct create(ProductDataMakeUp data) {
        return new LipstickProduct(data);
    }
}