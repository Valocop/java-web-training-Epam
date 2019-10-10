package by.training.module2.repo;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WordRepository implements Repository<Entity> {
    private static final Logger LOG = LogManager.getLogger();
    private List<Entity> wordList = new ArrayList<>();

    @Override
    public long create(Entity model) {
        long wordId = model.getId();
        if (idIsPresent(wordId) && wordId != 0) {
            update(model);
            return wordId;
        } else {
            long newWordId = RepositoryControl.ID.getAndIncrement();
            model.setId(newWordId);
            wordList.add(model);
            LOG.info("Word id [" + newWordId + "] was added to repo.");
            return newWordId;
        }
    }

    @Override
    public void update(Entity model) {
        Optional<Entity> optional = getOptionalFromId(model.getId());
        if (optional.isPresent()) {
            remove(optional.get());
            wordList.add(model);
            LOG.info("Word id [" + model.getId() + "] was updated.");
        } else {
            IllegalStateException e =
                    new IllegalStateException("Word id [" + model.getId() + "] was error for update.");
            LOG.error(e);
            throw e;
        }
    }

    @Override
    public void remove(Entity model) {
        Optional<Entity> optional = getOptionalFromId(model.getId());
        if (optional.isPresent()) {
            optional.ifPresent(entity -> {
                wordList.remove(entity);
                LOG.info("Word id [" + model.getId() + "] was removed from repo.");
            });
        } else {
            IllegalStateException e =
                    new IllegalStateException("Word id [" + model.getId() + "] was error for remove.");
            LOG.error(e);
            throw e;
        }
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return new ArrayList<>(wordList);
    }

    @Override
    public Entity read(long id, EntityType type) {
        if (type != EntityType.WORD) {
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
        if (type != EntityType.WORD) {
            IllegalArgumentException e = new IllegalArgumentException("Incorrect type");
            LOG.error(e);
            throw e;
        }
        ArrayList<Entity> list = new ArrayList<>();
        for (Entity entity : wordList) {
            if (spec.match(entity)) {
                list.add(entity);
                LOG.info("Spec match word id [" + entity.getId() + "]");
            }
        }
        return list;
    }

    private Optional<Entity> getOptionalFromId(long id) {
        return wordList.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }

    private boolean idIsPresent(long id) {
        return getOptionalFromId(id).isPresent();
    }
}
