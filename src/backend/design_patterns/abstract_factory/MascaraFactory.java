package backend.design_patterns.abstract_factory;

public class MascaraFactory implements MakeupProductFactory {

    @Override
    public MakeupProduct create(ProductDataMakeUp data) {
        return new MascaraProduct(data);
    }
}