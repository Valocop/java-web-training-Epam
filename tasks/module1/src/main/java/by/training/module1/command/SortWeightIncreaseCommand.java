package by.training.module1.command;

import by.training.module1.entity.Necklace;
import by.training.module1.service.NecklaceService;
import by.training.module1.service.ServiceFactory;
import by.training.module1.service.exception.ServiceException;

public class SortWeightIncreaseCommand implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private NecklaceService necklaceService = serviceFactory.getNecklaceService();
    private Necklace necklace;

    SortWeightIncreaseCommand(Necklace necklace) {
        this.necklace = necklace;
    }

    @Override
    public void execute() {
        try {
            necklaceService.sortWeightIncrease(necklace);
        } catch (ServiceException e) {
//            log
            System.out.println(e.getMessage());
        }
    }
}
