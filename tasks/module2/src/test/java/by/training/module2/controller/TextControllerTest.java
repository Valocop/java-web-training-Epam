package by.training.module2.controller;

import by.training.module2.chain.*;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.repo.Repository;
import by.training.module2.repo.RepositoryControl;
import by.training.module2.service.Service;
import by.training.module2.service.ServiceControl;
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
    private Repository<Entity> repo = new RepositoryControl();
    private Service<Entity> service = new ServiceControl(repo);
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
    public void shouldReadValidFileAndSaveText() {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectTextList = repo.getAll(EntityType.TEXT);
        Entity expectText = expectTextList.get(0);
        Assert.assertEquals(expectTextList.size(), 1);
        Assert.assertEquals(expectText.entrySize(), 4);
    }

    @Test
    public void shouldReadValidFileAndSaveParagraphs() {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectParagraphList = repo.getAll(EntityType.PARAGRAPH);
        Assert.assertEquals(expectParagraphList.size(), 4);
    }

    @Test
    public void shouldReadValidFileAndSaveSentences() {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectSentenceList = repo.getAll(EntityType.SENTENCE);
        Assert.assertEquals(expectSentenceList.size(), 12);
    }

    @Test
    public void shouldReadValidFileAndSaveWords() {
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
        List<Entity> expectWordList = repo.getAll(EntityType.WORD);
        Assert.assertEquals(expectWordList.size(), 125);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExpectIllegalArgumentExceptionReadEmptyFile() {
        controller.execute(Paths.get("src", "test", "resources", "testEmpty.txt").toString());
    }

    @Test
    public void shouldReadFileWithoutParagraphs() {
        controller.execute(Paths.get("src", "test", "resources", "testWithoutParagraphs.txt").toString());
        List<Entity> expectTextList = repo.getAll(EntityType.TEXT);
        List<Entity> expectParagraphList = repo.getAll(EntityType.PARAGRAPH);
        Assert.assertEquals(expectTextList.size(), 1);
        Assert.assertEquals(expectParagraphList.size(), 1);
    }

    @Test
    public void shouldReadFileWithoutSentences() {
        controller.execute(Paths.get("src", "test", "resources", "testWithoutSentences.txt").toString());
        List<Entity> expectTextList = repo.getAll(EntityType.TEXT);
        List<Entity> expectParagraphList = repo.getAll(EntityType.PARAGRAPH);
        List<Entity> expectSentenceList = repo.getAll(EntityType.SENTENCE);
        Assert.assertEquals(expectTextList.size(), 0);
        Assert.assertEquals(expectParagraphList.size(), 0);
        Assert.assertEquals(expectSentenceList.size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReadFileWithoutWords() {
        controller.execute(Paths.get("src", "test", "resources", "testWithoutWords.txt").toString());
    }
}
