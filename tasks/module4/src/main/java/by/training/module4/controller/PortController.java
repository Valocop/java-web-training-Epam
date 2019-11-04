package by.training.module4.controller;

import by.training.module4.builder.BuilderException;
import by.training.module4.command.Command;
import by.training.module4.command.CommandProvider;
import by.training.module4.command.CommandType;
import by.training.module4.entity.Container;
import by.training.module4.entity.Dock;
import by.training.module4.entity.Ship;
import by.training.module4.pool.PortPool;
import by.training.module4.pool.ResourceException;
import by.training.module4.pool.ShipAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class PortController {
    private static final Logger LOG = LogManager.getLogger();

    public boolean execute(CommandProvider<Ship> provider, CommandType type, String xmlPath,
                           LinkedList<Dock> docks, List<Container> containers, int capacity) {
        Command<Ship> command = provider.getCommand(type);
        List<Ship> ships;
        try {
            ships = command.execute(xmlPath);
        } catch (BuilderException e) {
            LOG.error(e);
            return false;
        }
        PortPool<Dock, Container> portPool = PortPool.getInstance();
        try {
            portPool.init(docks, containers, capacity);
        } catch (ResourceException e) {
            LOG.error(e);
            return false;
        }
        ExecutorService es = Executors.newFixedThreadPool(ships.size());
        List<Future<Boolean>> futureList = new ArrayList<>();
        for (Ship ship : ships) {
            ShipAction shipAction = new ShipAction(ship);
            futureList.add(es.submit(shipAction));
        }
        es.shutdown();
        Boolean result = true;
        for (Future<Boolean> future : futureList) {
            try {
                result &= future.get(2, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return result;
    }
}
