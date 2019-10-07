package by.training.module2.repo;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParagraphRepository implements Repository<Entity> {
    private static final Logger LOG = LogManager.getLogger();
    private List<Entity> paragraphList = new ArrayList<>();

    @Override
    public long add(Entity model) {
        long paragraphId = model.getId();
        Optional<Entity> optional = fromId(paragraphId);

        if (optional.isPresent() && paragraphId != 0) {
            remove(optional.get());
            paragraphList.add(model);
            LOG.info("Paragraph id [" + paragraphId + "] was updated.");
            return paragraphId;
        } else {
            long newWordId = RepositoryManager.ID.getAndIncrement();
            model.setId(newWordId);
            paragraphList.add(model);
            LOG.info("Paragraph id [" + newWordId + "] was added to repo.");
            return newWordId;
        }
    }

    @Override
    public void remove(Entity model) {
        long paragraphId = model.getId();
        Optional<Entity> optional = fromId(paragraphId);
        optional.ifPresent(entity -> {
            paragraphList.remove(entity);
            LOG.info("Paragraph id [" + paragraphId + "] was removed from repo.");
        });
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return new ArrayList<>(paragraphList);
    }

    @Override
    public Entity getById(long id, EntityType type) {
        return fromId(id).get();
    }

    private Optional<Entity> fromId(long id) {
        return paragraphList.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }
}
