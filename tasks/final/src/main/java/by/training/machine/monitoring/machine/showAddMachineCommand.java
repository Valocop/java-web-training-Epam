package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Bean(name = "showAddMachine")
public class showAddMachineCommand implements ServletCommand {
    private MachineService machineService;

    public showAddMachineCommand(MachineService machineService) {

    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {

    }
}
