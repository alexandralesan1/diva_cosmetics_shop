package backend.design_patterns.abstract_factory;

public class ConcealerFactory implements MakeupProductFactory {

    @Override
    public MakeupProduct create(ProductDataMakeUp data) {
        return new ConcealerProduct(data);
    }
}