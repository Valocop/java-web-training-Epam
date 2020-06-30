package by.training.module1.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataFileReader {
    private static final Logger LOGGER = LogManager.getLogger();

    public List<String> readData(String stringPath) throws IOException {
        Path path = Paths.get(stringPath);
        List<String> list = Files.readAllLines(path);
        LOGGER.info("File [" + stringPath + "] was read to List");
        return list;
    }
}
