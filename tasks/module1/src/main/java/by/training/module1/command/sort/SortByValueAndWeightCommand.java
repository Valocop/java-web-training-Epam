package by.training.module1.command.sort;

import by.training.module1.command.Command;
import by.training.module1.entity.Decor;
import by.training.module1.service.DecorService;
import by.training.module1.service.exception.ServiceException;

import java.util.Comparator;
import java.util.List;

public class SortByValueAndWeightCommand implements Command {
    private DecorService service;

    public SortByValueAndWeightCommand(DecorService service) {
        this.service = service;
    }

    @Override
    public List<Decor> execute() {
        List<Decor> decors = null;
        Comparator<Decor> decorComparator = Comparator.comparing(Decor::getValue).thenComparing(Decor::getWeight);
        try {
            decors = service.sortByValueAndWeight(decorComparator);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return decors;
    }
}
