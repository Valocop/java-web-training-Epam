package by.training.module1.service;

import by.training.module1.entity.Gemstone;
import by.training.module1.entity.Necklace;
import by.training.module1.service.exception.ServiceException;

import java.util.List;

public interface NecklaceService {
    double calculateValue(Necklace necklace) throws ServiceException;
    double calculateWeight(Necklace necklace) throws ServiceException;
    List<Gemstone> sortValueDecrease(Necklace necklace) throws ServiceException;
    List<Gemstone> sortValueIncrease(Necklace necklace) throws ServiceException;
    List<Gemstone> sortWeightDecrease(Necklace necklace) throws ServiceException;
    List<Gemstone> sortWeightIncrease(Necklace necklace) throws ServiceException;
    List<Gemstone> findGemstonesByTransparency(Necklace necklace, int transparency) throws ServiceException;
}
