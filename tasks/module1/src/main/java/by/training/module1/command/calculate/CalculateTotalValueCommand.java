package by.training.module1.command.calculate;

import by.training.module1.command.Command;
import by.training.module1.service.DecorService;
import by.training.module1.service.exception.ServiceException;

public class CalculateTotalValueCommand implements Command {
    private DecorService service;

    public CalculateTotalValueCommand(DecorService service) {
        this.service = service;
    }

    @Override
    public Object execute() {
        double totalValue = 0;
        try {
            totalValue = service.calculateTotalValue();
        } catch (ServiceException e) {
//            log
        }
        return totalValue;
    }
}
