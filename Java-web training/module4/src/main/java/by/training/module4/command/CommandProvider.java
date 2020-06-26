package by.training.module4.command;

public interface CommandProvider<T> {
    Command<T> getCommand(CommandType commandType);
    void addCommand(CommandType type, Command<T> command);
}
