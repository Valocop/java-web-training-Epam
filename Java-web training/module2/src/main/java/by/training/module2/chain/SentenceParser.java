package by.training.module2.chain;

import by.training.module2.composite.ModelLeaf;
import by.training.module2.model.SentenceComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends ModelParser {
    private static final Logger LOG = LogManager.getLogger();
    private final String REGEX = "[^\\s]+[\\s]*";

    @Override
    public ModelLeaf parseText(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }
        Pattern pattern = Pattern.compile(REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
        SentenceComposite sentenceComposite = new SentenceComposite();

        if (matcher.find()) {
            matcher.reset();

            while (matcher.find()) {
                String word = text.substring(matcher.start(), matcher.end()).trim();
                ModelLeaf wordLeaf = nextParse(word);
                sentenceComposite.addLeaf(wordLeaf);
            }
            LOG.info("Word was find.");
            return sentenceComposite;
        }
        LOG.info("Word wasn't find.");
        return nextParse(text);
    }
}
