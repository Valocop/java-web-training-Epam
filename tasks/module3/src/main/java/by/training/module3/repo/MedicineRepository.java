package by.training.module3.repo;

import by.training.module3.entity.Medicine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MedicineRepository implements Repository<Medicine> {
    private static final AtomicLong ID = new AtomicLong(1);
    private static final Logger LOG = LogManager.getLogger();
    private List<Medicine> medicines = new ArrayList<>();

    @Override
    public long create(Medicine model) {
        long medId = model.getId();
        if (idIsPresent(medId) && medId != 0) {
            update(model);
            return medId;
        } else {
            if (medId <= 0L) {
                medId = ID.getAndIncrement();
            }
            model.setId(medId);
            medicines.add(model);
            LOG.info("Medicine id [" + medId + "] was added to repo.");
            return medId;
        }
    }

    @Override
    public void update(Medicine model) {
        Optional<Medicine> optional = getOptionalFromId(model.getId());
        if (optional.isPresent()) {
            remove(optional.get());
            medicines.add(model);
            LOG.info("Medicine id [" + model.getId() + "] was updated.");
        } else {
            throw new IllegalStateException("Medicine id [" + model.getId() + "] was error for update.");
        }
    }

    @Override
    public void remove(Medicine model) {
        Optional<Medicine> optional = getOptionalFromId(model.getId());
        if (optional.isPresent()) {
            optional.ifPresent(medicine -> {
                medicines.remove(medicine);
                LOG.info("Medicine id [" + model.getId() + "] was removed from repo.");
            });
        } else {
            throw new IllegalStateException("Medicine id [" + model.getId() + "] was error for remove.");
        }
    }

    @Override
    public List<Medicine> getAll() {
        return new ArrayList<>(medicines);
    }

    @Override
    public Medicine read(long id) {
        Optional<Medicine> optional = getOptionalFromId(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("Incorrect id [" + id +"] can't read a medicine");
        }
    }

    @Override
    public List<Medicine> find(FindSpecification<Medicine> spec) {
        ArrayList<Medicine> list = new ArrayList<>();
        for (Medicine medicine : medicines) {
            if (spec.match(medicine)) {
                list.add(medicine);
                LOG.info("Spec match medicine id [" + medicine.getId() + "]");
            }
        }
        return list;
    }

    private Optional<Medicine> getOptionalFromId(long id) {
        return medicines.stream()
                .filter(medicine -> medicine.getId() == id)
                .findAny();
    }

    private boolean idIsPresent(long id) {
        return getOptionalFromId(id).isPresent();
    }
}
