package by.training.module1.entity;

import java.util.Objects;

public class Pearl extends ArtificalDecor {
    private double height;

    public Pearl(double value, double weight, int transparency, int color, double height) {
        super(value, weight, transparency, color);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pearl pearl = (Pearl) o;
        return Double.compare(pearl.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }

    @Override
    public String toString() {
        return super.toString() +
                " Pearl{" +
                "height=" + height +
                '}';
    }
}
