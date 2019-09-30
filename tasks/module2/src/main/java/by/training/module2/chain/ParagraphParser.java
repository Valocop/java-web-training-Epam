package by.training.module2.chain;

import by.training.module2.composite.TextComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends TextParser {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public TextComposite parseText(String text) {

        if (text.startsWith("\t")) {
            Pattern pattern = Pattern.compile("^\t.");
            Matcher matcher = pattern.matcher(text);
        }

        return  nextParse(text);
    }
}
