package by.training.module2.service;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.repo.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class TextService implements Service<Entity> {
    private static final Logger LOG = LogManager.getLogger();
    private Repository<Entity> repository;

    public TextService(Repository<Entity> repository) {
        this.repository = repository;
    }

    @Override
    public long add(Entity model) {
        return repository.add(model);
    }

    @Override
    public void remove(Entity model) {
        repository.remove(model);
    }

    @Override
    public List<Entity> sort(Comparator<Entity> comparator, EntityType type) {
        if (type != EntityType.TEXT) {
            IllegalArgumentException e = new IllegalArgumentException("[" + type.name()
                    + "] Entity type is not TEXT.");
            LOG.error(e);
            throw e;
        }
        List<Entity> texts = repository.getAll(type);
        texts.sort(comparator);
        return texts;
    }

    @Override
    public Entity getById(long id, EntityType type) {
        if (type != EntityType.TEXT) {
            IllegalArgumentException e = new IllegalArgumentException("[" + type.name()
                    + "] Entity type is not TEXT.");
            LOG.error(e);
            throw e;
        }
        return repository.getById(id, type);
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return repository.getAll(type);
    }
}
