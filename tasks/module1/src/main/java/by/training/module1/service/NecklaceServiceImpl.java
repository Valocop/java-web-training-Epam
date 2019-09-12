package by.training.module1.service;

import by.training.module1.entity.Gemstone;
import by.training.module1.entity.Necklace;
import by.training.module1.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NecklaceServiceImpl implements NecklaceService {
    private List<Gemstone> gemstones;

    @Override
    public double calculateValue(Necklace necklace) throws ServiceException {
        if (!containGemstones(necklace)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        double sumValue = 0;

        for (Gemstone gemstone : necklace.getGemstones()) {
// exception?
            sumValue += gemstone.getValue();
        }
        return sumValue;
    }

    @Override
    public double calculateWeight(Necklace necklace) throws ServiceException {
        if (!containGemstones(necklace)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        double sumWeight = 0;

        for (Gemstone gemstone : necklace.getGemstones()) {
// exception?
            sumWeight += gemstone.getWeight();
        }
        return sumWeight;
    }

    @Override
    public List<Gemstone> sortValueDecrease(Necklace necklace) throws ServiceException {

        if (!containGemstones(necklace)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }
        this.gemstones = new ArrayList<>(sortValueIncrease(necklace));
        Collections.reverse(this.gemstones);
        return this.gemstones;
    }

    @Override
    public List<Gemstone> sortValueIncrease(Necklace necklace) throws ServiceException {
        if (!containGemstones(necklace)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = new ArrayList<>(necklace.getGemstones());
        this.gemstones.sort(new Comparator<Gemstone>() {
            public int compare(Gemstone o1, Gemstone o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        return this.gemstones;
    }

    @Override
    public List<Gemstone> sortWeightDecrease(Necklace necklace) throws ServiceException {
        if (!containGemstones(necklace)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = sortWeightIncrease(necklace);
        Collections.reverse(this.gemstones);
        return this.gemstones;
    }

    @Override
    public List<Gemstone> sortWeightIncrease(Necklace necklace) throws ServiceException {
        if (!containGemstones(necklace)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        this.gemstones = new ArrayList<>(necklace.getGemstones());
        this.gemstones.sort(new Comparator<Gemstone>() {
            @Override
            public int compare(Gemstone o1, Gemstone o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        });
        return this.gemstones;
    }

    @Override
    public List<Gemstone> findGemstonesByTransparency(Necklace necklace, int transparency) throws ServiceException {
        if (!containGemstones(necklace)) {
            throw new ServiceException("Necklace does not contain gemstones");
        }

        if (transparency < 0) {
            throw new ServiceException("Transparency bellow zero");
        }

        this.gemstones = new ArrayList<>(necklace.getGemstones());

        for (Gemstone gemstone : this.gemstones) {
            if (gemstone.getTransparency() != transparency) {
                this.gemstones.remove(gemstone);
            }
        }
        return this.gemstones;
    }

    private boolean containGemstones(Necklace necklace) {

        if (necklace.getGemstones() != null && !necklace.getGemstones().isEmpty()) {
            return true;
        } else
            return false;
    }
}
