package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.characteristic.CharacteristicDao;
import by.training.machine.monitoring.characteristic.CharacteristicDto;
import by.training.machine.monitoring.dao.*;
import by.training.machine.monitoring.manufacture.ManufactureDao;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.model.ModelDao;
import by.training.machine.monitoring.model.ModelDto;
import by.training.machine.monitoring.role.RoleService;
import by.training.machine.monitoring.user.UserDao;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j
@RunWith(JUnit4.class)
public class MachineServiceTest {
    private UserDao mockUserDao;
    private ModelDao mockModelDao;
    private RoleService mockRoleService;
    private MachineDao mockMachineDao;
    private CharacteristicDao mockCharacteristicDao;
    private MachineLogDao mockMachineLogDao;
    private MachineErrorDao mockMachineErrorDao;
    private ManufactureDao mockManufactureDao;
    private MachineService machineService;
    private ManufactureDto manufactureDtoTest = new ManufactureDto(1L, "Manufacturer", 1L);
    private MachineDto machineDtoTest =new MachineDto(1L, "Qwe123", 1L, 1L, 1L);
    private ModelDto modelDtoTest = new ModelDto(1L, "TestModel", new Date(), "Description", null, 1L);
    private CharacteristicDto mockCharacteristicDto = new CharacteristicDto(1l, 1000D, "1", "electric", "10", "electric", 1L);

    @BeforeClass
    public static void contextInit() {
        try {
            ApplicationContext.initialize();
        } catch (Exception e) {
            log.warn("Context was initialized", e);
        }
    }

    @Before
    public void init() throws SQLException, DaoException {
        mockUserDao = mockUserDao();
        mockModelDao = mockModelDao();
        mockCharacteristicDao = mockCharacteristicDao();
        mockRoleService = mockRoleService();
        mockMachineDao = mockMachineDao();
        mockMachineLogDao = mockMachineLogDao();
        mockMachineErrorDao = mockMachineErrorDao();
        mockManufactureDao = mockManufactureDao();
        machineService = new MachineServiceImpl(mockUserDao, mockModelDao, mockCharacteristicDao, mockMachineDao, mockMachineLogDao, mockMachineErrorDao, mockManufactureDao);
    }

    @Test
    public void shouldSaveMachine() {
        boolean isSaved = machineService.saveMachine(machineDtoTest);
        Assert.assertTrue(isSaved);
    }

    @Test
    public void shouldDeleteMachine() throws DaoException {
        boolean isDeleted = machineService.delMachineByManufacture(1L, 1L);
        Mockito.verify(mockMachineDao, Mockito.times(1)).delAssignUserMachine(Mockito.anyLong());
        Mockito.verify(mockMachineLogDao, Mockito.times(1)).delByMachineId(Mockito.anyLong());
        Mockito.verify(mockMachineErrorDao, Mockito.times(1)).delByMachineId(Mockito.anyLong());
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void shouldGetMachineInfo() throws DaoException {
        Mockito.when(mockMachineDao.getByManufacture(Mockito.anyLong())).thenReturn(new ArrayList<MachineDto>(){{add(machineDtoTest);}});
        Mockito.when(mockManufactureDao.getById(Mockito.anyLong())).thenReturn(manufactureDtoTest);
        Mockito.when(mockModelDao.getById(Mockito.anyLong())).thenReturn(modelDtoTest);
        Mockito.when(mockCharacteristicDao.getById(Mockito.anyLong())).thenReturn(mockCharacteristicDto);
        Mockito.when(mockMachineLogDao.getMachineLogByMachineId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        Mockito.when(mockMachineErrorDao.getErrorsByMachineId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        Mockito.when(mockUserDao.getUsersByMachineId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        List<MachineInfoDto> machineInfo = machineService.getMachineInfo(machineDtoTest.getManufactureId());
        Assert.assertNotNull(machineInfo);
    }

    private ManufactureDao mockManufactureDao() {
        ManufactureDao mockManufactureDao = Mockito.mock(ManufactureDao.class);
        return mockManufactureDao;
    }

    private MachineErrorDao mockMachineErrorDao() {
        MachineErrorDao mockMachineErrorDao = Mockito.mock(MachineErrorDao.class);
        return mockMachineErrorDao;
    }

    private MachineLogDao mockMachineLogDao() {
        MachineLogDao mockMachineLogDao = Mockito.mock(MachineLogDao.class);
        return mockMachineLogDao;
    }

    private MachineDao mockMachineDao() throws DaoException {
        MachineDao mockMachineDao = Mockito.mock(MachineDao.class);
        Mockito.when(mockMachineDao.save(machineDtoTest)).thenReturn(machineDtoTest.getId());
        Mockito.when(mockMachineDao.delByManufactureAndMachine(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
        return mockMachineDao;
    }

    private CharacteristicDao mockCharacteristicDao() throws DaoException {
        CharacteristicDao mockCharacteristicDao = Mockito.mock(CharacteristicDao.class);
        Mockito.when(mockCharacteristicDao.getById(machineDtoTest.getCharacteristicId())).thenReturn(mockCharacteristicDto);
        return mockCharacteristicDao;
    }

    private UserDao mockUserDao() throws DaoException {
        UserDao mockUserDao = Mockito.mock(UserDao.class);
        return mockUserDao;
    }

    private RoleService mockRoleService() {
        RoleService mockRoleService = Mockito.mock(RoleService.class);
        return mockRoleService;
    }

    private ModelDao mockModelDao() throws DaoException {
        ModelDao mockModelDao = Mockito.mock(ModelDao.class);
        Mockito.when(mockModelDao.getById(machineDtoTest.getModelId())).thenReturn(modelDtoTest);
        return mockModelDao;
    }
}
