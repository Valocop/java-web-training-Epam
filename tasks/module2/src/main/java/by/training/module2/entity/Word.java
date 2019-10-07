package by.training.module2.entity;

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
