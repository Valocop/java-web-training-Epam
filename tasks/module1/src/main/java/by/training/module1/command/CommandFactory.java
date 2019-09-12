package by.training.module1.command;

import by.training.module1.entity.Necklace;

public final class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();

    private CommandFactory() {}

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getByType(CommandType type) {
        switch (type) {
            case CALCULATE_VALUE:
                return new CalculateValueCommand(Necklace.necklace1);
            case CALCULATE_WEIGHT:
                return new CalculateWeightCommand(Necklace.necklace1);
            case SORT_BY_VALUE_DECREASE:
                return new SortValueDecreaseCommand(Necklace.necklace1);
            case SORT_BY_VALUE_INCREASE:
                return new SortValueIncreaseCommand(Necklace.necklace1);
            case SORT_BY_WEIGHT_DECREASE:
                return new SortWeightDecreaseCommand(Necklace.necklace1);
            case SORT_BY_WEIGHT_INCREASE:
                return new SortWeightIncreaseCommand(Necklace.necklace1);
            case FIND_GEMSTONES_BY_TRANSPARENCY:
                return new FindGemstonesTransparencyCommand(Necklace.necklace1, 80);
                default: return null; //?
        }
    }
}
