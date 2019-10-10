package by.training.module2.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paragraph paragraph1 = (Paragraph) o;
        return id == paragraph1.id &&
                textId == paragraph1.textId &&
                order == paragraph1.order &&
                sentencesCount == paragraph1.sentencesCount &&
                paragraph.equals(paragraph1.paragraph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textId, order, sentencesCount, paragraph);
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
