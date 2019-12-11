package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.characteristic.CharacteristicDto;
import by.training.machine.monitoring.characteristic.CharacteristicService;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.model.ModelDto;
import by.training.machine.monitoring.model.ModelService;
import by.training.machine.monitoring.user.UserDto;
import by.training.machine.monitoring.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class MachineServiceImpl implements MachineService {
    private UserService userService;
    private ModelService modelService;
    private CharacteristicService characteristicService;
    private MachineDao machineDao;
    private MachineLogDao machineLogDao;
    private MachineErrorDao machineErrorDao;

    @Override
    @Transactional
    public boolean saveMachine(MachineDto machineDto) {
        try {
            ModelDto model = modelService.getModel(machineDto.getModelId());
            CharacteristicDto characteristic = characteristicService.getCharacteristic(machineDto.getCharacteristicId());
            if (model != null && characteristic != null) {
                return machineDao.save(machineDto) > 0;
            }
        } catch (DaoException e) {
            log.error("Fail to save machine");
            return false;
        }
        return false;
    }

    @Override
    public List<MachineInfoDto> getMachineInfo(Long manufactureId) {
        List<MachineInfoDto> machineInfoList = new ArrayList<>();
        try {
            List<MachineDto> machinesList = machineDao.getByManufacture(manufactureId);
            if (machinesList != null) {
                for (MachineDto machineDto : machinesList) {
                    ModelDto model = modelService.getModel(machineDto.getModelId());
                    CharacteristicDto characteristic = characteristicService.getCharacteristic(machineDto.getCharacteristicId());
                    List<MachineLogDto> machineLogsList = machineLogDao.getMachineLogByMachineId(machineDto.getId());
                    List<MachineErrorDto> machineErrors = machineErrorDao.getErrorsByMachineId(machineDto.getId());
                    List<UserDto> userList = userService.getUsersByMachineId(machineDto.getId());
                    if (model != null && characteristic != null) {
                        machineInfoList.add(MachineInfoDto.builder()
                                .machineDto(machineDto)
                                .modelDto(model)
                                .characteristicDto(characteristic)
                                .machineLogsList(machineLogsList)
                                .machineErrorsList(machineErrors)
                                .usersList(userList)
                                .build());
                    } else {
                        return new ArrayList<>();
                    }
                }
                return machineInfoList;
            }
            return new ArrayList<>();
        } catch (DaoException e) {
            log.error("Failed to get info about machine");
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public boolean delMachineByManufacture(Long machineId, Long manufactureId) {
        try {
            machineDao.delAssignUserMachine(machineId);
            machineLogDao.delByMachineId(machineId);
            machineErrorDao.delByMachineId(machineId);
            return machineDao.delByManufactureAndMachine(manufactureId, machineId);
        } catch (DaoException e) {
            log.error("Failed ti delete machine", e);
            return false;
        }
    }
}
