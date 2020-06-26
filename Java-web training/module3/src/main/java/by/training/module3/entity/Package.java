package by.training.module3.entity;

import java.util.Objects;

public class Package {
    private PackageType type;
    private int count;
    private double value;

    public Package() {
    }

    public Package(PackageType type, int count, double value) {
        this.type = type;
        this.count = count;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return count == aPackage.count &&
                Double.compare(aPackage.value, value) == 0 &&
                type == aPackage.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, count, value);
    }

    @Override
    public String toString() {
        return "Package{" +
                "type=" + type +
                ", count=" + count +
                ", value=" + value +
                '}';
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
