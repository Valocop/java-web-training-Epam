package by.training.module1.service;

import by.training.module1.entity.Decor;

import java.util.*;

public class DecorServiceRepository implements DecorRepository<Decor> {
    private List<Decor> decors = new ArrayList<>();

    @Override
    public boolean addDecor(Decor decor) {
        return decors.add(decor);
    }

    @Override
    public boolean removeDecor(Decor decor) {
        return decors.remove(decor);
    }

    @Override
    public Number calculate(DecorCalculateSpecification<Decor> spec) throws ServiceException {
//        processing

        return null;
    }

    @Override
    public List<Decor> find(DecorMatchSpecification<Decor> spec) throws ServiceException {
        if (spec == null) {
            throw new ServiceException(new NullPointerException());
        }

        List<Decor> decors = new ArrayList<>();

        for (Decor decor : this.decors) {
            if (spec.match(decor)) {
                decors.add(decor);
            }
        }
        return decors;
    }

    @Override
    public List<Decor> sort(DecorSortSpecification<Decor> spec) throws ServiceException {
        if (spec == null) {
            throw new ServiceException(new NullPointerException());
        }

        decors.sort(spec.getSort());
        return decors;
    }

    @Override
    public List<Decor> getDecors() {
        return decors;
    }
}
