package by.training.module2.chain;

import by.training.module2.composite.ModelLeaf;
import by.training.module2.model.ParagraphComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends ModelParser {
    private static final Logger LOG = LogManager.getLogger();
    private final String REGEX = "[^\\s]*?[^.!?]+[.!?]";

    @Override
    public ModelLeaf parseText(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }
        Pattern pattern = Pattern.compile(REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
        ParagraphComposite paragraphComposite = new ParagraphComposite();

        if (matcher.find()) {
            matcher.reset();

            while (matcher.find()) {
                String sentence = text.substring(matcher.start(), matcher.end()).trim();
                ModelLeaf sentenceLeaf = nextParse(sentence);
                paragraphComposite.addLeaf(sentenceLeaf);
            }
            LOG.info("Sentence was find.");
            return paragraphComposite;
        }
        LOG.info("Sentence wasn't find.");
        return nextParse(text);
    }
}
