package by.training.module3.service;

import by.training.module3.entity.Medicine;
import by.training.module3.repo.FindSpecification;
import by.training.module3.repo.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class MedicineService implements Service<Medicine> {
    private static final Logger LOG = LogManager.getLogger();
    private Repository<Medicine> repo;

    public MedicineService(Repository<Medicine> repo) {
        this.repo = repo;
    }

    @Override
    public long add(Medicine model) {
        LOG.info("Try to add medicine id [" + model.getId() + "].");
        return repo.create(model);
    }

    @Override
    public void update(Medicine model) {
        LOG.info("Try to update medicine id [" + model.getId() + "].");
        repo.update(model);
    }

    @Override
    public void remove(Medicine model) {
        LOG.info("Try to remove medicine id [" + model.getId() + "].");
        repo.remove(model);
    }

    @Override
    public List<Medicine> sort(Comparator<Medicine> comparator) {
        List<Medicine> medicines = repo.getAll();
        medicines.sort(comparator);
        return medicines;
    }

    @Override
    public List<Medicine> find(FindSpecification<Medicine> spec) {
        LOG.info("Try to find medicines by specification.");
        return repo.find(spec);
    }

    @Override
    public Medicine getById(long id) {
        LOG.info("Try to get medicine id [" + id + "].");
        return repo.read(id);
    }

    @Override
    public List<Medicine> getAll() {
        return repo.getAll();
    }
}
