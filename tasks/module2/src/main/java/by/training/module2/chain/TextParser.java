package by.training.module2.chain;

import by.training.module2.composite.TextComposite;

public abstract class TextParser implements ParserChain<TextComposite> {
    private ParserChain<TextComposite> next;

    @Override
    public ParserChain<TextComposite> linkWith(ParserChain<TextComposite> next) {
        return this.next = next;
    }

    protected TextComposite nextParse(String line) {
        if (next != null) {
            return next.parseText(line);
        } else {
            return null;
        }
    }
}
