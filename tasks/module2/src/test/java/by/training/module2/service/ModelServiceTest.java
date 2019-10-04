package by.training.module2.service;

import by.training.module2.chain.*;
import by.training.module2.composite.ModelComposite;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.model.*;
import by.training.module2.repo.ModelRepository;
import by.training.module2.repo.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JUnit4.class)
public class ModelServiceTest {
    private static final Logger LOG = LogManager.getLogger();
    private Service<ModelLeaf> service;
    private Repository<ModelLeaf> modelLeafRepository = initRepository();

    @Before
    public void init() {
        service = new ModelService(modelLeafRepository);
    }

    @Test
    public void shouldSortParagraphs() {
        SortSpecification<ModelLeaf> specification = new ParagraphSortSpec();
        List<ModelLeaf> expectList = service.sort(specification);

        Assert.assertEquals(expectList.size(), 4);
        Assert.assertEquals(((ModelComposite)expectList.get(3)).getCountOfLeaf(), 5);
        Assert.assertEquals(expectList.get(0).getModelType(), ModelType.PARAGRAPH);
    }

    private Repository<ModelLeaf> initRepository() {
        ParserChain<ModelLeaf> parserChain = new WordParser()
                .linkWith(new SentenceParser())
                .linkWith(new ParagraphParser())
                .linkWith(new TextParser())
                .linkWith(new InvalidTextParser());
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("src", "test", "resources", "testValid.txt"));
        } catch (IOException e) {
            LOG.warn("Repo is not init.");
        }
        String text = new String(bytes);
        ModelLeaf leaf = parserChain.parseText(text);
        Repository<ModelLeaf> modelLeafRepository = new ModelRepository();
        modelLeafRepository.add(leaf);
        LOG.info("Repo is init.");
        return modelLeafRepository;
    }
}
