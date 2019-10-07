package by.training.module2.service;

import by.training.module2.chain.*;
import by.training.module2.composite.ModelLeaf;
import by.training.module2.controller.TextController;
import by.training.module2.entity.Entity;
import by.training.module2.entity.EntityType;
import by.training.module2.repo.Repository;
import by.training.module2.repo.RepositoryManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)
public class ServiceManagerTest {
    private Repository<Entity> repo = new RepositoryManager();
    private Service<Entity> service = new ServiceManager(repo);
    private TextController controller;
    ParserChain<ModelLeaf> parserChain = new WordParser()
            .linkWith(new SentenceParser())
            .linkWith(new ParagraphParser())
            .linkWith(new TextParser())
            .linkWith(new InvalidTextParser());

    @Before
    public void init() throws IOException {
        controller = new TextController(service, parserChain);
        controller.execute(Paths.get("src", "test", "resources", "testValid.txt").toString());
    }

    @Test
    public void shouldSortParagraphs() {
        Comparator<Entity> comparator = Comparator.comparingDouble(Entity::entrySize);
        List<Entity> expectParagraphList = service.sort(comparator, EntityType.PARAGRAPH);
        Assert.assertEquals(expectParagraphList.size(), 4);
        Assert.assertEquals(expectParagraphList.get(0).entrySize(), 1);
        Assert.assertEquals(expectParagraphList.get(1).entrySize(), 2);
        Assert.assertEquals(expectParagraphList.get(2).entrySize(), 4);
        Assert.assertEquals(expectParagraphList.get(3).entrySize(), 5);
    }
}
