package by.training.module2.service;

import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.repo.Repository;

import java.util.Comparator;
import java.util.List;

public class ServiceManager implements Service<Entity> {
    private Repository<Entity> repo;
    private Service<Entity> textService;
    private Service<Entity> paragraphService;
    private Service<Entity> sentenceService;
    private Service<Entity> wordService;

    public ServiceManager(Repository<Entity> repo) {
        this.repo = repo;
        textService = new TextService(this.repo);
        paragraphService = new ParagraphService(this.repo);
        sentenceService = new SentenceService(this.repo);
        wordService = new WordService(this.repo);
    }

    @Override
    public long add(Entity model) {
        Service<Entity> service = getService(model.getType());
        return service.add(model);
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
                    throw new IllegalArgumentException();
        }
    }
}
