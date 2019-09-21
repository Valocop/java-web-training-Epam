package by.training.module1.service;

import by.training.module1.entity.Decor;
import by.training.module1.entity.Pearl;
import by.training.module1.entity.Amber;
import by.training.module1.repo.DecorRepository;
import by.training.module1.repo.MatchSpecification;
import by.training.module1.repo.MatchTransparencySpec;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)

public class DecorServiceTest {
    private static final double ERROR = 0.00001;
    private Service<Decor> decorService;
    private Decor pearl1 = new Pearl(100, 10, 85, 5, 15);
    private Decor pearl2 = new Pearl(1000, 50, 80, 5, 25);
    private Decor amber1 = new Amber(500, 5, 40, 1000, 10);
    private Decor amber2 = new Amber(1000.5923, 10, 30, 5000, 11);
    private Decor amber3 = new Amber(5000, 25, 40, 1000, 10);
    private Decor amber4 = new Amber(5000, 26, 70, 1000, 10);

    @Before
    public void signUp() {
        decorService = new DecorService(new DecorRepository());
        decorService.add(pearl1);
        decorService.add(pearl2);
        decorService.add(amber1);
        decorService.add(amber2);
        decorService.add(amber3);
        decorService.add(amber4);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectNullPointerExceptionFindByNull() {
        List<Decor> actualDecors = decorService.find(null);
    }

    @Test
    public void shouldFindDecorByTransparencyThirty() {
        MatchSpecification<Decor> matchSpecification = new MatchTransparencySpec(30);
        List<Decor> actualDecors = decorService.find(matchSpecification);
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(amber2);
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldFindDecorByTransparencyForty() {
        MatchSpecification<Decor> matchSpecification = new MatchTransparencySpec(40);
        List<Decor> actualDecors = decorService.find(matchSpecification);
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(amber1);
        expectDecors.add(amber3);
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldReturnEmptyListByTransparencyFifty() {
        MatchSpecification<Decor> matchSpecification = new MatchTransparencySpec(50);
        List<Decor> actualDecors = decorService.find(matchSpecification);
        List<Decor> expectDecors = new ArrayList<>();
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectNullPointerExceptionSortByValueSpec() {
        SortSpecification<Decor> sortSpecification = new SortByValueSpec(null);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
    }

    @Test
    public void shouldSortByValueIncrease() {
        SortSpecification<Decor> sortSpecification = new SortByValueSpec(SortType.INCREASE);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
        List<Decor> expectDecors = new ArrayList<>(decorService.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByValueDecrease() {
        SortSpecification<Decor> sortSpecification = new SortByValueSpec(SortType.DECREASE);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
        List<Decor> expectDecors = new ArrayList<>(decorService.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o2.getValue(), o1.getValue());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectNullPointerExceptionSortByWeightSpec() {
        SortSpecification<Decor> sortSpecification = new SortByWeightSpec(null);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectServiceExceptionSortByWeightSpec() {
        List<Decor> actualDecors = decorService.sort(null);
    }

    @Test
    public void shouldSortByWeightIncrease() {
        SortSpecification<Decor> sortSpecification = new SortByWeightSpec(SortType.INCREASE);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
        List<Decor> expectDecors = new ArrayList<>(decorService.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByWeightDecrease() {
        SortSpecification<Decor> sortSpecification = new SortByWeightSpec(SortType.DECREASE);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
        List<Decor> expectDecors = new ArrayList<>(decorService.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o2.getWeight(), o1.getWeight());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectNullPointerExceptionSortByValueAndWeightSpec() {
        SortSpecification<Decor> sortSpecification = new SortByValueAndWeightSpec(null);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
    }

    @Test
    public void shouldSortByValueAndWeightIncrease() {
        SortSpecification<Decor> sortSpecification = new SortByValueAndWeightSpec(SortType.INCREASE);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
        List<Decor> expectDecors = new ArrayList<>(decorService.getAll());
        expectDecors.sort(Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight));
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByValueAndWeightDecrease() {
        SortSpecification<Decor> sortSpecification = new SortByValueAndWeightSpec(SortType.DECREASE);
        List<Decor> actualDecors = decorService.sort(sortSpecification);
        List<Decor> expectDecors = new ArrayList<>(decorService.getAll());
        expectDecors.sort(Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight).reversed());
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectServiceExceptionCalculate() {
        Double actualResult = decorService.calcDouble(null);
    }

    @Test
    public void shouldCalculateTotalValueDecor() {
        CalculateDoubleSpecification<Decor> calculateDoubleSpecification = new CalculateValueSpec();
        Double actualResult = decorService.calcDouble(calculateDoubleSpecification);
        Double expectResult = 0d;
        for (Decor decor : decorService.getAll()) {
            expectResult += decor.getValue();
        }
        Assert.assertEquals(expectResult, actualResult, ERROR);
    }

    @Test
    public void shouldCalculateTotalWeightDecor() {
        CalculateDoubleSpecification<Decor> calculateDoubleSpecification = new CalculateWeightSpec();
        Double actualResult = decorService.calcDouble(calculateDoubleSpecification);
        Double expectResult = 0d;
        for (Decor decor : decorService.getAll()) {
            expectResult += decor.getWeight();
        }
        Assert.assertEquals(expectResult, actualResult, ERROR);
    }
}
