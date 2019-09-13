package by.training.module1.service;

import by.training.module1.entity.Decor;
import by.training.module1.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DecorServiceImpl implements DecorService {
    private List<Decor> decors = new ArrayList<>();

    public void addDecor(Decor decor) {
        decors.add(decor);
    }

    public boolean removeDecor(Decor decor) {
        return decors.remove(decor);
    }

    @Override
    public double calculateTotalValue() throws ServiceException {
        double totalValue = 0;

        for (Decor decor : decors) {
            totalValue += decor.getValue();
        }
        return totalValue;
    }

    @Override
    public double calculateTotalWeight() throws ServiceException {
        double totalWeight = 0;

        for (Decor decor : decors) {
            totalWeight += decor.getWeight();
        }
        return totalWeight;
    }

    @Override
    public List<Decor> sortByValue(Comparator<Decor> comparator) throws ServiceException {
        decors.sort(comparator);
        return decors;
    }

    @Override
    public List<Decor> sortByWeight(Comparator<Decor> comparator) throws ServiceException {
        decors.sort(comparator);
        return decors;
    }

    @Override
    public List<Decor> sortByValueAndWeight(Comparator<Decor> comparator) throws ServiceException {
        decors.sort(comparator);
        return decors;
    }

    @Override
    public List<Decor> findDecorByTransparency(int transparency) throws ServiceException {
        List<Decor> decors = new ArrayList<>();

        for (Decor decor : this.decors) {
            if (decor.getTransparency() == transparency) {
                decors.add(decor);
            }
        }
        return decors;
    }
}
