package by.training.machine.monitoring.characteristic;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.model.ModelService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class CharacteristicServiceImpl implements CharacteristicService {
    private CharacteristicDao characteristicDao;
    private ModelService modelService;

    @Override
    @Transactional
    public boolean saveCharacteristic(CharacteristicDto characteristicDto) {
        try {
            return characteristicDao.save(characteristicDto) > 0;
        } catch (DaoException e) {
            log.error("Failed to save characteristic");
            return false;
        }
    }

    @Override
    public boolean assignManufactureCharacteristic(UserEntity userEntity) {
        return modelService.assignModelManufacture(userEntity.getId(), userEntity.getName());
    }

    @Override
    public CharacteristicDto getCharacteristic(Long id) throws DaoException {
        return characteristicDao.getById(id);
    }

    @Override
    public List<CharacteristicDto> getCharacteristicByManufacture(Long manufactureId) {
        try {
            return characteristicDao.getByManufactureId(manufactureId);
        } catch (DaoException e) {
            return new ArrayList<>();
        }
    }
}
