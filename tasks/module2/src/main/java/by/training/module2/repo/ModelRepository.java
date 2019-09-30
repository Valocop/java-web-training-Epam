package by.training.module2.repo;

import by.training.module2.composite.TextLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModelRepository implements Repository<TextLeaf> {
    private static final Logger LOG = LogManager.getLogger();
    private final List<TextLeaf> textLeaves = new ArrayList<>();

    @Override
    public boolean add(TextLeaf model) {
        return textLeaves.add(model);
    }

    @Override
    public boolean remove(TextLeaf model) {
        return textLeaves.remove(model);
    }

    @Override
    public List<TextLeaf> find(MatchSpecification<TextLeaf> spec) {
        List<TextLeaf> list = new ArrayList<>();

        textLeaves.forEach(textLeaf -> {
            if (spec.match(textLeaf)) {
                LOG.info("TextLeaf was found by specification.");
            }
        });
        return null;
    }

    @Override
    public List<TextLeaf> getAll(GetSpecification spec) {
        return null;
    }
}
