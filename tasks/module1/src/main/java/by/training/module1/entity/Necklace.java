package by.training.module1.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Necklace {

    private final static Gemstone gemstone1 = new Gemstone("Янтарь", "натуральный", 100, 5, 20);
    private final static Gemstone gemstone2 = new Gemstone("Жемчуг", "натуральный", 150, 2, 80);
    private final static Gemstone gemstone3 = new Gemstone("Алмаз", "натуральный", 500, 1, 90);
    private final static Gemstone gemstone4 = new Gemstone("Стекло", "искуственный", 10, 10, 97);
    public final static Necklace necklace1 = new Necklace("Ожерелье1", gemstone1, gemstone2, gemstone3, gemstone4);

    private String name;
    private List<Gemstone> gemstones;

    public Necklace(String name, Gemstone... gemstones) {
        this.name = name;
        this.gemstones = new ArrayList<>();
        Collections.addAll(this.gemstones, gemstones);
    }

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
