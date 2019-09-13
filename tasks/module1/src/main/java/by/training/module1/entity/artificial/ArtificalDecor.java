package by.training.module1.entity.artificial;

import by.training.module1.entity.Decor;
import by.training.module1.entity.DecorType;

public class ArtificalDecor extends Decor {
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
    public String toString() {
        return super.toString() +
                " ArtificalDecor{" +
                "color=" + color +
                '}';
    }
}
