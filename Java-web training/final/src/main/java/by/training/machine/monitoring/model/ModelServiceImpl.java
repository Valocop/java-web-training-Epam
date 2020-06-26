package by.training.machine.monitoring.model;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {
    private ModelDao modelDao;
    private ManufactureService manufactureService;

    @Override
    @Transactional
    public boolean saveModel(ModelDto modelDto) {
        try {
            return modelDao.save(modelDto) > 0;
        } catch (DaoException e) {
            log.error("Failed to save model", e);
            return false;
        }
    }

    @Override
    public ModelDto getModel(Long modelId) {
        try {
            return modelDao.getById(modelId);
        } catch (DaoException e) {
            log.error("Failed to get model by id", e);
            return null;
        }
    }

    @Override
    @Transactional
    public List<ModelDto> getModelByUserId(Long userId) {
        Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(userId);
        if (manufactureByUserId.isPresent()) {
            Long manufactureId = manufactureByUserId.get().getId();
            try {
                return modelDao.getModelByManufactureId(manufactureId);
            } catch (DaoException e) {
                log.error("Failed to get model by user id");
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public boolean assignModelManufacture(Long userId, String nameUser) {
        if (manufactureService.getManufactureByUserId(userId).isPresent()) {
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
    public List<ModelDto> getModelByManufacture(Long manufactureId) {
        try {
            return modelDao.getModelByManufactureId(manufactureId);
        } catch (DaoException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean deleteModelByManufactureId(Long manufactureId) {
        try {
            return modelDao.deleteModelByManufactureId(manufactureId);
        } catch (DaoException e) {
            log.error("Failed to delete model by manufacture id");
            return false;
        }
    }
}
