package by.training.module1.service;

import by.training.module1.entity.Gemstone;
import by.training.module1.service.exception.ServiceException;

import java.util.List;

public interface GemstoneService {
    double calculateValue(List<Gemstone> gemstones) throws ServiceException;
    double calculateWeight(List<Gemstone> gemstones) throws ServiceException;
    List<Gemstone> sortValueDecrease(List<Gemstone> gemstones) throws ServiceException;
    List<Gemstone> sortValueIncrease(List<Gemstone> gemstones) throws ServiceException;
    List<Gemstone> sortWeightDecrease(List<Gemstone> gemstones) throws ServiceException;
    List<Gemstone> sortWeightIncrease(List<Gemstone> gemstones) throws ServiceException;
    List<Gemstone> findGemstonesByTransparency(List<Gemstone> gemstones, int transparency) throws ServiceException;
}
