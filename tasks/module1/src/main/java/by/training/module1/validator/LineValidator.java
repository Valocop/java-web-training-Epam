package by.training.module1.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger();
    private String line;
    private ResultValidator resultValidator = new ResultValidator();

    public LineValidator(String line) {
        this.line = line;
    }

    private void checkLine() {
        Pattern pattern = Pattern.compile("( *[\\wа-яА-Я.]+ *: *[\\wа-яА-Я.]+ *)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            LOGGER.info("[" + line + "]" + " contain parameters.");
        } else {
            resultValidator.addException("InvalidLine", Arrays.asList("["
                    + line + "]" + " do not contain parameters."));
            LOGGER.info("[" + line + "]" + " do not contain parameters.");
        }
    }

    @Override
    public ResultValidator validate() {
        checkLine();
        return resultValidator;
    }
}

