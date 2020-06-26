package by.training.machine.monitoring.manufacture;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.characteristic.CharacteristicDao;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.machine.*;
import by.training.machine.monitoring.model.ModelDao;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@Log4j
@RunWith(JUnit4.class)
public class ManufactureServiceTest {
    private ModelDao mockModelDao;
    private CharacteristicDao mockCharacteristicDao;
    private ManufactureDao mockManufactureDao;
    private MachineService mockMachineService;
    private ManufactureService manufactureService;
    private ManufactureDto manufactureDtoTest = new ManufactureDto(1L, "Manufacturer", 1L);


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
        mockModelDao = mockModelDao();
        mockCharacteristicDao = mockCharacteristicDao();
        mockManufactureDao = mockManufactureDao();
        mockMachineService = mockMachineService();
        manufactureService = new ManufactureServiceImpl(mockManufactureDao, mockMachineService ,mockModelDao, mockCharacteristicDao);
    }

    @Test
    public void shouldSaveManufacture() throws DaoException {
        Mockito.when(mockManufactureDao.save(manufactureDtoTest)).thenReturn(1L);
        boolean isSaved = manufactureService.saveManufacture(manufactureDtoTest);
        Assert.assertTrue(isSaved);
    }

    @Test
    public void shouldGetManufacture() throws DaoException {
        Mockito.when(mockManufactureDao.getById(Mockito.anyLong())).thenReturn(manufactureDtoTest);
        Optional<ManufactureDto> manufactureOptional = manufactureService.getManufacture(Mockito.anyLong());
        Assert.assertTrue(manufactureOptional.isPresent());
        Assert.assertEquals(manufactureDtoTest.getName(), manufactureOptional.get().getName());
    }

    @Test
    public void shouldDeleteManufacture() throws DaoException {
        Mockito.when(mockManufactureDao.delete(manufactureDtoTest)).thenReturn(true);
        boolean isDeleted = manufactureService.deleteManufacture(manufactureDtoTest);
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void shouldDeleteManufactureByUserId() throws DaoException {
        Mockito.when(mockManufactureDao.getByUserId(Mockito.anyLong())).thenReturn(Optional.of(manufactureDtoTest));
        Mockito.when(mockMachineService.getMachineInfo(Mockito.anyLong())).thenReturn(new ArrayList<>());
        Mockito.when(mockMachineService.delMachineByManufacture(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
        Mockito.when(mockModelDao.deleteModelByManufactureId(Mockito.anyLong())).thenReturn(true);
        Mockito.when(mockCharacteristicDao.deleteByManufactureId(Mockito.anyLong())).thenReturn(true);
        boolean isDeleted = manufactureService.deleteManufactureByUserId(Mockito.anyLong());
        Assert.assertTrue(isDeleted);
    }

    private MachineService mockMachineService() {
        return Mockito.mock(MachineService.class);
    }

    private ManufactureDao mockManufactureDao() {
        return Mockito.mock(ManufactureDao.class);
    }

    private CharacteristicDao mockCharacteristicDao() throws DaoException {
        return Mockito.mock(CharacteristicDao.class);
    }

    private ModelDao mockModelDao() throws DaoException {
        return Mockito.mock(ModelDao.class);
    }
}
