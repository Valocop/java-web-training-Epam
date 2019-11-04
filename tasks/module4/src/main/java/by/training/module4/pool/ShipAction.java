package by.training.module4.pool;

import by.training.module4.entity.Container;
import by.training.module4.entity.Dock;
import by.training.module4.entity.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class ShipAction implements Callable<Boolean> {
    private static final Logger LOG = LogManager.getLogger();
    private Ship ship;

    public ShipAction(Ship ship) {
        this.ship = ship;
    }

    private void giveContainer() {
        List<Container> containers = ship.getContainers();
        if (!containers.isEmpty()) {
            List<Container> list = new ArrayList<>(containers.subList(0, containers.size() / 2 + 1));
            try {
                LOG.info(ship.getName() + " try to give " + list.size() + " containers to Port.");
                ship.getPortPool().createItems(list);
                containers.removeAll(list);
                ship.setContainers(containers);
                LOG.info(ship.getName() + " add " + list.size() + " containers to Port.");
            } catch (ResourceException e) {
                LOG.info(ship.getName() + " can't add " + list.size() + " containers to Port. " + e.getMessage());
            }
        } else {
            LOG.info(ship.getName() + " is empty.");
        }
    }

    private void receiveContainer() {
        int capacity = ship.getCapacity();
        List<Container> containers = ship.getContainers();
        if ((capacity - containers.size()) > 0) {
            int count = new Random().nextInt((capacity - containers.size()) / 2);
            try {
                LOG.info(ship.getName() + " try to receive " + count + " containers from Port.");
                List<Container> list = ship.getPortPool().getItems(count);
                containers.addAll(list);
                ship.setContainers(containers);
                LOG.info(ship.getName() + " receive " + list.size() + " containers from Port.");
            } catch (ResourceException e) {
                LOG.info(ship.getName() + " can't receive " +
                        count + " containers from Port. " + e.getMessage());
            }
        } else {
            LOG.info(ship.getName() + " is crowded.");
        }
    }

    @Override
    public Boolean call() throws Exception {
        LOG.info(ship.getName() + " starts to work.");
        for (int i = 0; i < 2; i++) {
            Dock dock;
            try {
                dock = ship.getPortPool().getResource();
            } catch (ResourceException e) {
                LOG.error(ship.getName() + " can't connect to Dock. " + e.getMessage());
                return false;
            }
            if (dock != null) {
                LOG.info(ship.getName() + " connect to " + dock.getName());
                if (new Random().nextBoolean()) {
                    giveContainer();
                } else {
                    receiveContainer();
                }
                ship.getPortPool().returnResource(dock);
                LOG.info(ship.getName() + " disconnect from " + dock.getName());
            }
            else {
                LOG.error(ship.getName() + " can't connect to Dock.");
                return false;
            }
        }
        LOG.info(ship.getName() + " finished to work.");
        return true;
    }
}
