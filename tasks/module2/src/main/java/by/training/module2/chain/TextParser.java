package by.training.module2.chain;

import by.training.module2.composite.ModelLeaf;
import by.training.module2.model.TextComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser extends ModelParser {
    private static final Logger LOG = LogManager.getLogger();
    private final String REGEX = "^[\\s]*(.|\\r|\\n|\\r\\n)+?([.?!] *)$";

    @Override
    public ModelLeaf parseText(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }
        Pattern pattern = Pattern.compile(REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
        TextComposite textComposite = new TextComposite();

        if (matcher.find()) {
            matcher.reset();

            while (matcher.find()) {
                String paragraph = text.substring(matcher.start(), matcher.end()).trim();
                ModelLeaf paragraphLeaf = nextParse(paragraph);
                textComposite.addLeaf(paragraphLeaf);
            }
            LOG.info("Paragraph was find.");
            return textComposite;
        }
        LOG.info("Paragraph wasn't find.");
        return nextParse(text);
    }
}
