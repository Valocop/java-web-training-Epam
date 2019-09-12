package by.training.module1.command;

import by.training.module1.entity.Necklace;
import by.training.module1.service.NecklaceService;
import by.training.module1.service.ServiceFactory;
import by.training.module1.service.exception.ServiceException;

public class FindGemstonesTransparencyCommand implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private NecklaceService necklaceService = serviceFactory.getNecklaceService();
    private Necklace necklace;
    private int transparency;

    FindGemstonesTransparencyCommand(Necklace necklace, int transparency) {
        this.necklace = necklace;
        this.transparency = transparency;
    }

    @Override
    public void execute() {
        try {
            necklaceService.findGemstonesByTransparency(necklace, transparency);
        } catch (ServiceException e) {
//            log
            System.out.println(e.getMessage());
        }
    }
}
