package by.training.module1.entity.natural;

import by.training.module1.entity.Decor;
import by.training.module1.entity.DecorType;

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
    public String toString() {
        return super.toString() +
                " NaturalDecor{" +
                "age=" + age +
                '}';
    }
}
