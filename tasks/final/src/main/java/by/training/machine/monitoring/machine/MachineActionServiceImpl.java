package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.TransactionSupport;
import by.training.machine.monitoring.dao.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class MachineActionServiceImpl implements MachineActionService {
    private MachineDao machineDao;
    private MachineLogDao machineLogDao;
    private MachineErrorDao machineErrorDao;

    @Override
    @Transactional
    public boolean addRandomData(Long machineId) {
        try {
            MachineDto machineDto = machineDao.getById(machineId);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                addRandomLog(machineDto.getId());
                TimeUnit.MILLISECONDS.sleep(100);
                addRandomError(machineDto.getId());
            } catch (InterruptedException e) {
                log.error("Failed to add log for machine", e);
            }
            return true;
        } catch (DaoException e) {
            log.error("Failed to add random data to log", e);
            return false;
        }
    }

    private boolean addRandomLog(Long machineId) {
        MachineLogDto machineLogDto = MachineLogDto.builder()
                .date(new Date())
                .fuelLevel(randomGenerate(50.0, 100.0))
                .oilPressure(randomGenerate(30.0, 80.0))
                .oilLevel(randomGenerate(70.0, 100.0))
                .coolantTemp(randomGenerate(70.0, 100.0))
                .machineId(machineId)
                .build();
        try {
            machineLogDao.save(machineLogDto);
            return true;
        } catch (DaoException e) {
            log.error("Failed to save log for machine " + machineId, e);
            return false;
        }
    }

    private boolean addRandomError(Long machineId) {
        MachineErrorDto machineErrorDto = MachineErrorDto.builder()
                .date(new Date())
                .errorCode(String.valueOf(((int) randomGenerate(1.0, 6.0))))
                .machineId(machineId)
                .build();
        try {
            machineErrorDao.save(machineErrorDto);
            return true;
        } catch (DaoException e) {
            log.error("Failed to save error for machine " + machineId, e);
            return false;
        }
    }

    private double randomGenerate(double min, double max) {
        return (Math.random() * ((max - min) + 1)) + min;
    }
}
