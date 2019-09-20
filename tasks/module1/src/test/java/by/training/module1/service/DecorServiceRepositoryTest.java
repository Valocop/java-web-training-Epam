package by.training.module1.service;

import by.training.module1.entity.Decor;
import by.training.module1.entity.Pearl;
import by.training.module1.entity.Amber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)

public class DecorServiceRepositoryTest {
    private static final double ERROR = 0.00001;
    private DecorRepository<Decor> serviceRepository;
    private Decor pearl1 = new Pearl(100, 10, 85, 5, 15);
    private Decor pearl2 = new Pearl(1000, 50, 80, 5, 25);
    private Decor amber1 = new Amber(500, 5, 40, 1000, 10);
    private Decor amber2 = new Amber(1000.5923, 10, 30, 5000, 11);
    private Decor amber3 = new Amber(5000, 25, 40, 1000, 10);
    private Decor amber4 = new Amber(5000, 26, 70, 1000, 10);

    @Before
    public void signUp() {
        serviceRepository = new DecorServiceRepository();
        serviceRepository.add(pearl1);
        serviceRepository.add(pearl2);
        serviceRepository.add(amber1);
        serviceRepository.add(amber2);
        serviceRepository.add(amber3);
        serviceRepository.add(amber4);
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowServiceException() throws ServiceException {
        List<Decor> actualDecors = serviceRepository.find(null);
    }

    @Test
    public void shouldFindDecorByTransparencyThirty() throws ServiceException {
        DecorMatchSpecification<Decor> decorMatchSpecification = new DecorMatchTransparencySpec(30);
        List<Decor> actualDecors = serviceRepository.find(decorMatchSpecification);
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(amber2);
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldFindDecorByTransparencyForty() throws ServiceException {
        DecorMatchSpecification<Decor> decorMatchSpecification = new DecorMatchTransparencySpec(40);
        List<Decor> actualDecors = serviceRepository.find(decorMatchSpecification);
        List<Decor> expectDecors = new ArrayList<>();
        expectDecors.add(amber1);
        expectDecors.add(amber3);
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldReturnEmptyListByTransparencyFifty() throws ServiceException {
        DecorMatchSpecification<Decor> decorMatchSpecification = new DecorMatchTransparencySpec(50);
        List<Decor> actualDecors = serviceRepository.find(decorMatchSpecification);
        List<Decor> expectDecors = new ArrayList<>();
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectNullPointerExceptionSortByValueSpec() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByValueSpec(null);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
    }

    @Test
    public void shouldSortByValueIncrease() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByValueSpec(SortType.INCREASE);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
        List<Decor> expectDecors = new ArrayList<>(serviceRepository.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByValueDecrease() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByValueSpec(SortType.DECREASE);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
        List<Decor> expectDecors = new ArrayList<>(serviceRepository.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o2.getValue(), o1.getValue());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectNullPointerExceptionSortByWeightSpec() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByWeightSpec(null);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
    }

    @Test(expected = ServiceException.class)
    public void shouldExpectServiceExceptionSortByWeightSpec() throws ServiceException {
        List<Decor> actualDecors = serviceRepository.sort(null);
    }

    @Test
    public void shouldSortByWeightIncrease() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByWeightSpec(SortType.INCREASE);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
        List<Decor> expectDecors = new ArrayList<>(serviceRepository.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByWeightDecrease() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByWeightSpec(SortType.DECREASE);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
        List<Decor> expectDecors = new ArrayList<>(serviceRepository.getAll());
        expectDecors.sort(new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o2.getWeight(), o1.getWeight());
            }
        });
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = NullPointerException.class)
    public void shouldExpectNullPointerExceptionSortByValueAndWeightSpec() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByValueAndWeightSpec(null);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
    }

    @Test
    public void shouldSortByValueAndWeightIncrease() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByValueAndWeightSpec(SortType.INCREASE);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
        List<Decor> expectDecors = new ArrayList<>(serviceRepository.getAll());
        expectDecors.sort(Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight));
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test
    public void shouldSortByValueAndWeightDecrease() throws ServiceException {
        DecorSortSpecification<Decor> decorSortSpecification = new DecorSortByValueAndWeightSpec(SortType.DECREASE);
        List<Decor> actualDecors = serviceRepository.sort(decorSortSpecification);
        List<Decor> expectDecors = new ArrayList<>(serviceRepository.getAll());
        expectDecors.sort(Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight).reversed());
        Assert.assertEquals(expectDecors, actualDecors);
    }

    @Test(expected = ServiceException.class)
    public void shouldExpectServiceExceptionCalculate() throws ServiceException {
        Double actualResult = serviceRepository.calculate(null);
    }

    @Test
    public void shouldCalculateTotalValueDecor() throws ServiceException {
        DecorCalculateSpecification<Decor> calculateSpecification = new DecorCalculateValueSpec();
        Double actualResult = serviceRepository.calculate(calculateSpecification);
        Double expectResult = 0d;
        for (Decor decor : serviceRepository.getAll()) {
            expectResult += decor.getValue();
        }
        Assert.assertEquals(expectResult, actualResult, ERROR);
    }

    @Test
    public void shouldCalculateTotalWeightDecor() throws ServiceException {
        DecorCalculateSpecification<Decor> calculateSpecification = new DecorCalculateWeightSpec();
        Double actualResult = serviceRepository.calculate(calculateSpecification);
        Double expectResult = 0d;
        for (Decor decor : serviceRepository.getAll()) {
            expectResult += decor.getWeight();
        }
        Assert.assertEquals(expectResult, actualResult, ERROR);
    }
}
