package by.training.module2.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFileReader {
    private static final Logger LOG = LogManager.getLogger();

    public String readData(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        LOG.info("Path [" + path + "] was read to text.");
        return new String(bytes);
    }
}
