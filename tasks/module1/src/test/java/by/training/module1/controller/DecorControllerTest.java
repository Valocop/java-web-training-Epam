package by.training.module1.controller;

import by.training.module1.entity.Amber;
import by.training.module1.entity.Decor;
import by.training.module1.entity.Pearl;
import by.training.module1.repo.DecorRepository;
import by.training.module1.service.DecorService;
import by.training.module1.service.Service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)

public class DecorControllerTest {

    @Test
    public void shouldReadFileAndCreateDecors() {
        DecorRepository decorRepository = new DecorRepository();
        Service<Decor> decorService = new DecorService(decorRepository);
        Path resourceDirectory = Paths.get("src","test", "resources", "testValid.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(decorService, path);

        boolean result = decorController.process();
        List<Decor> expectList = decorService.getAll();
        List<Decor> actualList = new ArrayList<>();
        actualList.add(new Amber(150.432, 10.1923, 80, 0, 0));
        actualList.add(new Amber(12, 15.62, 85, 1500, 7));
        actualList.add(new Pearl(100.5, 15.62, 87, 5, 15));
        actualList.add(new Pearl(1000, 150.62, 90, 50, 17));
        actualList.add(new Pearl(1, 10, 100, 0, 0));

        Assert.assertTrue(result);
        Assert.assertEquals(5, expectList.size());
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldNotReadEmptyFile() {
        DecorRepository decorRepository = new DecorRepository();
        Service<Decor> serviceRepository = new DecorService(decorRepository);
        Path resourceDirectory = Paths.get("src","test", "resources", "testEmpty.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);

        boolean expectResult = decorController.process();
        List<Decor> expectList = serviceRepository.getAll();

        Assert.assertFalse(expectResult);
        Assert.assertTrue(expectList.isEmpty());
    }

    @Test
    public void shouldReadFileSkipInvalidLinesAndCreateDecors() {
        DecorRepository decorRepository = new DecorRepository();
        Service<Decor> serviceRepository = new DecorService(decorRepository);
        Path resourceDirectory = Paths.get("src","test", "resources", "testHalfValid.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);

        boolean result = decorController.process();
        List<Decor> expectList = serviceRepository.getAll();
        List<Decor> actualList = new ArrayList<>();
        actualList.add(new Amber(150.432, 10.1923, 80, 0, 5));
        actualList.add(new Pearl(100.5, 15.62, 87, 5, 15));

        Assert.assertTrue(result);
        Assert.assertEquals(2, expectList.size());
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldNotReadNoExistFile() {
        DecorRepository decorRepository = new DecorRepository();
        Service<Decor> serviceRepository = new DecorService(decorRepository);
        Path resourceDirectory = Paths.get("src","test", "resources", "noExist.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);

        boolean result = decorController.process();
        List<Decor> expectList = serviceRepository.getAll();

        Assert.assertFalse(result);
        Assert.assertTrue(expectList.isEmpty());
    }

    @Test
    public void shouldNotReadFileWhenPathNull() {
        DecorRepository decorRepository = new DecorRepository();
        Service<Decor> serviceRepository = new DecorService(decorRepository);
        String path = null;
        DecorController decorController = new DecorController(serviceRepository, path);

        boolean result = decorController.process();
        List<Decor> expectList = serviceRepository.getAll();

        Assert.assertFalse(result);
        Assert.assertTrue(expectList.isEmpty());
    }

    @Test
    public void shouldNotReadDirectory() {
        DecorRepository decorRepository = new DecorRepository();
        Service<Decor> serviceRepository = new DecorService(decorRepository);
        Path resourceDirectory = Paths.get("src","test", "resources");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);

        boolean result = decorController.process();
        List<Decor> expectListList = serviceRepository.getAll();

        Assert.assertFalse(result);
        Assert.assertTrue(expectListList.isEmpty());
    }

    @Test
    public void shouldNotReadNotValidFile() {
        DecorRepository decorRepository = new DecorRepository();
        Service<Decor> serviceRepository = new DecorService(decorRepository);
        Path resourceDirectory = Paths.get("src","test", "resources", "testNotValid.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);

        boolean result = decorController.process();
        List<Decor> expectList = serviceRepository.getAll();

        Assert.assertTrue(result);
        Assert.assertTrue(expectList.isEmpty());
    }
}
