package by.training.module1.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger();
    private String stringPath;
    private Path path;
    private ResultValidator resultValidator = new ResultValidator();

    public FileValidator(String path) {
        this.stringPath = path;
    }

    private void checkPath() {
        if (stringPath != null) {
            try {
                path = Paths.get(stringPath);
            } catch (InvalidPathException e) {
                resultValidator.addException("InvalidPathException", Arrays.asList("["
                        + stringPath + "] is invalid path."));
                LOGGER.info("[" + stringPath + "] is invalid path.");
            }
            if (Files.exists(path)) {
                LOGGER.info("[" + stringPath + "] is exist.");

                if (Files.isRegularFile(path)) {
                    LOGGER.info("[" + stringPath + "] is a file.");

                    if (Files.isReadable(path)) {
                        LOGGER.info("[" + stringPath + "] file is readable.");

                        checkPathOfEmpty(path);

                    } else {
                        resultValidator.addException("FileIsNotReadable", Arrays.asList("["
                                + stringPath + "] file is not readable."));
                        LOGGER.info("[" + stringPath + "] file is not readable.");
                    }
                } else {
                    resultValidator.addException("IsNotFile", Arrays.asList("["
                            + stringPath + "] is not a file."));
                    LOGGER.info("[" + stringPath + "] is not a file.");
                }
            } else {
                resultValidator.addException("FileIsNotExists", Arrays.asList("["
                        + stringPath + "] is not exist."));
                LOGGER.info("[" + stringPath + "] is not exist.");
            }
        } else {
            resultValidator.addException("PathIsNull", Arrays.asList("Path is null."));
            LOGGER.info("Path is null.");
        }
    }

    private void checkPathOfEmpty(Path path) {
        try {
            if (Files.size(path) != 0) {
                LOGGER.info("[" + stringPath + "] file is not empty.");
                LOGGER.info("[" + stringPath + "] file can be read.");
            } else {
                resultValidator.addException("FileIsEmpty", Arrays.asList("["
                        + stringPath + "] file is empty."));
                LOGGER.error("[" + stringPath + "] file is empty.");
            }
        } catch (IOException e) {
            resultValidator.addException("FileReadSizeException", Arrays.asList("["
                    + stringPath + "] file size unknown."));
            LOGGER.error("FileReadSizeException" + "["
                    + stringPath + "] file size unknown.");
        }
    }

    @Override
    public ResultValidator validate() {
        checkPath();
        return resultValidator;
    }
}
