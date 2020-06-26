package by.training.module1.service;

import by.training.module1.entity.Decor;
import by.training.module1.repo.MatchSpecification;
import by.training.module1.repo.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DecorService implements Service<Decor> {
    private static final Logger LOGGER = LogManager.getLogger();
    private Repository<Decor> repo;

    public DecorService(Repository<Decor> repo) {
        this.repo = repo;
    }

    @Override
    public boolean add(Decor entity) {
        return repo.add(entity);
    }

    @Override
    public boolean remove(Decor entity) {
        return repo.remove(entity);
    }

    @Override
    public double calcDouble(CalculateDoubleSpecification<Decor> spec) {
        Double result = 0d;
        if (spec == null) {
            NullPointerException e = new NullPointerException("Specification must be not null, can't calculate.");
            LOGGER.error(e);
            throw e;
        } else {
            List<Decor> list = repo.getAll();
            for (Decor decor : list) {
                result += spec.getValue(decor);
            }
            LOGGER.info("Result of calculation is [" + result + "]");
            return result;
        }
    }

    @Override
    public List<Decor> sort(SortSpecification<Decor> spec) {
        if (spec == null) {
            NullPointerException e = new NullPointerException("Specification must be not null, can't sort.");
            LOGGER.error(e);
            throw e;
        } else {
            List<Decor> list = repo.getAll();
            list.sort(spec.getSort());
            LOGGER.info("List was sorted by [" + spec.getClass().getSimpleName());
            return list;
        }
    }

    @Override
    public List<Decor> getAll() {
        return repo.getAll();
    }

    @Override
    public List<Decor> find(MatchSpecification<Decor> spec) {
        if (spec == null) {
            NullPointerException e = new NullPointerException("Specification must be not null, can't find");
            LOGGER.error(e);
            throw e;
        } else {
            List<Decor> list = repo.find(spec);
            LOGGER.info("Result of find by " + spec.getClass().getSimpleName() + " [" + list.toString() + "]");
            return list;
        }
    }
}
