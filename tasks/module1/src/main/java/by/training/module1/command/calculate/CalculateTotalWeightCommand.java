package by.training.module1.command.calculate;

import by.training.module1.command.Command;
import by.training.module1.service.DecorService;
import by.training.module1.service.exception.ServiceException;

public class CalculateTotalWeightCommand implements Command {
    private DecorService service;

    public CalculateTotalWeightCommand(DecorService service) {
        this.service = service;
    }

    @Override
    public Object execute() {
        double totalWeight = 0;
        try {
            totalWeight = service.calculateTotalWeight();
        } catch (ServiceException e) {
//            log
        }
        return totalWeight;
    }
}
