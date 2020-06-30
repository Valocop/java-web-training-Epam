package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.characteristic.CharacteristicDao;
import by.training.machine.monitoring.characteristic.CharacteristicDto;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import by.training.machine.monitoring.manufacture.ManufactureDao;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.model.ModelDao;
import by.training.machine.monitoring.model.ModelDto;
import by.training.machine.monitoring.user.UserDao;
import by.training.machine.monitoring.user.UserDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class MachineServiceImpl implements MachineService {
    private UserDao userDao;
    private ModelDao modelDao;
    private CharacteristicDao characteristicDao;
    private MachineDao machineDao;
    private MachineLogDao machineLogDao;
    private MachineErrorDao machineErrorDao;
    private ManufactureDao manufactureDao;

    @Override
    @Transactional
    public boolean saveMachine(MachineDto machineDto) {
        try {
            ModelDto model = modelDao.getById(machineDto.getModelId());
            CharacteristicDto characteristic = characteristicDao.getById(machineDto.getCharacteristicId());
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
                    ManufactureDto manufacture = manufactureDao.getById(machineDto.getManufactureId());
                    ModelDto model = modelDao.getById(machineDto.getModelId());
                    CharacteristicDto characteristic = characteristicDao.getById(machineDto.getCharacteristicId());
                    List<MachineLogDto> machineLogsList = machineLogDao.getMachineLogByMachineId(machineDto.getId());
                    List<MachineErrorDto> machineErrors = machineErrorDao.getErrorsByMachineId(machineDto.getId());
                    List<UserDto> userList = userDao.getUsersByMachineId(machineDto.getId());
                    if (model != null && characteristic != null) {
                        machineInfoList.add(MachineInfoDto.builder()
                                .manufactureDto(manufacture)
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

    @Override
    public Optional<MachineInfoDto> getMachineInfoByMachineId(Long machineId) {
        try {
            MachineDto machineDto = machineDao.getById(machineId);
            if (machineDto != null) {
                ManufactureDto manufacture = manufactureDao.getById(machineDto.getManufactureId());
                ModelDto model = modelDao.getById(machineDto.getModelId());
                CharacteristicDto characteristic = characteristicDao.getById(machineDto.getCharacteristicId());
                List<MachineLogDto> machineLogsList = machineLogDao.getMachineLogByMachineId(machineDto.getId());
                List<MachineErrorDto> machineErrors = machineErrorDao.getErrorsByMachineId(machineDto.getId());
                return Optional.of(MachineInfoDto.builder()
                        .manufactureDto(manufacture)
                        .machineDto(machineDto)
                        .modelDto(model)
                        .characteristicDto(characteristic)
                        .machineLogsList(machineLogsList)
                        .machineErrorsList(machineErrors)
                        .build());
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            log.error("Failed to get machine info by machine id", e);
            return Optional.empty();
        }
    }
}
