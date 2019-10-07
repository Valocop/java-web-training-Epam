package by.training.module2.entity;

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
