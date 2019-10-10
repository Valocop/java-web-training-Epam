package by.training.module2.service;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.repo.FindSpecification;
import by.training.module2.repo.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class WordService implements Service<Entity> {
    private static final Logger LOG = LogManager.getLogger();
    private Repository<Entity> repository;

    public WordService(Repository<Entity> repository) {
        this.repository = repository;
    }

    @Override
    public long add(Entity model) {
        return repository.create(model);
    }

    @Override
    public void update(Entity model) {
        repository.update(model);
    }

    @Override
    public void remove(Entity model) {
        repository.remove(model);
    }

    @Override
    public List<Entity> sort(Comparator<Entity> comparator, EntityType type) {
        if (type != EntityType.WORD) {
            IllegalArgumentException e = new IllegalArgumentException("[" + type.name()
                    + "] Entity type is not WORD.");
            LOG.error(e);
            throw e;
        }
        List<Entity> words = repository.getAll(type);
        words.sort(comparator);
        return words;
    }

    @Override
    public List<Entity> find(FindSpecification<Entity> spec, EntityType type) {
        if (type != EntityType.WORD) {
            IllegalArgumentException e = new IllegalArgumentException("[" + type.name()
                    + "] Entity type is not WORD.");
            LOG.error(e);
            throw e;
        }
        return repository.find(spec, type);
    }

    @Override
    public Entity getById(long id, EntityType type) {
        if (type != EntityType.WORD) {
            IllegalArgumentException e = new IllegalArgumentException("[" + type.name()
                    + "] Entity type is not WORD.");
            LOG.error(e);
            throw e;
        }
        return repository.read(id, type);
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return repository.getAll(type);
    }
}
