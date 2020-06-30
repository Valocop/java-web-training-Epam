package by.training.module4.command;

import by.training.module4.entity.Ship;

import java.util.HashMap;
import java.util.Map;

public class ShipCommandProvider implements CommandProvider<Ship> {
    private Map<CommandType, Command<Ship>> commandMap = new HashMap<>();

    @Override
    public Command<Ship> getCommand(CommandType commandType) {
        return commandMap.get(commandType);
    }

    @Override
    public void addCommand(CommandType type, Command<Ship> command) {
        commandMap.put(type, command);
    }
}
