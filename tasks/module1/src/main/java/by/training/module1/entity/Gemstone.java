package by.training.module1.entity;

public class Gemstone {
    private String gemType;
    private String gemName;
    private double value;
    private double weight;
    private int transparency;

    Gemstone(String gemName, String gemType, double value, double weight, int transparency) {
        this.gemName = gemName;
        this.gemType = gemType;
        this.value = value;
        this.weight = weight;
        this.transparency = transparency;
    }

    public String getGemType() {
        return gemType;
    }

    public void setGemType(String gemType) {
        this.gemType = gemType;
    }

    public String getGemName() {
        return gemName;
    }

    public void setGemName(String gemName) {
        this.gemName = gemName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }
}
