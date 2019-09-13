package by.training.module1.entity.natural;

public class Amber extends NaturalDecor {
    private double size;

    public Amber(double value, double weight, int transparency, double age, double size) {
        super(value, weight, transparency, age);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
