package by.training.machine.monitoring.manufacture;

import by.training.machine.monitoring.characteristic.CharacteristicDao;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.machine.MachineInfoDto;
import by.training.machine.monitoring.machine.MachineService;
import by.training.machine.monitoring.model.ModelDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Optional;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class ManufactureServiceImpl implements ManufactureService {
    private ManufactureDao manufactureDao;
    private MachineService machineService;
    private ModelDao modelDao;
    private CharacteristicDao characteristicDao;

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
    public Optional<ManufactureDto> getManufacture(Long Id) {
        try {
            return Optional.ofNullable(manufactureDao.getById(Id));
        } catch (DaoException e) {
            log.error("Fail to get manufacture by id", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ManufactureDto> getManufactureByUserId(Long userId) {
        try {
            return manufactureDao.getByUserId(userId);
        } catch (DaoException e) {
            log.error("Failed to get manufacture by user id");
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean deleteManufactureByUserId(Long userId) {
        try {
            Optional<ManufactureDto> manufactureDtoOptional = manufactureDao.getByUserId(userId);
            if (manufactureDtoOptional.isPresent()) {
                ManufactureDto manufactureDto = manufactureDtoOptional.get();
                List<MachineInfoDto> machineInfo = machineService.getMachineInfo(manufactureDto.getId());
                machineInfo.forEach(machineInfoDto -> machineService
                        .delMachineByManufacture(machineInfoDto.getMachineDto().getId(), manufactureDto.getId()));
                modelDao.deleteModelByManufactureId(manufactureDto.getId());
                characteristicDao.deleteByManufactureId(manufactureDto.getId());
                deleteManufacture(manufactureDto);
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            log.error("Failed to delete manufacture by user id");
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteManufacture(ManufactureDto manufactureDto) {
        try {
            return manufactureDao.delete(manufactureDto);
        } catch (DaoException e) {
            log.error("Failed to delete manufacture", e);
            return false;
        }
    }
}
