package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.dao.*;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Log4j
@RunWith(JUnit4.class)
public class MachineDaoTest {
    //language=H2
    private static final String CREATE_SCHEMA_MACHINE = "create schema machine_monitoring_schema";
    //language=H2
    private static final String DROP_SCHEMA_MACHINE = "drop schema machine_monitoring_schema";
    //language=H2
    private static final String CREATE_TABLE = "create table machine_monitoring_schema.machine" +
            "(id integer auto_increment primary key, " +
            "uniq_code varchar(100) not null," +
            "model_id integer not null," +
            "characteristic_id integer not null," +
            "manufacture_id integer not null)";
    //language=H2
    private static final String DROP_TABLE = "drop table machine_monitoring_schema.machine";

    @BeforeClass
    public static void contextInit() {
        try {
            ApplicationContext.initialize();
        } catch (Exception e) {
            log.warn("Context was initialized", e);
        }
    }

    @Before
    public void init() throws DaoException, SQLException {
        executeSql(CREATE_SCHEMA_MACHINE);
        executeSql(CREATE_TABLE);
    }

    @Test
    public void shouldSaveAndDeleteMachine() throws DaoException {
        MachineDao machineDao = ApplicationContext.getInstance().getBean(MachineDao.class);
        MachineDto machineDto = MachineDto.builder()
                .uniqNumber("Qwerty123")
                .characteristicId(1L)
                .modelId(1L)
                .manufactureId(1L)
                .build();
        Long save = machineDao.save(machineDto);
        Assert.assertNotNull(save);
        machineDto.setId(save);
        boolean isDeleted = machineDao.delete(machineDto);
        Assert.assertTrue(isDeleted);
        List<MachineDto> all = machineDao.findAll();
        Assert.assertEquals(0, all.size());
    }

    @Test
    public void shouldSaveUpdateDeleteMachine() throws DaoException {
        MachineDao machineDao = ApplicationContext.getInstance().getBean(MachineDao.class);
        MachineDto machineDto = MachineDto.builder()
                .uniqNumber("Qwerty123")
                .characteristicId(1L)
                .modelId(1L)
                .manufactureId(1L)
                .build();
        Long save = machineDao.save(machineDto);
        Assert.assertNotNull(save);
        machineDto.setId(save);
        machineDto.setUniqNumber("Qwe");
        boolean isUpdated = machineDao.update(machineDto);
        Assert.assertTrue(isUpdated);
        MachineDto machineDtoById = machineDao.getById(machineDto.getId());
        Assert.assertNotNull(machineDtoById);
        Assert.assertEquals(machineDto.getUniqNumber(), machineDtoById.getUniqNumber());
    }

    @After
    public void destroy() throws SQLException, DaoException {
        executeSql(DROP_TABLE);
        executeSql(DROP_SCHEMA_MACHINE);
    }

    private void executeSql(String sql) throws DaoException, SQLException {
        ConnectionManager connectionManager = ApplicationContext.getInstance().getBean(ConnectionManager.class);
        Assert.assertNotNull(connectionManager);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(true);
            ps.executeUpdate();
        }
    }
}
