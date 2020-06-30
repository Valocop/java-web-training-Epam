package by.training.module2.entity;

import java.util.Objects;

public class Sentence implements Entity {
    private long id;
    private long paragraphId;
    private int order;
    private int wordsCount;
    private String sentence;

    public Sentence(long paragraphId, int order, int wordsCount, String sentence) {
        this.paragraphId = paragraphId;
        this.order = order;
        this.wordsCount = wordsCount;
        this.sentence = sentence;
    }

    @Override
    public EntityType getType() {
        return EntityType.SENTENCE;
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
        return wordsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence1 = (Sentence) o;
        return id == sentence1.id &&
                paragraphId == sentence1.paragraphId &&
                order == sentence1.order &&
                wordsCount == sentence1.wordsCount &&
                sentence.equals(sentence1.sentence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paragraphId, order, wordsCount, sentence);
    }

    public long getParagraphId() {
        return paragraphId;
    }

    public int getOrder() {
        return order;
    }

    public String getSentence() {
        return sentence;
    }
}
