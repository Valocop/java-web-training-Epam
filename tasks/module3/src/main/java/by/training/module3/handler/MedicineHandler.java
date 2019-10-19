package by.training.module3.handler;

import by.training.module3.entity.*;
import by.training.module3.entity.Package;
import by.training.module3.command.MedicineEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicineHandler extends Handler<Medicine> {
    private static final Logger LOG = LogManager.getLogger();

    private MedicineEnum medicineEnum;
    private Medicine medicine;
    private Pharm pharm;
    private Certificate certificate;
    private Package aPackage;
    private Dosage dosage;
    private List<Medicine> medicines = new ArrayList<>();

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        medicineEnum = MedicineEnum.getByString(localName);
        LOG.info("Start of element + [" + localName + "].");
        if (medicineEnum == null) {
            throw new SAXException("MedicineEnum is null. Localname [" + localName + "]");
        }
        switch (medicineEnum) {
            case MEDICINE:
                createMedicine(attributes);
                break;
            case PHARM:
                createPharm(attributes);
                break;
            case CERTIFICATE:
                certificate = new Certificate();
                break;
            case DOSAGE:
                dosage = new Dosage();
                break;
            case PACKAGE:
                aPackage = new Package();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        LOG.info("End of element + [" + localName + "].");
        medicineEnum = MedicineEnum.getByString(localName);
        switch (medicineEnum) {
            case MEDICINE:
                medicines.add(medicine);
                break;
            case PHARM:
                medicine.addPharm(pharm);
                break;
            case CERTIFICATE:
                pharm.setCertificate(certificate);
                break;
            case DOSAGE:
                pharm.setDosage(dosage);
                break;
            case PACKAGE:
                pharm.setaPackage(aPackage);
                break;
        }
        medicineEnum = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length).trim();
        if (medicineEnum != null) {
            switch (medicineEnum) {
                case MED_TYPE:
                    addMedType(s);
                    break;
                case MED_VERSION:
                    addMedVersion(s);
                    break;
                case ANALOG:
                    medicine.addAnalog(s);
                    break;
                case NUMBER:
                    addNumber(s);
                    break;
                case ISSUE_DATE:
                    addIssueDate(s);
                    break;
                case SHELF_DATE:
                    addShelfDate(s);
                    break;
                case REGISTR_ORGANIZATION:
                    certificate.setRegistrOrganization(s);
                    break;
                case PACKAGE_TYPE:
                    addPackageType(s);
                    break;
                case COUNT:
                    addCount(s);
                    break;
                case VALUE:
                    addValue(s);
                    break;
                case DOSA:
                    addDosa(s);
                    break;
                case FREQUENCY:
                    addFrequency(s);
                    break;
            }
        }
    }

    private void addFrequency(String s) throws SAXException {
        int freq = 0;
        try {
            freq = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new SAXException(MedicineEnum.FREQUENCY + " is not int. Parameter is [" + s + "].", e);
        }
        LOG.info(MedicineEnum.FREQUENCY + " was add with value " + freq);
        dosage.setFrequency(freq);
    }

    private void addDosa(String s) throws SAXException {
        double dosa = 0;
        try {
            dosa = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new SAXException(MedicineEnum.DOSA + " is not double. Parameter is [" + s + "].", e);
        }
        LOG.info(MedicineEnum.DOSA + " was add with value " + dosa);
        dosage.setDosage(dosa);
    }

    private void addValue(String s) throws SAXException {
        double value = 0;
        try {
            value = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new SAXException(MedicineEnum.VALUE + " is not double. Parameter is [" + s + "].", e);
        }
        LOG.info(MedicineEnum.VALUE + " was add with value " + value);
        aPackage.setValue(value);
    }

    private void addCount(String s) throws SAXException {
        int count = 0;
        try {
            count = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new SAXException(MedicineEnum.COUNT + " is not int. Parameter is [" + s + "].", e);
        }
        LOG.info(MedicineEnum.COUNT + " was add with value " + count);
        aPackage.setCount(count);
    }

    private void addPackageType(String s) throws SAXException {
        PackageType packageType = PackageType.getByString(s);
        if (packageType == null) {
            throw new SAXException(MedicineEnum.PACKAGE_TYPE + " is null. Parameter is [" + s + "].");
        }
        LOG.info(MedicineEnum.PACKAGE_TYPE + " was add with value " + packageType);
        aPackage.setType(packageType);
    }

    private void addShelfDate(String s) throws SAXException {
        DateFormat shelfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date shelfDate = null;
        try {
            shelfDate = shelfDateFormat.parse(s);
        } catch (ParseException e) {
            throw new SAXException(MedicineEnum.SHELF_DATE + " is not date. Parameter is [" + s + "].", e);
        }
        LOG.info(MedicineEnum.SHELF_DATE + " was add with value " + shelfDate);
        certificate.setShelfDate(shelfDate);
    }

    private void addIssueDate(String s) throws SAXException {
        DateFormat issueDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date issueDate = null;
        try {
            issueDate = issueDateFormat.parse(s);
        } catch (ParseException e) {
            throw new SAXException(MedicineEnum.ISSUE_DATE + " is not date. Parameter is [" + s + "].", e);
        }
        LOG.info(MedicineEnum.ISSUE_DATE + " was add with value " + issueDate);
        certificate.setIssueDate(issueDate);
    }

    private void addNumber(String s) throws SAXException {
        long number = 0;
        try {
            number = Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new SAXException(MedicineEnum.NUMBER + " is not long. Parameter is [" + s + "].", e);
        }
        LOG.info(MedicineEnum.NUMBER + " was add with value " + number);
        certificate.setNumber(number);
    }

    private void addMedVersion(String s) throws SAXException {
        MedicineVersion version = MedicineVersion.getByString(s);
        if (version == null) {
            throw new SAXException(MedicineEnum.MED_VERSION + " is null. Parameter is [" + s + "].");
        }
        LOG.info(MedicineEnum.MED_VERSION + " was add with value " + version);
        medicine.setVersion(version);
    }

    private void addMedType(String s) throws SAXException {
        MedicineType type = MedicineType.getByString(s);
        if (type == null) {
            throw new SAXException(MedicineEnum.MED_TYPE + " is null. Parameter is [" + s + "].");
        }
        LOG.info(MedicineEnum.MED_TYPE + " was add with value " + type);
        medicine.setType(type);
    }

    @Override
    public List<Medicine> getEntities() {
        return new ArrayList<>(medicines);
    }

    private void createMedicine(Attributes attributes) throws SAXException {
        medicine = new Medicine();
        String idMed = attributes.getValue(0);
        try {
            long id = Long.parseLong(idMed);
            medicine.setId(id);
            LOG.info("Medicine set ID " + id);
        } catch (NumberFormatException e) {
            throw new SAXException(e);
        }
        String name = attributes.getValue(1);
        LOG.info("Medicine set name " + name);
        medicine.setName(name);
    }

    private void createPharm(Attributes attributes) throws SAXException {
        pharm = new Pharm();
        String idPharm = attributes.getValue(0);
        try {
            long id = Long.parseLong(idPharm);
            pharm.setId(id);
            LOG.info("Pharm set ID " + idPharm);
        } catch (NumberFormatException e) {
            throw new SAXException(e);
        }
        String name = attributes.getValue(1);
        LOG.info("Pharm set name " + name);
        pharm.setName(name);
    }
}
