package by.training.module1.service;

import by.training.module1.entity.Gemstone;
import by.training.module1.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GemstoneServiceImpl implements GemstoneService {
    private List<Gemstone> gemstones;

    @Override
    public double calculateValue(List<Gemstone> gemstones) throws ServiceException {

        if (!containGemstones(gemstones)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = new ArrayList<>(gemstones);
        double sumValue = 0;

        for (Gemstone gemstone : this.gemstones) {
//            if (gemstone.getValue() < 0) {
//                throw new ServiceException("Incorrect value of gemstone " + gemstone.getGemName());
//            }
            sumValue += gemstone.getValue();
        }
        return sumValue;
    }

    @Override
    public double calculateWeight(List<Gemstone> gemstones) throws ServiceException {

        if (!containGemstones(gemstones)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = new ArrayList<>(gemstones);
        double sumWeight = 0;

        for (Gemstone gemstone : this.gemstones) {
//            if (gemstone.getWeight() <= 0) {
//                throw new ServiceException("Incorrect weight of gemstone " + gemstone.getGemName());
//            }
            sumWeight += gemstone.getWeight();
        }
        return sumWeight;
    }

    @Override
    public List<Gemstone> sortValueDecrease(List<Gemstone> gemstones) throws ServiceException {

        if (!containGemstones(gemstones)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = sortValueIncrease(gemstones);
        Collections.reverse(this.gemstones);
        return this.gemstones;
    }

    @Override
    public List<Gemstone> sortValueIncrease(List<Gemstone> gemstones) throws ServiceException {

        if (!containGemstones(gemstones)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = new ArrayList<>(gemstones);
        this.gemstones.sort(Comparator.comparingDouble(Gemstone::getValue));
        return this.gemstones;
    }

    @Override
    public List<Gemstone> sortWeightDecrease(List<Gemstone> gemstones) throws ServiceException {

        if (!containGemstones(gemstones)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = sortWeightIncrease(gemstones);
        Collections.reverse(this.gemstones);
        return this.gemstones;
    }

    @Override
    public List<Gemstone> sortWeightIncrease(List<Gemstone> gemstones) throws ServiceException {

        if (!containGemstones(gemstones)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = new ArrayList<>(gemstones);
        this.gemstones.sort(Comparator.comparingDouble(Gemstone::getWeight));
        return this.gemstones;
    }

    public List<Gemstone> findGemstonesByTransparency(List<Gemstone> gemstones, int transparency) throws ServiceException {

        if (!containGemstones(gemstones)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        if (transparency < 0) {
            throw new ServiceException("Transparency bellow zero");
        }

        this.gemstones = new ArrayList<>(gemstones);

        for (Gemstone gemstone : this.gemstones) {
            if (gemstone.getTransparency() != transparency) {
                this.gemstones.remove(gemstone);
            }
        }
        return this.gemstones;
    }

    private boolean containGemstones(List<Gemstone> gemstones) {
        return gemstones != null && !gemstones.isEmpty();
    }
}
