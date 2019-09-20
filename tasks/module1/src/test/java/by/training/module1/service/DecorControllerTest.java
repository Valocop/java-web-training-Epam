package by.training.module1.service;

import by.training.module1.controller.DecorController;
import by.training.module1.entity.Amber;
import by.training.module1.entity.Decor;
import by.training.module1.entity.Pearl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)

public class DecorControllerTest {

    @Test
    public void shouldReadFileAndCreateDecors() {
        DecorServiceRepository serviceRepository = new DecorServiceRepository();
        String path = "src\\main\\resources\\test1.txt";
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
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
        DecorServiceRepository serviceRepository = new DecorServiceRepository();
        String path = "src\\main\\resources\\test2.txt";
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        Assert.assertFalse(result);
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldReadFileSkipInvalidLinesAndCreateDecors() {
        DecorServiceRepository serviceRepository = new DecorServiceRepository();
        String path = "src\\main\\resources\\test3.txt";
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
        DecorServiceRepository serviceRepository = new DecorServiceRepository();
        String path = "src\\main\\resources\\testTest.txt";
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        Assert.assertFalse(result);
        Assert.assertEquals(expectList, actualList);
    }

    @Test
    public void shouldDoNotReadFileWhenPathNull() {
        DecorServiceRepository serviceRepository = new DecorServiceRepository();
        String path = null;
        DecorController decorController = new DecorController(serviceRepository, path);
        boolean result = decorController.process();
        List<Decor> actualList = serviceRepository.getAll();
        List<Decor> expectList = new ArrayList<>();
        Assert.assertFalse(result);
        Assert.assertEquals(expectList, actualList);
    }
}
