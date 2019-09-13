package by.training.module1.service;

import by.training.module1.entity.Decor;
import by.training.module1.service.exception.ServiceException;

import java.util.Comparator;
import java.util.List;

public interface DecorService {
    boolean addDecor(Decor decor);
    boolean removeDecor(Decor decor);
    List<Decor> getAllDecor();
    double calculateTotalValue() throws ServiceException;
    double calculateTotalWeight() throws ServiceException;
    List<Decor> sortByValue(Comparator<Decor> comparator) throws ServiceException;
    List<Decor> sortByWeight(Comparator<Decor> comparator) throws ServiceException;
    List<Decor> sortByValueAndWeight(Comparator<Decor> comparator) throws ServiceException;
    List<Decor> findDecorByTransparency(int transparency) throws ServiceException;
}
