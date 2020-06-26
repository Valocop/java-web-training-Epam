package by.training.module4.controller;

import by.training.module4.builder.Builder;
import by.training.module4.builder.ShipDomBuilder;
import by.training.module4.command.*;
import by.training.module4.entity.Container;
import by.training.module4.entity.Dock;
import by.training.module4.entity.Ship;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RunWith(JUnit4.class)
public class PortControllerTest {
    private PortController portController;
    private CommandProvider<Ship> commandProvider;
    private LinkedList<Dock> docks = new LinkedList<>();

    @Before
    public void init() {
        Builder<Ship> DOMBuilder = new ShipDomBuilder();
        Command<Ship> DOMParseCommand = new DOMShipParseCommand(DOMBuilder);
        commandProvider = new ShipCommandProvider();
        commandProvider.addCommand(CommandType.DOM_PARSE_COMMAND, DOMParseCommand);
        portController = new PortController();
        docks.add(new Dock(1, "Dock #1"));
        docks.add(new Dock(2, "Dock #2"));
        docks.add(new Dock(3, "Dock #3"));
        docks.add(new Dock(4, "Dock #4"));
    }

    @Test
    public void shouldReturnTrue() {
        String XMLPath = Paths.get("src", "test", "resources", "validData.xml").toString();
        boolean result = portController.execute(commandProvider, CommandType.DOM_PARSE_COMMAND, XMLPath,
                docks, getContainers(40), 100);
        Assert.assertTrue(result);
    }

    private List<Container> getContainers(int count) {
        List<Container> containers = new ArrayList<>();
        for (int i = 0; i < count; i ++) {
            containers.add(new Container(i, "Container " + i));
        }
        return containers;
    }
}
