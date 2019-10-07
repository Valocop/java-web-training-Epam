package by.training.module2.model;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.entity.Sentence;
import by.training.module2.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class SentenceComposite implements ModelComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<ModelLeaf> words = new LinkedList<>();

    @Override
    public void addLeaf(ModelLeaf modelLeaf) {
        words.add(modelLeaf);
    }

    @Override
    public List<ModelLeaf> getLeaves() {
        return words;
    }

    @Override
    public void removeLeaf(ModelLeaf modelLeaf) {
        words.remove(modelLeaf);
    }

    @Override
    public int getCountOfLeaves() {
        return words.size();
    }

    public List<ModelLeaf> getWords() {
        return words;
    }

    @Override
    public String toString() {
        StringBuilder sentenceBuild = new StringBuilder();
        words.forEach(textLeaf -> sentenceBuild.append(textLeaf.toString()));
        sentenceBuild.append(System.lineSeparator());
        LOG.info("Sentence is build.");
        return sentenceBuild.toString();
    }

    @Override
    public Entity save(Service<Entity> service, long parentId, int order) {
        Entity sentence = new Sentence(parentId, order, words.size(), toString());
        long sentenceId = service.add(sentence);

        for (int i = 0; i < words.size(); i ++) {
            ModelLeaf wordLeaf = words.get(i);
            wordLeaf.save(service, sentenceId, i);
        }
        return load(service, sentenceId);
    }

    @Override
    public Entity load(Service<Entity> service, long id) {
        return service.getById(id, EntityType.SENTENCE);
    }
}
