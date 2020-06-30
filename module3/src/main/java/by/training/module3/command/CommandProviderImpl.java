package by.training.module3.command;

import by.training.module3.entity.Medicine;

import java.util.HashMap;
import java.util.Map;

public class CommandProviderImpl implements CommandProvider<Medicine> {
    private Map<CommandType, Command<Medicine>> commandMap = new HashMap<>();

    @Override
    public Command<Medicine> getCommand(CommandType type) {
        return commandMap.get(type);
    }

    @Override
    public void addCommand(CommandType type, Command<Medicine> command) {
        commandMap.put(type, command);
    }
}
