package by.training.module1.repo;

import by.training.module1.entity.Decor;
import by.training.module1.repo.MatchSpecification;

public class MatchTransparencySpec implements MatchSpecification<Decor> {
    private final int transparency;

    public MatchTransparencySpec(int transparency) {
        this.transparency = transparency;
    }

    @Override
    public boolean match(Decor entity) {
        return entity.getTransparency() == transparency;
    }
}
