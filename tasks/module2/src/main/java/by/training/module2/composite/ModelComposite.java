package by.training.module2.composite;

import java.util.List;

public interface ModelComposite extends ModelLeaf {
    void addLeaf(ModelLeaf modelLeaf);
    List<ModelLeaf> getLeafes();
    void removeLeaf(ModelLeaf modelLeaf);
    int getCountOfLeaf();
}
