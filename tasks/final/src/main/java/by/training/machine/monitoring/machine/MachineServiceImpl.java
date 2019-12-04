package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.TransactionSupport;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Bean
@Log4j
@AllArgsConstructor
@TransactionSupport
public class MachineServiceImpl implements MachineService {

    @Override
    public boolean saveMachine(MachineDto machineDto) {
        return false;
    }
}
