package by.training.module2.entity;

import java.util.Objects;

public class Word implements Entity{
    private long id;
    private long sentenceId;
    private int order;
    private String word;

    public Word(long sentenceId, int order, String word) {
        this.sentenceId = sentenceId;
        this.order = order;
        this.word = word;
    }

    @Override
    public EntityType getType() {
        return EntityType.WORD;
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
        return word.length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return id == word1.id &&
                sentenceId == word1.sentenceId &&
                order == word1.order &&
                word.equals(word1.word);
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sentenceId, order, word);
    }

    public long getSentenceId() {
        return sentenceId;
    }

    public int getOrder() {
        return order;
    }

    public String getWord() {
        return word;
    }
}
