package by.training.module2.entity;

import java.util.Objects;

public class Text implements Entity {
    private long id;
    private int oder;
    private int paragraphsCount;
    private String text;

    public Text(int order, int paragraphsCount, String text) {
        this.oder = order;
        this.paragraphsCount = paragraphsCount;
        this.text = text;
    }

    @Override
    public EntityType getType() {
        return EntityType.TEXT;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int entrySize() {
        return paragraphsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text1 = (Text) o;
        return id == text1.id &&
                oder == text1.oder &&
                paragraphsCount == text1.paragraphsCount &&
                text.equals(text1.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oder, paragraphsCount, text);
    }

    public int getOder() {
        return oder;
    }

    public String getText() {
        return text;
    }
}
