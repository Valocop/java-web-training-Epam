package by.training.module2.repo;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SentenceRepository implements Repository<Entity> {
    private static final Logger LOG = LogManager.getLogger();
    private List<Entity> sentenceList = new ArrayList<>();

    @Override
    public long add(Entity model) {
        long sentenceId = model.getId();
        Optional<Entity> optional = fromId(sentenceId);

        if (optional.isPresent() && sentenceId != 0) {
            remove(optional.get());
            sentenceList.add(model);
            LOG.info("Sentence id [" + sentenceId + "] was updated.");
            return sentenceId;
        } else {
            long newWordId = RepositoryManager.ID.getAndIncrement();
            model.setId(newWordId);
            sentenceList.add(model);
            LOG.info("Sentence id [" + newWordId + "] was added to repo.");
            return newWordId;
        }
    }

    @Override
    public void remove(Entity model) {
        long sentenceId = model.getId();
        Optional<Entity> optional = fromId(sentenceId);
        optional.ifPresent(entity -> {
            sentenceList.remove(entity);
            LOG.info("Sentence id [" + sentenceId + "] was removed from repo.");
        });
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return new ArrayList<>(sentenceList);
    }

    @Override
    public Entity getById(long id, EntityType type) {
        return fromId(id).get();
    }

    private Optional<Entity> fromId(long id) {
        return sentenceList.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }
}
