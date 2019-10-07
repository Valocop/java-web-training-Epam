package by.training.module2.model;

import by.training.module2.composite.ModelLeaf;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.entity.Word;
import by.training.module2.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WordLeaf implements ModelLeaf {
    private static final Logger LOG = LogManager.getLogger();
    private String word;

    public WordLeaf(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        LOG.info("[" + word + "] word is build.");
        return word + " ";
    }

    @Override
    public Entity save(Service<Entity> service, long parentId, int order) {
        Entity word = new Word(parentId, order, this.word);
        long wordId = service.add(word);
        return load(service, wordId);
    }

    @Override
    public Entity load(Service<Entity> service, long id) {
        return service.getById(id, EntityType.WORD);
    }
}
