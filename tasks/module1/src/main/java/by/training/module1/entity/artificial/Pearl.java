package by.training.module1.entity.artificial;

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
    public String toString() {
        return super.toString() +
                " Pearl{" +
                "height=" + height +
                '}';
    }
}
