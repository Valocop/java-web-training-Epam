package by.training.module1.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineValidation {
    private static final Logger LOGGER = LogManager.getLogger();
    private String line;
    private ResultValidation resultValidation = new ResultValidation();

    public LineValidation(String line) {
        this.line = line;
    }

    private void checkLine() {
        Pattern pattern = Pattern.compile("( *[\\wа-яА-Я.]+ *: *[\\wа-яА-Я.]+ *)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            LOGGER.info("[" + line + "]" + " contain parameters.");
        } else {
            resultValidation.addException("InvalidLine", Arrays.asList("[" + line + "]" + " do not contain parameters."));
            LOGGER.info("[" + line + "]" + " do not contain parameters.");
        }
    }

    public ResultValidation validate() {
        checkLine();
        return resultValidation;
    }
}

