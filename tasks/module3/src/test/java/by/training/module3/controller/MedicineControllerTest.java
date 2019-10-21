package by.training.module3.controller;

import by.training.module3.builder.*;
import by.training.module3.command.*;
import by.training.module3.entity.Medicine;
import by.training.module3.entity.MedicineType;
import by.training.module3.entity.MedicineVersion;
import by.training.module3.repo.MedicineRepository;
import by.training.module3.repo.Repository;
import by.training.module3.service.MedicineService;
import by.training.module3.service.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class MedicineControllerTest {
    private Repository<Medicine> medicineRepo;
    private Service<Medicine> medicineService;
    private MedicineController medicineController;
    private CommandProvider<Medicine> commandProvider;

    @Before
    public void init() {
        medicineRepo = new MedicineRepository();
        medicineService = new MedicineService(medicineRepo);
        medicineController = new MedicineController(medicineService);
        commandProvider = new CommandProviderImpl();
        Builder DOMBuilder = MedicineBuilderFactory.getBuilder(ParserType.DOM);
        Command<Medicine> DOMParseCommand = new DOMMedicineParseCommand(DOMBuilder);
        Builder SAXBuilder = MedicineBuilderFactory.getBuilder(ParserType.SAX);
        Command<Medicine> SAXParseCommand = new SAXMedicineParseCommand(SAXBuilder);
        commandProvider.addCommand(CommandType.DOM_PARSE_COMMAND, DOMParseCommand);
        commandProvider.addCommand(CommandType.SAX_PARSE_COMMAND, SAXParseCommand);
    }

    @Test
    public void shouldValidateXMLByXSD() {
        String XMLPath = Paths.get("src", "test", "resources", "medicineValid.xml").toString();
        String XSDPath = Paths.get("src", "test", "resources", "medicine.xsd").toString();
        medicineController.execute(commandProvider, CommandType.SAX_PARSE_COMMAND, XMLPath, XSDPath);
        Assert.assertFalse(medicineService.getAll().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExpectIllegalArgumentExceptionValidateXMLByXSD() {
        String XMLPath = Paths.get("src", "test", "resources", "medicineNotValid.xml").toString();
        String XSDPath = Paths.get("src", "test", "resources", "medicine.xsd").toString();
        medicineController.execute(commandProvider, CommandType.SAX_PARSE_COMMAND, XMLPath, XSDPath);
        Assert.fail();
    }

    @Test
    public void shouldParseXMLBySAX() {
        String XMLPath = Paths.get("src", "test", "resources", "medicineValid.xml").toString();
        String XSDPath = Paths.get("src", "test", "resources", "medicine.xsd").toString();
        medicineController.execute(commandProvider, CommandType.SAX_PARSE_COMMAND, XMLPath, XSDPath);
        List<Medicine> expectMedicines = medicineService.getAll();
        Medicine expectAnalgin = expectMedicines.get(0);
        Assert.assertEquals(expectAnalgin.getId(), 12321342);
        Assert.assertEquals(expectAnalgin.getType(), MedicineType.PAIN_MEDICATION);
        Assert.assertEquals(expectAnalgin.getVersion(), MedicineVersion.PILL);
        Assert.assertEquals(expectAnalgin.getAnalogs(), Arrays.asList("Andifen", "Baralgetas"));
        Assert.assertEquals(expectAnalgin.getPharms().size(), 2);
        Assert.assertEquals(expectMedicines.size(), 5);
    }

    @Test
    public void shouldParseXMLByDOM() {
        String XMLPath = Paths.get("src", "test", "resources", "medicineValid.xml").toString();
        String XSDPath = Paths.get("src", "test", "resources", "medicine.xsd").toString();
        medicineController.execute(commandProvider, CommandType.DOM_PARSE_COMMAND, XMLPath, XSDPath);
        List<Medicine> expectMedicines = medicineService.getAll();
        Medicine expectAnalgin = expectMedicines.get(0);
        Assert.assertEquals(expectAnalgin.getId(), 12321342);
        Assert.assertEquals(expectAnalgin.getType(), MedicineType.PAIN_MEDICATION);
        Assert.assertEquals(expectAnalgin.getVersion(), MedicineVersion.PILL);
        Assert.assertEquals(expectAnalgin.getAnalogs(), Arrays.asList("Andifen", "Baralgetas"));
        Assert.assertEquals(expectAnalgin.getPharms().size(), 2);
        Assert.assertEquals(expectMedicines.size(), 5);
    }
}
