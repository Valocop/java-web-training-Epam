package by.training.module2.model;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.entity.Paragraph;
import by.training.module2.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ParagraphComposite implements ModelComposite {
    private static final Logger LOG = LogManager.getLogger();
    private final List<ModelLeaf> sentences = new LinkedList<>();

    @Override
    public void addLeaf(ModelLeaf modelLeaf) {
        sentences.add(modelLeaf);
    }

    @Override
    public List<ModelLeaf> getLeaves() {
        return sentences;
    }

    @Override
    public void removeLeaf(ModelLeaf modelLeaf) {
        sentences.remove(modelLeaf);
    }

    @Override
    public int getCountOfLeaves() {
        return sentences.size();
    }

    public List<ModelLeaf> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        StringBuilder paragraphBuilder = new StringBuilder("\t");
        sentences.forEach(textLeaf -> paragraphBuilder.append(textLeaf.toString()));
        LOG.info("Paragraph is build.");
        return paragraphBuilder.toString();
    }

    @Override
    public Entity save(Service<Entity> service, long parentId, int order) {
        Entity paragraph = new Paragraph(parentId, order, sentences.size(), toString());
        long paragraphId = service.add(paragraph);

        for (int i = 0; i < sentences.size(); i ++) {
            ModelLeaf sentenceLeaf = sentences.get(i);
            sentenceLeaf.save(service, paragraphId, i);
        }
        return load(service, paragraphId);
    }

    @Override
    public Entity load(Service<Entity> service, long id) {
        return service.getById(id, EntityType.PARAGRAPH);
    }
}
