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
    private final String REGEX = "^([ ]+|[\\t]+)(.|\\r|\\n|\\r\\n)+?([.?!] *)$";

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
            return textComposite;
        }
        return nextParse(text);
    }

    public static void main(String[] args) throws IOException {
        ParserChain<ModelLeaf> parserChain = new WordParser()
                .linkWith(new SentenceParser())
                .linkWith(new ParagraphParser())
                .linkWith(new TextParser())
                .linkWith(new InvalidTextParser());
        byte[] bytes = Files.readAllBytes(Paths.get("src", "test", "resources", "testValid.txt"));
//        byte[] bytes = Files.readAllBytes(Paths.get("src", "test", "resources", "testWithoutSentences.txt"));
//        byte[] bytes = Files.readAllBytes(Paths.get("src", "test", "resources", "testWithoutWords.txt"));
//        byte[] bytes = Files.readAllBytes(Paths.get("src", "test", "resources", "testWithoutParagraphs.txt"));

        String text = new String(bytes);
        ModelLeaf leaf = parserChain.parseText(text);
        System.out.println(leaf.toString());
    }
}
