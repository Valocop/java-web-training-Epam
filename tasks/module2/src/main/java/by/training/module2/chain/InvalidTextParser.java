package by.training.module2.chain;

import by.training.module2.composite.ModelLeaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvalidTextParser extends ModelParser {
    private static final Logger LOG = LogManager.getLogger();
    private final String REGEX_WORD = "[[:word:]]+";

    @Override
    public ModelLeaf parseText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text is null.");
        }
        Pattern pattern = Pattern.compile(REGEX_WORD, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return nextParse(text);
        } else {
            throw new IllegalArgumentException("Text don't contain a words.");
        }
    }
}
