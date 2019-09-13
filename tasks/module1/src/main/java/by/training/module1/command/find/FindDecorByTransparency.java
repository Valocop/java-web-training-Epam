package by.training.module1.command.find;

import by.training.module1.command.Command;
import by.training.module1.entity.Decor;
import by.training.module1.service.DecorService;
import by.training.module1.service.exception.ServiceException;

import java.util.List;

public class FindDecorByTransparency implements Command {
    private DecorService service;
    private int transparency;

    public FindDecorByTransparency(DecorService service, int transparency) {
        this.service = service;
        this.transparency = transparency;
    }

    @Override
    public Object execute() {
        List<Decor> decors = null;
        try {
            decors = service.findDecorByTransparency(transparency);
        } catch (ServiceException e) {
//            log
        }
        return decors;
    }
}
