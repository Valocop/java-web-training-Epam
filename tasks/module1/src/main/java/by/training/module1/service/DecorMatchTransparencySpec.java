package by.training.module1.service;

import by.training.module1.entity.Decor;

public class DecorMatchTransparencySpec implements DecorMatchSpecification<Decor> {
    private final int transparency;

    public DecorMatchTransparencySpec(int transparency) {
        this.transparency = transparency;
    }

    @Override
    public boolean match(Decor entity) {
        return entity.getTransparency() == transparency;
    }
}
