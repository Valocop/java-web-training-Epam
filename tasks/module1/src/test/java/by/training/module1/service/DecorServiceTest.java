package by.training.module1.service;

import by.training.module1.command.Command;
import by.training.module1.command.CommandFactory;
import by.training.module1.command.CommandType;
import by.training.module1.entity.Decor;
import by.training.module1.entity.artificial.Pearl;
import by.training.module1.entity.natural.Amber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class DecorServiceTest {
    private static final double ERROR = 0.00001;
    private CommandFactory commandFactory = CommandFactory.getInstance();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DecorService decorService = serviceFactory.getDecorService();
    private Decor pearl1 = new Pearl(100, 10, 85, 5, 15);
    private Decor pearl2 = new Pearl(1000, 50, 80, 5, 25);
    private Decor amber1 = new Amber(500, 5, 40, 1000, 10);
    private Decor amber2 = new Amber(1000.5923, 10, 30, 5000, 11);

    @Before
    public void setUp() {
        decorService.addDecor(pearl1);
        decorService.addDecor(pearl2);
        decorService.addDecor(amber1);
        decorService.addDecor(amber2);
    }

    @Test
    public void shouldCalculateTotalValue() {
        Command command = commandFactory.getByType(CommandType.CALCULATE_TOTAL_VALUE);
        double actualTotalValue = (double) command.execute();
        double expectTotalValue = pearl1.getValue() + pearl2.getValue() + amber1.getValue() + amber2.getValue();
        assertEquals(expectTotalValue, actualTotalValue, ERROR);
    }

    @Test
    public void shouldCalculateTotalWeight() {
        Command command = commandFactory.getByType(CommandType.CALCULATE_TOTAL_WEIGHT);
        double actualTotalWeight = (double) command.execute();
        double expectTotalWeight = pearl1.getWeight() + pearl2.getWeight() + amber1.getWeight() + amber2.getWeight();
        assertEquals(expectTotalWeight, actualTotalWeight, ERROR);
    }

    @Test
    public void shouldFindDecorByTransparency() {
        Command command = commandFactory.getByType(CommandType.FIND_DECOR_BY_TRANSPARENCY);
        List<Decor> actualDecors = (List<Decor>) command.execute();
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(amber1);
        assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByValue() {
        Command command = commandFactory.getByType(CommandType.SORT_BY_VALUE);
        List<Decor> actualDecors = (List<Decor>) command.execute();
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(pearl1);
        expectDecors.add(pearl2);
        expectDecors.add(amber1);
        expectDecors.add(amber2);
        expectDecors.sort(Comparator.comparing(Decor::getValue));
        assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByWeight() {
        Command command = commandFactory.getByType(CommandType.SORT_BY_WEIGHT);
        List<Decor> actualDecors = (List<Decor>) command.execute();
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(pearl1);
        expectDecors.add(pearl2);
        expectDecors.add(amber1);
        expectDecors.add(amber2);
        expectDecors.sort(Comparator.comparing(Decor::getWeight));
        assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByValueAndWeight() {
        Command command = commandFactory.getByType(CommandType.SORT_BY_VALUE_AND_WEIGHT);
        List<Decor> actualDecors = (List<Decor>) command.execute();
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(pearl1);
        expectDecors.add(pearl2);
        expectDecors.add(amber1);
        expectDecors.add(amber2);
        expectDecors.sort(Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight));
        assertEquals(expectDecors, actualDecors);
    }

    @After
    public void setDown() {
        decorService.removeDecor(pearl1);
        decorService.removeDecor(pearl2);
        decorService.removeDecor(amber1);
        decorService.removeDecor(amber2);
    }
}
