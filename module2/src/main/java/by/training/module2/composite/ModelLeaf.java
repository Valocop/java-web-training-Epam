package by.training.module2.composite;

import by.training.module2.entity.Entity;
import by.training.module2.service.Service;

public interface ModelLeaf {
    Entity save(Service<Entity> service, long parentId, int order);
    Entity load(Service<Entity> service, long id);
}
