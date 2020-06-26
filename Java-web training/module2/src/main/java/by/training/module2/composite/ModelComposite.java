package by.training.module2.composite;

import java.util.List;

public interface ModelComposite extends ModelLeaf {
    void addLeaf(ModelLeaf modelLeaf);
    List<ModelLeaf> getLeaves();
    void removeLeaf(ModelLeaf modelLeaf);
    int getCountOfLeaves();
}
