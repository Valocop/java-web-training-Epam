package by.training.module2.chain;

public interface ParserChain<T> {
    T parseText(String text);
    ParserChain<T> linkWith(ParserChain<T> next);
}
