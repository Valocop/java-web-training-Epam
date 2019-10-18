package by.training.module3.entity;

import java.util.Objects;

public class Dosage {
    private double dosage;
    private int frequency;

    public Dosage() {
    }

    public Dosage(double dosage, int frequency) {
        this.dosage = dosage;
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dosage dosage1 = (Dosage) o;
        return Double.compare(dosage1.dosage, dosage) == 0 &&
                frequency == dosage1.frequency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dosage, frequency);
    }

    @Override
    public String toString() {
        return "Dosage{" +
                "dosage=" + dosage +
                ", frequency=" + frequency +
                '}';
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
