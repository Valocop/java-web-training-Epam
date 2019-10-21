package by.training.module3.entity;

import java.util.Objects;

public class Pharm {
    private long id;
    private String name;
    private Certificate certificate;
    private Package aPackage;
    private Dosage dosage;

    public Pharm() {
    }

    public Pharm(long id, String name, Certificate certificate, Package aPackage, Dosage dosage) {
        this.id = id;
        this.name = name;
        this.certificate = certificate;
        this.aPackage = aPackage;
        this.dosage = dosage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharm pharm = (Pharm) o;
        return id == pharm.id &&
                Objects.equals(name, pharm.name) &&
                Objects.equals(certificate, pharm.certificate) &&
                Objects.equals(aPackage, pharm.aPackage) &&
                Objects.equals(dosage, pharm.dosage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, certificate, aPackage, dosage);
    }

    @Override
    public String toString() {
        return "Pharm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", certificate=" + certificate +
                ", aPackage=" + aPackage +
                ", dosage=" + dosage +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }
}
