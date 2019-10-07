package by.training.module2.controller;

import by.training.module2.chain.*;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.repo.Repository;
import by.training.module2.repo.RepositoryManager;
import by.training.module2.service.Service;
import by.training.module2.service.ServiceManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JUnit4.class)
public class TextControllerTest {
    private Repository<Entity> repo = new RepositoryManager();
    private Service<Entity> service = new ServiceManager(repo);
    private TextController controller;
    ParserChain<ModelLeaf> parserChain = new WordParser()
                .linkWith(new SentenceParser())
                .linkWith(new ParagraphParser())
                .linkWith(new TextParser())
                .linkWith(new InvalidTextParser());

    @Before
    public void init() {
        controller = new TextController(service, parserChain);
    }

    @Test
    public void shouldReadAndSaveText() throws IOException {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectTextList = repo.getAll(EntityType.TEXT);
        Entity expectText = expectTextList.get(0);
        Assert.assertEquals(expectTextList.size(), 1);
        Assert.assertEquals(expectText.entrySize(), 4);
    }

    @Test
    public void shouldReadAndSaveParagraphs() throws IOException {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectParagraphList = repo.getAll(EntityType.PARAGRAPH);
        Assert.assertEquals(expectParagraphList.size(), 4);
    }

    @Test
    public void shouldReadAndSaveSentences() throws IOException {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectSentenceList = repo.getAll(EntityType.SENTENCE);
        Assert.assertEquals(expectSentenceList.size(), 12);
    }

    @Test
    public void shouldReadAndSaveWords() throws IOException {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectWordList = repo.getAll(EntityType.WORD);
        Assert.assertEquals(expectWordList.size(), 125);
    }
}
