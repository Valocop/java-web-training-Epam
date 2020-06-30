package by.training.machine.monitoring.manufacture;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DataSource;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RunWith(JUnit4.class)
public class ManufactureDaoTest {
    //language=H2
    private static final String CREATE_SCHEMA_MACHINE = "create schema machine_monitoring_schema";
    //language=H2
    private static final String DROP_SCHEMA_MACHINE = "drop schema machine_monitoring_schema";
    //language=H2
    private static final String CREATE_TABLE = "create table machine_monitoring_schema.manufacture" +
            "(id integer primary key auto_increment not null ," +
            "name varchar(150) not null," +
            "user_id integer not null)";
    //language=H2
    private static final String DROP_TABLE = "drop table machine_monitoring_schema.manufacture";

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
    public void shouldSaveAndDeleteManufacture() throws DaoException {
        ManufactureDao manufactureDao = ApplicationContext.getInstance().getBean(ManufactureDao.class);
        Assert.assertNotNull(manufactureDao);
        ManufactureDto manufactureDto = ManufactureDto.builder()
                .name("Manufacture")
                .userId(1L)
                .build();
        Long save = manufactureDao.save(manufactureDto);
        Assert.assertNotNull(save);
        manufactureDto.setId(save);
        manufactureDao.delete(manufactureDto);
    }

    @Test
    public void shouldSaveUpdateDeleteManufacture() throws DaoException {
        ManufactureDao manufactureDao = ApplicationContext.getInstance().getBean(ManufactureDao.class);
        Assert.assertNotNull(manufactureDao);
        ManufactureDto manufactureDto = ManufactureDto.builder()
                .name("Manufacture")
                .userId(1L)
                .build();
        Long save = manufactureDao.save(manufactureDto);
        Assert.assertNotNull(save);
        manufactureDto.setId(save);
        manufactureDto.setName("NewName");
        boolean isUpdated = manufactureDao.update(manufactureDto);
        Assert.assertTrue(isUpdated);
        ManufactureDto manufactureDaoById = manufactureDao.getById(manufactureDto.getId());
        Assert.assertNotNull(manufactureDaoById);
        boolean isDeleted = manufactureDao.delete(manufactureDto);
        Assert.assertTrue(isDeleted);
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
