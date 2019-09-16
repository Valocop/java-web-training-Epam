package by.training.module1.entity;

import java.util.Objects;

public abstract class NaturalDecor extends Decor {
    private double age;

    public NaturalDecor(double value, double weight, int transparency, double age) {
        super(value, weight, transparency, DecorType.NATURAL);
        this.age = age;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NaturalDecor that = (NaturalDecor) o;
        return Double.compare(that.age, age) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age);
    }

    @Override
    public String toString() {
        return super.toString() +
                " NaturalDecor{" +
                "age=" + age +
                '}';
    }
}
