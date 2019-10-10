package by.training.module2.service;

import by.training.module2.chain.*;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.controller.TextController;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.entity.Word;
import by.training.module2.repo.Repository;
import by.training.module2.repo.RepositoryControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)
public class ServiceControlTest {
    private Repository<Entity> repo = new RepositoryControl();
    private Service<Entity> service = new ServiceControl(repo);
    private ParserChain<ModelLeaf> parserChain = new WordParser()
            .linkWith(new SentenceParser())
            .linkWith(new ParagraphParser())
            .linkWith(new TextParser())
            .linkWith(new InvalidTextParser());

    @Test
    public void shouldSortParagraphs() {
        TextController controller = new TextController(service, parserChain);
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());

        Comparator<Entity> comparator = Comparator.comparingDouble(Entity::entrySize);
        List<Entity> expectParagraphList = service.sort(comparator, EntityType.PARAGRAPH);
        Assert.assertEquals(expectParagraphList.size(), 4);
        Assert.assertEquals(expectParagraphList.get(0).entrySize(), 1);
        Assert.assertEquals(expectParagraphList.get(1).entrySize(), 2);
        Assert.assertEquals(expectParagraphList.get(2).entrySize(), 4);
        Assert.assertEquals(expectParagraphList.get(3).entrySize(), 5);
    }

    @Test
    public void shouldAddWordEntity() {
        Entity actualWord = new Word(100500, 100500, "Hello!!!");
        long expectId = service.add(actualWord);
        actualWord.setId(expectId);
        Entity expectWord = service.getById(expectId, EntityType.WORD);
        Assert.assertEquals(expectWord, actualWord);
    }

    @Test
    public void shouldUpdateWordEntity() {
        Entity actualWord = new Word(100500, 100500, "Hello!!!");
        long expectId = service.add(actualWord);
        actualWord.setId(expectId);
        ((Word) actualWord).setWord("AAA!!!");
        service.update(actualWord);
        Entity expectWord = service.getById(expectId, EntityType.WORD);
        Assert.assertEquals(expectWord, actualWord);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRemoveWordEntity() {
        Entity actualWord = new Word(100500, 100500, "Hello!!!");
        long actualId = service.add(actualWord);
        actualWord.setId(actualId);
        Entity expectWord = service.getById(actualId, EntityType.WORD);
        Assert.assertEquals(expectWord, actualWord);
        service.remove(expectWord);
        service.getById(actualId, EntityType.WORD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExpectIllegalArgumentExceptionWhileTypeIncorrect() {
        Entity actualWord = new Word(100500, 100500, "Hello!!!");
        long actualId = service.add(actualWord);
        Entity expectWord = service.getById(actualId, EntityType.TEXT);
    }
}
