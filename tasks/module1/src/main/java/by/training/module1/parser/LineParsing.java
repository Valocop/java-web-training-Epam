package by.training.module1.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParsing {
    private static final Logger LOGGER = LogManager.getLogger();
    private String line;
    private Map<String, String> parameters = new HashMap<>();

    public LineParsing(String line) {
        this.line = line;
    }

    public Map<String, String> parseLine() {
        Pattern pattern = Pattern.compile("( *[\\wа-яА-Я.]+ *: *[\\wа-яА-Я.]+ *)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String linePath = line.substring(matcher.start(), matcher.end()).trim();
            String[] linePaths = linePath.split(":");
            if (linePaths.length == 2) {
                parameters.put(linePaths[0].toLowerCase().trim(), linePaths[1].trim());
                LOGGER.info("[" + linePaths[0] + " : " + linePaths[1] + "]" + " add to parameters.");
            } else {
                LOGGER.info("[" + linePath + "]" + " can't add to parameters.");
            }
        }
        return parameters;
    }
}
