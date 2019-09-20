package by.training.module1.service;

import by.training.module1.entity.Decor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class DecorServiceRepository implements DecorRepository<Decor> {
    private static final Logger LOGGER = LogManager.getLogger();
    private List<Decor> decors = new ArrayList<>();

    @Override
    public boolean add(Decor decor) {
        return decors.add(decor);
    }

    @Override
    public boolean remove(Decor decor) {
        return decors.remove(decor);
    }

    @Override
    public Double calculate(DecorCalculateSpecification<Decor> spec) throws ServiceException {
        Double result = 0d;
        if (spec == null) {
            LOGGER.error("NullPointerException");
            throw new ServiceException(new NullPointerException());
        }

        for (Decor decor : decors) {
            result += spec.getValue(decor);
        }
        return result;
    }

    @Override
    public List<Decor> find(DecorMatchSpecification<Decor> spec) throws ServiceException {
        if (spec == null) {
            LOGGER.error("NullPointerException");
            throw new ServiceException(new NullPointerException());
        }

        List<Decor> decors = new ArrayList<>();

        for (Decor decor : this.decors) {
            if (spec.match(decor)) {
                decors.add(decor);
            }
        }
        LOGGER.info("Find decors by specification");
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
    public List<Decor> getAll() {
        return decors;
    }
}
