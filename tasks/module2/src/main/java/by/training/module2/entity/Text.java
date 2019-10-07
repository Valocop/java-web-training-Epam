package by.training.module2.entity;

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

    public int getOder() {
        return oder;
    }

    public String getText() {
        return text;
    }
}
