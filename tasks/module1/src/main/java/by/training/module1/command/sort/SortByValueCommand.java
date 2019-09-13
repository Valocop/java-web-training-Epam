package by.training.module1.command.sort;

import by.training.module1.command.Command;
import by.training.module1.entity.Decor;
import by.training.module1.service.DecorService;
import by.training.module1.service.exception.ServiceException;

import java.util.Comparator;
import java.util.List;

public class SortByValueCommand implements Command {
    private DecorService service;

    public SortByValueCommand(DecorService service) {
        this.service = service;
    }

    @Override
    public List<Decor> execute() {
        List<Decor> decors = null;
        Comparator<Decor> decorComparator = new Comparator<Decor>() {
            @Override
            public int compare(Decor o1, Decor o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        };
        try {
            decors = service.sortByValue(decorComparator);
        } catch (ServiceException e) {
//            log
        }
        return decors;
    }
}
