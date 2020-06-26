package by.training.module1.entity;

import java.util.Objects;

public abstract class ArtificalDecor extends Decor {
    private int color;

    public ArtificalDecor(double value, double weight, int transparency, int color) {
        super(value, weight, transparency, DecorType.ARTIFICIAL);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArtificalDecor that = (ArtificalDecor) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }

    @Override
    public String toString() {
        return super.toString() +
                " ArtificalDecor{" +
                "color=" + color +
                '}';
    }
}
