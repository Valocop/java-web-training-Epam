package by.training.machine.monitoring.characteristic;

import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.entity.UserEntity;

import java.util.List;

public interface CharacteristicService {
    boolean saveCharacteristic(CharacteristicDto characteristicDto);
    boolean assignManufactureCharacteristic(UserEntity userEntity);
    CharacteristicDto getCharacteristic(Long id) throws DaoException;
    List<CharacteristicDto> getCharacteristicByManufacture(Long manufactureId);
    boolean deleteCharacteristicByManufactureId(Long manufactureId);
}
