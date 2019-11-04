package by.training.module4.entity;

import by.training.module4.pool.PortPool;

import java.util.List;
import java.util.Objects;

public class Ship {
    private long id;
    private String name;
    private List<Container> containers;
    private PortPool<Dock, Container> portPool;
    private int capacity;

    public Ship() {
    }

    public Ship(long id, String name, List<Container> containers, PortPool<Dock, Container> portPool) {
        this.id = id;
        this.name = name;
        this.containers = containers;
        this.portPool = portPool;
        capacity = containers.size() + (int) (containers.size() * Math.random() * 10);
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPortPool(PortPool<Dock, Container> portPool) {
        this.portPool = portPool;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PortPool<Dock, Container> getPortPool() {
        return portPool;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return id == ship.id &&
                capacity == ship.capacity &&
                Objects.equals(name, ship.name) &&
                Objects.equals(containers, ship.containers) &&
                Objects.equals(portPool, ship.portPool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, containers, portPool, capacity);
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", containers=" + containers +
                ", portPool=" + portPool +
                ", CAPACITY=" + capacity +
                '}';
    }
}
