package by.training.module2.entity;

public interface Entity {
    EntityType getType();
    long getId();
    void setId(long id);
    int entrySize();
}
