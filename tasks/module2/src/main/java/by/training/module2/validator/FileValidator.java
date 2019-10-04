package by.training.module2.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileValidator {
    private static final Logger LOG = LogManager.getLogger();

    private void checkPath(String stringPath, ResultValidator resultValidator) {
        Path path = null;
        if (stringPath != null) {
            try {
                path = Paths.get(stringPath);
            } catch (InvalidPathException e) {
                resultValidator.addException("InvalidPathException", Arrays.asList("["
                        + stringPath + "] is invalid path."));
                LOG.info("[" + stringPath + "] is invalid path.");
            }
            if (Files.exists(path)) {
                LOG.info("[" + stringPath + "] is exist.");

                if (Files.isRegularFile(path)) {
                    LOG.info("[" + stringPath + "] is a file.");

                    if (Files.isReadable(path)) {
                        LOG.info("[" + stringPath + "] file is readable.");

                        checkPathOfEmpty(path, stringPath, resultValidator);

                    } else {
                        resultValidator.addException("FileIsNotReadable", Arrays.asList("["
                                + stringPath + "] file is not readable."));
                        LOG.info("[" + stringPath + "] file is not readable.");
                    }
                } else {
                    resultValidator.addException("IsNotFile", Arrays.asList("["
                            + stringPath + "] is not a file."));
                    LOG.info("[" + stringPath + "] is not a file.");
                }
            } else {
                resultValidator.addException("FileIsNotExists", Arrays.asList("["
                        + stringPath + "] is not exist."));
                LOG.info("[" + stringPath + "] is not exist.");
            }
        } else {
            resultValidator.addException("PathIsNull", Arrays.asList("Path is null."));
            LOG.info("Path is null.");
        }
    }

    private void checkPathOfEmpty(Path path, String stringPath, ResultValidator resultValidator) {
        try {
            if (Files.size(path) != 0) {
                LOG.info("[" + stringPath + "] file is not empty.");
                LOG.info("[" + stringPath + "] file can be read.");
            } else {
                resultValidator.addException("FileIsEmpty", Arrays.asList("["
                        + stringPath + "] file is empty."));
                LOG.error("[" + stringPath + "] file is empty.");
            }
        } catch (IOException e) {
            resultValidator.addException("FileReadSizeException", Arrays.asList("["
                    + stringPath + "] file size unknown."));
            LOG.error("FileReadSizeException" + "["
                    + stringPath + "] file size unknown.");
        }
    }

    public ResultValidator validateFile(String path) {
        ResultValidator resultValidator = new ResultValidator();
        checkPath(path, resultValidator);
        return resultValidator;
    }
}
