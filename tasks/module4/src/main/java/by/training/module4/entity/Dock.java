package by.training.module4.entity;


import java.util.Objects;

public class Dock {
    private long id;
    private String name;

    public Dock(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dock dock = (Dock) o;
        return id == dock.id &&
                Objects.equals(name, dock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Dock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
