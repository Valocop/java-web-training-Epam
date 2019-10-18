package by.training.module3.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Medicine {
    private long id;
    private String name;
    private List<Pharm> pharms = new ArrayList<>();
    private MedicineType type;
    private List<String> analogs = new ArrayList<>();
    private MedicineVersion version;

    public Medicine() {
    }

    public Medicine(long id, String name, List<Pharm> pharms, MedicineType type,
                    List<String> analogs, MedicineVersion version) {
        this.id = id;
        this.name = name;
        this.pharms = pharms;
        this.type = type;
        this.analogs = analogs;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return id == medicine.id &&
                Objects.equals(name, medicine.name) &&
                Objects.equals(pharms, medicine.pharms) &&
                type == medicine.type &&
                Objects.equals(analogs, medicine.analogs) &&
                version == medicine.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pharms, type, analogs, version);
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pharms=" + pharms +
                ", type=" + type +
                ", analogs=" + analogs +
                ", version=" + version +
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

    public List<Pharm> getPharms() {
        return pharms;
    }

    public void addPharm(Pharm pharm) {
        pharms.add(pharm);
    }

    public void setPharms(List<Pharm> pharms) {
        this.pharms = pharms;
    }

    public MedicineType getType() {
        return type;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }

    public List<String> getAnalogs() {
        return analogs;
    }

    public void addAnalog(String analog) {
        this.analogs.add(analog);
    }

    public void setAnalogs(List<String> analogs) {
        this.analogs = analogs;
    }

    public MedicineVersion getVersion() {
        return version;
    }

    public void setVersion(MedicineVersion version) {
        this.version = version;
    }
}
