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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)

public class DecorControllerTest {
    private ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void shouldReadFileAndCreateDecors() {
        Service<Decor> decorService = new DecorService(new DecorRepository());
        Path resourceDirectory = Paths.get("src","test","resources", "testValid.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(decorService, path);
        boolean result = decorController.process();
        List<Decor> actualList = decorService.getAll();
        List<Decor> expectList = new ArrayList<>();
        expectList.add(new Amber(150.432, 10.1923, 80, 0, 0));
        expectList.add(new Amber(12, 15.62, 85, 1500, 7));
        expectList.add(new Pearl(100.5, 15.62, 87, 5, 15));
        expectList.add(new Pearl(1000, 150.62, 90, 50, 17));
        expectList.add(new Pearl(1, 10, 100, 0, 0));
        Assert.assertTrue(result);
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldDoNotReadEmptyFile() {
        Service<Decor> serviceRepository = new DecorService(new DecorRepository());
        Path resourceDirectory = Paths.get("src","test","resources", "testEmpty.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        Assert.assertFalse(result);
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldReadFileSkipInvalidLinesAndCreateDecors() {
        Service<Decor> serviceRepository = new DecorService(new DecorRepository());
        Path resourceDirectory = Paths.get("src","test","resources", "testHalfValid.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        expectList.add(new Amber(150.432, 10.1923, 80, 0, 5));
        expectList.add(new Pearl(100.5, 15.62, 87, 5, 15));
        Assert.assertTrue(result);
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldDoNotReadNoExistFile() {
        Service<Decor> serviceRepository = new DecorService(new DecorRepository());
        Path resourceDirectory = Paths.get("src","test","resources", "noExist.txt");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        Assert.assertFalse(result);
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldDoNotReadFileWhenPathNull() {
        Service<Decor> serviceRepository = new DecorService(new DecorRepository());
        String path = null;
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        Assert.assertFalse(result);
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldDoNotReadDirectory() {
        Service<Decor> serviceRepository = new DecorService(new DecorRepository());
        Path resourceDirectory = Paths.get("src","test","resources");
        String path = resourceDirectory.toString();
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        Assert.assertFalse(result);
        Assert.assertEquals(expectList, actualList);
    }
}
