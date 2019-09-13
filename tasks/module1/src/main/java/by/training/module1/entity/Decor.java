package by.training.module1.entity;

public abstract class Decor {
    private double value;
    private double weight;
    private int transparency;
    private DecorType decorType;

    public Decor(double value, double weight, int transparency, DecorType decorType) {
        this.value = value;
        this.weight = weight;
        this.transparency = transparency;
        this.decorType = decorType;
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

    public DecorType getDecorType() {
        return decorType;
    }

    public void setDecorType(DecorType decorType) {
        this.decorType = decorType;
    }

    @Override
    public String toString() {
        return "Decor{" +
                "value=" + value +
                ", weight=" + weight +
                ", transparency=" + transparency +
                ", decorType=" + decorType +
                '}';
    }
}
