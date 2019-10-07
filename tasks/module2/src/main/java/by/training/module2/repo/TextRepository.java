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
    public long add(Entity model) {
        long textId = model.getId();
        Optional<Entity> optional = fromId(textId);

        if (optional.isPresent() && textId != 0) {
            remove(optional.get());
            textList.add(model);
            LOG.info("Text id [" + textId + "] was updated.");
            return textId;
        } else {
            long newWordId = RepositoryManager.ID.getAndIncrement();
            model.setId(newWordId);
            textList.add(model);
            LOG.info("Text id [" + newWordId + "] was added to repo.");
            return newWordId;
        }
    }

    @Override
    public void remove(Entity model) {
        long textId = model.getId();
        Optional<Entity> optional = fromId(textId);
        optional.ifPresent(entity -> {
            textList.remove(entity);
            LOG.info("Text id [" + textId + "] was removed from repo.");
        });
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return new ArrayList<>(textList);
    }

    @Override
    public Entity getById(long id, EntityType type) {
        return fromId(id).get();
    }

    private Optional<Entity> fromId(long id) {
        return textList.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }
}
