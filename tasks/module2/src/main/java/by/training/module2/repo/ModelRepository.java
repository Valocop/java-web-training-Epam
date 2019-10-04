package by.training.module2.repo;

import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.model.ModelType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModelRepository implements Repository<ModelLeaf> {
    private static final Logger LOG = LogManager.getLogger();
    private final List<ModelLeaf> leaves = new ArrayList<>();

    @Override
    public void add(ModelLeaf model) {
        leaves.add(model);
    }

    @Override
    public void remove(ModelLeaf model) {
        leaves.remove(model);
    }

    @Override
    public List<ModelLeaf> getByType(ModelType type) {
        List<ModelLeaf> typeLeaves = new ArrayList<>();

        for (ModelLeaf leaf : leaves) {
            typeLeaves.addAll(findByType(leaf, type));
        }
        return typeLeaves;
    }

    @Override
    public List<ModelLeaf> getAll() {
        return new ArrayList<>(leaves);
    }

    private List<ModelLeaf> findByType(ModelLeaf leaf, ModelType type) {
        List<ModelLeaf> list = new ArrayList<>();
        if (leaf.getModelType() == type) {
            list.add(leaf);
            return list;
        }

        List<ModelLeaf> listin = ((ModelComposite) leaf).getLeafes();
        for (ModelLeaf modelLeaf : listin) {
            list.addAll(findByType(modelLeaf, type));
        }
        return list;
    }
}
