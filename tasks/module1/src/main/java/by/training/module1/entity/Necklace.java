package by.training.module1.entity;

import java.util.List;

public class Necklace {
    private String name;
    private List<Gemstone> gemstones;

    public List<Gemstone> getGemstones() {
        return gemstones;
    }

    public void setGemstones(List<Gemstone> gemstones) {
        this.gemstones = gemstones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
