package by.training.module1.service;

import by.training.module1.entity.Gemstone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GemtoneServiceImpl implements GemstoneService {
    private List<Gemstone> gemstones;

    public List<Gemstone> sortValue(List<Gemstone> gemstones) {
        this.gemstones = new ArrayList<>(gemstones);
        this.gemstones.sort(new Comparator<Gemstone>() {
            public int compare(Gemstone o1, Gemstone o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });
        return this.gemstones;
    }

    public List<Gemstone> sortWeight(List<Gemstone> gemstones) {
        this.gemstones = new ArrayList<>(gemstones);
        this.gemstones.sort(new Comparator<Gemstone>() {
            @Override
            public int compare(Gemstone o1, Gemstone o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        });
        return this.gemstones;
    }

    public double sumValue(List<Gemstone> gemstones) {
        this.gemstones = new ArrayList<>(gemstones);
        double sumValue = 0;

        for (Gemstone gemstone : this.gemstones) {
            sumValue += gemstone.getValue();
        }
        return sumValue;
    }

    public List<Gemstone> findGemstonesByTransparency(List<Gemstone> gemstones, int transparency) {
        this.gemstones = new ArrayList<>(gemstones);

        for (Gemstone gemstone : this.gemstones) {
            if (gemstone.getTransparency() != transparency) {
                this.gemstones.remove(gemstone);
            }
        }
        return this.gemstones;
    }
}
