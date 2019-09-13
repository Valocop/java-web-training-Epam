package by.training.module1.command;

import by.training.module1.command.calculate.CalculateTotalValueCommand;
import by.training.module1.command.calculate.CalculateTotalWeightCommand;
import by.training.module1.command.find.FindDecorByTransparency;
import by.training.module1.command.sort.SortByValueAndWeightCommand;
import by.training.module1.command.sort.SortByValueCommand;
import by.training.module1.command.sort.SortByWeightCommand;
import by.training.module1.service.DecorService;
import by.training.module1.service.ServiceFactory;

public final class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final DecorService decorService = serviceFactory.getDecorService();

    private CommandFactory() {}

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getByType(CommandType type) {
        switch (type) {
            case SORT_BY_VALUE:
                return new SortByValueCommand(decorService);
            case SORT_BY_WEIGHT:
                return new SortByWeightCommand(decorService);
            case SORT_BY_VALUE_AND_WEIGHT:
                return new SortByValueAndWeightCommand(decorService);
            case FIND_DECOR_BY_TRANSPARENCY:
                return new FindDecorByTransparency(decorService, 40);
            case CALCULATE_TOTAL_VALUE:
                return new CalculateTotalValueCommand(decorService);
            case CALCULATE_TOTAL_WEIGHT:
                return new CalculateTotalWeightCommand(decorService);
                default:
                    return () -> null;
        }
    }
}
