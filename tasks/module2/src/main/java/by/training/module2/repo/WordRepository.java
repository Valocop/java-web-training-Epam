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
    public long add(Entity model) {
        long wordId = model.getId();
        Optional<Entity> optional = fromId(wordId);

        if (optional.isPresent() && wordId != 0) {
            remove(optional.get());
            wordList.add(model);
            LOG.info("Word id [" + wordId + "] was updated.");
            return wordId;
        } else {
            long newWordId = RepositoryManager.ID.getAndIncrement();
            model.setId(newWordId);
            wordList.add(model);
            LOG.info("Word id [" + newWordId + "] was added to repo.");
            return newWordId;
        }
    }

    @Override
    public void remove(Entity model) {
        long wordId = model.getId();
        Optional<Entity> optional = fromId(wordId);
        optional.ifPresent(entity -> {
            wordList.remove(entity);
            LOG.info("Word id [" + wordId + "] was removed from repo.");
        });
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        return new ArrayList<>(wordList);
    }

    @Override
    public Entity getById(long id, EntityType type) {
        return fromId(id).get();
    }

    private Optional<Entity> fromId(long id) {
        return wordList.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }
}
