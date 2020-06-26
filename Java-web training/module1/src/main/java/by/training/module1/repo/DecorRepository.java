package by.training.module1.repo;

import by.training.module1.entity.Decor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DecorRepository implements Repository<Decor> {
    private static final Logger LOGGER = LogManager.getLogger();
    private List<Decor> decors = new ArrayList<>();

    @Override
    public boolean add(Decor entity) {
        if (entity == null) {
            NullPointerException e = new NullPointerException("Entity is null, can't to add in repo.");
            LOGGER.error(e);
            throw e;
        } else {
            LOGGER.info("[" + entity.toString() + "] was add to repo.");
            return decors.add(entity);
        }
    }

    @Override
    public boolean remove(Decor entity) {
        if (entity == null) {
            NullPointerException e = new NullPointerException("Entity is null, can't to remove from repo.");
            LOGGER.error(e);
            throw e;
        } else {
            LOGGER.info("[" + entity.toString() + "] was remove from repo.");
            return decors.remove(entity);
        }
    }

    @Override
    public List<Decor> find(MatchSpecification<Decor> spec) {
        List<Decor> list = new ArrayList<>();

        if (spec == null) {
            NullPointerException e = new NullPointerException("Specification is null, can't to find Decor.");
            LOGGER.error(e);
            throw e;
        } else {
            for (Decor decor : decors) {
                if (spec.match(decor)) {
                    LOGGER.info("[" + decor.toString() + "] was found by spec.");
                    list.add(decor);
                }
            }
        }
        return list;
    }

    @Override
    public List<Decor> getAll() {
        return decors;
    }
}
