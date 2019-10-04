package by.training.module2.chain;

import by.training.module2.composite.ModelLeaf;

public abstract class ModelParser implements ParserChain<ModelLeaf> {
    private ParserChain<ModelLeaf> next;

    @Override
    public ParserChain<ModelLeaf> linkWith(ParserChain<ModelLeaf> next) {
        ((ModelParser) next).next = this;
        return next;
    }

    protected ModelLeaf nextParse(String line) {
        if (next != null) {
            return next.parseText(line);
        } else {
            return null;
        }
    }
}
