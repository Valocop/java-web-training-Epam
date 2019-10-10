package by.training.module2.repo;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class RepositoryControl implements Repository<Entity> {
    public static final AtomicLong ID = new AtomicLong(1);
    private static final Logger LOG = LogManager.getLogger();
    private Repository<Entity> wordRepo = new WordRepository();
    private Repository<Entity> sentenceRepo = new SentenceRepository();
    private Repository<Entity> paragraphRepo = new ParagraphRepository();
    private Repository<Entity> textRepo = new TextRepository();

    @Override
    public long create(Entity model) {
        Repository<Entity> repo = getRepo(model.getType());
        return repo.create(model);
    }

    @Override
    public void update(Entity model) {
        Repository<Entity> repo = getRepo(model.getType());
        repo.update(model);
    }

    @Override
    public void remove(Entity model) {
        Repository<Entity> repo = getRepo(model.getType());
        repo.remove(model);
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        Repository<Entity> repo = getRepo(type);
        return repo.getAll(type);
    }

    @Override
    public Entity read(long id, EntityType type) {
        Repository<Entity> repo = getRepo(type);
        return repo.read(id, type);
    }

    @Override
    public List<Entity> find(FindSpecification<Entity> spec, EntityType type) {
        Repository<Entity> repo = getRepo(type);
        return repo.find(spec, type);
    }

    private Repository<Entity> getRepo(EntityType type) {
        switch (type) {
            case TEXT:
                return textRepo;
            case PARAGRAPH:
                return paragraphRepo;
            case SENTENCE:
                return sentenceRepo;
            case WORD:
                return wordRepo;
                default:
                    IllegalArgumentException e = new IllegalArgumentException("Incorrect of Entity type.");
                    LOG.error(e);
                    throw e;
        }
    }
}
