package by.training.module2.controller;

import by.training.module2.chain.ParserChain;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.entity.Entity;
import by.training.module2.reader.TextFileReader;
import by.training.module2.service.Service;
import by.training.module2.validator.FileValidator;
import by.training.module2.validator.ResultValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class TextController {
    private static final Logger LOG = LogManager.getLogger();
    private Service<Entity> service;
    private ParserChain<ModelLeaf> parserChain;
    private final long DEFAULT_ID = 10;
    private final int DEFAULT_ORDER_= 0;

    public TextController(Service<Entity> service, ParserChain<ModelLeaf> parserChain) {
        this.service = service;
        this.parserChain = parserChain;
    }

    public void execute(String path) throws IOException {
        FileValidator fileValidator = new FileValidator();
        ResultValidator resultFileValidator = fileValidator.validateFile(path);

        if (resultFileValidator.isValid()) {
            TextFileReader textFileReader = new TextFileReader();
            String text = textFileReader.readData(path);
            ModelLeaf modelLeaf = parserChain.parseText(text);
            modelLeaf.save(service, DEFAULT_ID, DEFAULT_ORDER_);
        } else {
            LOG.error("File validate error " + resultFileValidator.getExceptionMap());
            throw new IllegalArgumentException("Path is incorrect.");
        }
    }
}
