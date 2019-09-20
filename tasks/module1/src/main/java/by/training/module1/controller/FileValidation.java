package by.training.module1.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileValidation {
    private static final Logger LOGGER = LogManager.getLogger();
    private String stringPath;
    private Path path;
    private ResultValidation resultValidation = new ResultValidation();

    public FileValidation(String path) {
        this.stringPath = path;
    }

    private void checkPath() {

        if (stringPath != null) {
            try {
                path = Paths.get(stringPath);
            } catch (InvalidPathException e) {
                resultValidation.addException("InvalidPathException", Arrays.asList("[" + stringPath + "] is invalid path."));
                LOGGER.info("[" + stringPath + "] is invalid path.");
            }
            if (Files.exists(path)) {
                LOGGER.info("[" + stringPath + "] is exist.");

                if (Files.isRegularFile(path)) {
                    LOGGER.info("[" + stringPath + "] is a file.");

                    if (Files.isReadable(path)) {
                        LOGGER.info("[" + stringPath + "] file is readable.");

                        try {
                            long size = 0;
                            if ((size = Files.size(path)) != 0) {
                                LOGGER.info("[" + stringPath + "] file is not empty" + "[" + size + "].");
                                LOGGER.info("[" + stringPath + "] file can be read.");
                            } else {
                                resultValidation.addException("FileIsEmpty", Arrays.asList("[" + stringPath + "] file is empty."));
                                LOGGER.info("[" + stringPath + "] file is empty.");
                            }
                        } catch (IOException e) {
                            resultValidation.addException("FileReadSizeException", Arrays.asList("[" + stringPath + "] file size unknown."));
                        }
                    } else {
                        resultValidation.addException("FileIsNotReadable", Arrays.asList("[" + stringPath + "] file is not readable."));
                        LOGGER.info("[" + stringPath + "] file is not readable.");
                    }
                } else {
                    resultValidation.addException("IsNotFile", Arrays.asList("[" + stringPath + "] is not a file."));
                    LOGGER.info("[" + stringPath + "] is not a file.");
                }
            } else {
                resultValidation.addException("FileIsNotExists", Arrays.asList("[" + stringPath + "] is not exist."));
                LOGGER.info("[" + stringPath + "] is not exist.");
            }
        } else {
            resultValidation.addException("PathIsNull", Arrays.asList("Path is null."));
            LOGGER.info("Path is null.");
        }
    }

    public ResultValidation validate() {
        checkPath();
        return resultValidation;
    }
}
