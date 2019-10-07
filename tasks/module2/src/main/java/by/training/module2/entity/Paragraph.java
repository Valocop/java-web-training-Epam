package by.training.module2.entity;

public class Paragraph implements Entity {
    private long id;
    private long textId;
    private int order;
    private int sentencesCount;
    private String paragraph;

    public Paragraph(long textId, int order, int sentencesCount, String paragraph) {
        this.textId = textId;
        this.order = order;
        this.sentencesCount = sentencesCount;
        this.paragraph = paragraph;
    }

    @Override
    public EntityType getType() {
        return EntityType.PARAGRAPH;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int entrySize() {
        return sentencesCount;
    }

    public long getTextId() {
        return textId;
    }

    public int getOrder() {
        return order;
    }

    public String getParagraph() {
        return paragraph;
    }
}
