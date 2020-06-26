package by.training.module2.repo;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TextRepository implements Repository<Entity> {
    private static final Logger LOG = LogManager.getLogger();
    private List<Entity> textList = new ArrayList<>();

    @Override
    public long create(Entity model) {
        long wordId = model.getId();
        if (idIsPresent(wordId) && wordId != 0) {
            update(model);
            return wordId;
        } else {
            long newWordId = RepositoryControl.ID.getAndIncrement();
            model.setId(newWordId);
            textList.add(model);
            LOG.info("Text id [" + newWordId + "] was added to repo.");
            return newWordId;
        }
    }

    @Override
    public void update(Entity model) {
        Optional<Entity> optional = getOptionalFromId(model.getId());
        if (optional.isPresent()) {
            remove(optional.get());
            textList.add(model);
            LOG.info("Text id [" + model.getId() + "] was updated.");
        } else {
            IllegalStateException e =
                    new IllegalStateException("Text id [" + model.getId() + "] was error for update.");
            LOG.error(e);
            throw e;
        }
    }

    @Override
    public void remove(Entity model) {
        Optional<Entity> optional = getOptionalFromId(model.getId());
        if (optional.isPresent()) {
            optional.ifPresent(entity -> {
                textList.remove(entity);
                LOG.info("Text id [" + model.getId() + "] was removed from repo.");
            });
        } else {
            IllegalStateException e =
                    new IllegalStateException("Text id [" + model.getId() + "] was error for remove.");
            LOG.error(e);
            throw e;
        }
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return new ArrayList<>(textList);
    }

    @Override
    public Entity read(long id, EntityType type) {
        if (type != EntityType.TEXT) {
            IllegalArgumentException e = new IllegalArgumentException("Incorrect type");
            LOG.error(e);
            throw e;
        }
        Optional<Entity> optional = getOptionalFromId(id);
        if (!optional.isPresent()) {
            IllegalArgumentException e = new IllegalArgumentException("Incorrect id");
            LOG.error(e);
            throw e;
        }
        return optional.get();
    }

    @Override
    public List<Entity> find(FindSpecification<Entity> spec, EntityType type) {
        if (type != EntityType.TEXT) {
            IllegalArgumentException e = new IllegalArgumentException("Incorrect type");
            LOG.error(e);
            throw e;
        }
        ArrayList<Entity> list = new ArrayList<>();
        for (Entity entity : textList) {
            if (spec.match(entity)) {
                list.add(entity);
                LOG.info("Spec match text id [" + entity.getId() + "]");
            }
        }
        return list;
    }

    private Optional<Entity> getOptionalFromId(long id) {
        return textList.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }

    private boolean idIsPresent(long id) {
        return getOptionalFromId(id).isPresent();
    }
}
