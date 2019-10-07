package by.training.module2.model;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.entity.Text;
import by.training.module2.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class TextComposite implements ModelComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<ModelLeaf> paragraphs = new LinkedList<>();

    @Override
    public void addLeaf(ModelLeaf modelLeaf) {
        paragraphs.add(modelLeaf);
    }

    @Override
    public List<ModelLeaf> getLeaves() {
        return paragraphs;
    }

    @Override
    public void removeLeaf(ModelLeaf modelLeaf) {
        paragraphs.remove(modelLeaf);
    }

    @Override
    public int getCountOfLeaves() {
        return paragraphs.size();
    }

    public List<ModelLeaf> getParagraphs() {
        return paragraphs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        paragraphs.forEach(modelLeaf -> builder.append(modelLeaf.toString()));
        LOG.info("Text is build.");
        return builder.toString();
    }

    @Override
    public Entity save(Service<Entity> service, long parentId, int order) {
        Entity text = new Text(order, paragraphs.size(), toString());
        long textId = service.add(text);

        for (int i = 0; i < paragraphs.size(); i ++) {
            ModelLeaf paragraphLeaf = paragraphs.get(i);
            paragraphLeaf.save(service, textId, i);
        }
        return load(service, textId);
    }

    @Override
    public Entity load(Service<Entity> service, long id) {
        return service.getById(id, EntityType.TEXT);
    }
}
