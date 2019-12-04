package by.training.machine.monitoring.model;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {
    private ModelDao modelDao;
    private ManufactureService manufactureService;

    @Override
    public boolean saveModel(ModelDto modelDto) {
        try {
            return modelDao.save(modelDto) > 0;
        } catch (DaoException e) {
            log.error("Failed to save model", e);
            return false;
        }
    }

    @Override
    public ModelDto getModel(Long userId) throws DaoException {
        return modelDao.getById(userId);
    }

    @Override
    @Transactional
    public boolean assignModelManufacture(Long userId, String nameUser) {
        if (manufactureService.getManufacture(userId) != null) {
            return true;
        }
        else {
            ManufactureDto manufactureDto = ManufactureDto.builder()
                    .name(nameUser)
                    .userId(userId)
                    .build();
            return manufactureService.saveManufacture(manufactureDto);
        }
    }

    @Override
    public ManufactureDto getManufactureByUserId(Long userId) {
        return manufactureService.getManufacture(userId);
    }


}
