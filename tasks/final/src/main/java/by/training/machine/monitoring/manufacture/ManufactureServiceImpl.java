package by.training.machine.monitoring.manufacture;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class ManufactureServiceImpl implements ManufactureService {
    private ManufactureDao manufactureDao;

    @Override
    public boolean saveManufacture(ManufactureDto modelDto) {
        try {
            return manufactureDao.save(modelDto) > 0;
        } catch (DaoException e) {
            log.error("Failed to save manufacture", e);
            return false;
        }
    }

    @Override
    public ManufactureDto getManufacture(Long userId) {
        try {
            return manufactureDao.getById(userId);
        } catch (DaoException e) {
            log.error("Fail to get manufacture by user id", e);
            return null;
        }
    }


}
