package by.training.module1.entity;

import java.util.Objects;

public class Amber extends NaturalDecor {
    private double size;

    public Amber(double value, double weight, int transparency, double age, double size) {
        super(value, weight, transparency, age);
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Amber amber = (Amber) o;
        return Double.compare(amber.size, size) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size);
    }

    @Override
    public String toString() {
        return super.toString() +
                " Amber{" +
                "size=" + size +
                '}';
    }
}
