package by.training.module1.command;

public final class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();

    private final Command sortValueDecreaseCommand = new SortValueDecreaseCommand();
    private final Command sortValueIncreaseCommand = new SortValueIncreaseCommand();
    private final Command calculateValueCommand = new CalculateValueCommand();
    private final Command calculateWeightCommand = new CalculateWeightCommand();
    private final Command sortWeightDecreaseCommand = new SortWeightDecreaseCommand();
    private final Command sortWeightIncreaseCommand = new SortWeightIncreaseCommand();
    private final Command findGemstonesTransparencyCommand = new FindGemstonesTransparencyCommand();

    private CommandFactory() {}

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getByType(CommandType type) {
        switch (type) {
            case CALCULATE_VALUE:
                return calculateValueCommand;
            case CALCULATE_WEIGHT:
                return calculateWeightCommand;
            case SORT_BY_VALUE_DECREASE:
                return sortValueDecreaseCommand;
            case SORT_BY_VALUE_INCREASE:
                return sortValueIncreaseCommand;
            case SORT_BY_WEIGHT_DECREASE:
                return sortWeightDecreaseCommand;
            case SORT_BY_WEIGHT_INCREASE:
                return sortWeightIncreaseCommand;
            case FIND_GEMSTONES_BY_TRANSPARENCY:
                return findGemstonesTransparencyCommand;
                default: return new Command() {
                    @Override
                    public void execute() {

                    }
                };
        }
    }
}
