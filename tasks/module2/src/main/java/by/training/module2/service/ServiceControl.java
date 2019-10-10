package by.training.module2.service;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.repo.FindSpecification;
import by.training.module2.repo.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class ServiceControl implements Service<Entity> {
    private static final Logger LOG = LogManager.getLogger();
    private Service<Entity> textService;
    private Service<Entity> paragraphService;
    private Service<Entity> sentenceService;
    private Service<Entity> wordService;

    public ServiceControl(Repository<Entity> repo) {
        textService = new TextService(repo);
        paragraphService = new ParagraphService(repo);
        sentenceService = new SentenceService(repo);
        wordService = new WordService(repo);
    }

    @Override
    public long add(Entity model) {
        Service<Entity> service = getService(model.getType());
        return service.add(model);
    }

    @Override
    public void update(Entity model) {
        Service<Entity> service = getService(model.getType());
        service.update(model);
    }

    @Override
    public void remove(Entity model) {
        Service<Entity> service = getService(model.getType());
        service.remove(model);
    }

    @Override
    public List<Entity> sort(Comparator<Entity> comparator, EntityType type) {
        Service<Entity> service = getService(type);
        return service.sort(comparator, type);
    }

    @Override
    public List<Entity> find(FindSpecification<Entity> spec, EntityType type) {
        Service<Entity> service = getService(type);
        return service.find(spec, type);
    }

    @Override
    public Entity getById(long id, EntityType type) {
        Service<Entity> service = getService(type);
        return service.getById(id, type);
    }

    @Override
    public List<Entity> getAll(EntityType type) {
        Service<Entity> service = getService(type);
        return service.getAll(type);
    }

    private Service<Entity> getService(EntityType type) {
        switch (type){
            case TEXT:
                return textService;
            case PARAGRAPH:
                return paragraphService;
            case SENTENCE:
                return sentenceService;
            case WORD:
                return wordService;
                default:
                    IllegalArgumentException e =
                            new IllegalArgumentException("Incorrect of Entity type for get Service.");
                    LOG.error(e);
                    throw e;
        }
    }
}
