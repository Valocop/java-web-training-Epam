package by.training.module2.service;

import by.training.module2.composite.ModelLeaf;
import by.training.module2.repo.Repository;

import java.util.List;

public class ModelService implements Service<ModelLeaf> {
    Repository<ModelLeaf> repo;

    public ModelService(Repository<ModelLeaf> repo) {
        this.repo = repo;
    }

    @Override
    public void add(ModelLeaf model) {
        repo.add(model);
    }

    @Override
    public void remove(ModelLeaf model) {
        repo.remove(model);
    }

    @Override
    public List<ModelLeaf> sort(SortSpecification<ModelLeaf> spec) {
        List<ModelLeaf> list = repo.getByType(spec.getModelType());
        list.sort(spec.getSort());
        return list;
    }

    @Override
    public List<ModelLeaf> find(MatchSpecification<ModelLeaf> spec) {
//        processing
        return null;
    }
}
