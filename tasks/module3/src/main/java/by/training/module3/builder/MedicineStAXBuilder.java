package by.training.module3.builder;

import by.training.module3.entity.*;
import by.training.module3.entity.Package;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static by.training.module3.builder.MedicineEnum.*;

public class MedicineStAXBuilder extends Builder<Medicine> {
    private static final Logger LOG = LogManager.getLogger();
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void buildList(String fileNme) throws BuilderException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        try (FileInputStream inputStream = new FileInputStream(new File(fileNme))) {
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();
                    if (MEDICINE.getValue().equalsIgnoreCase(name)) {
                        Medicine medicine = buildMedicine(reader);
                        medicines.add(medicine);
                    }
                }
            }
        } catch (IOException | XMLStreamException e) {
            throw new BuilderException(e);
        }
    }

    private Medicine buildMedicine(XMLStreamReader reader) throws BuilderException {
        Medicine medicine = new Medicine();
        String medName = buildMedName(reader);
        LOG.info("Set name [" + medName + "].");
        medicine.setName(medName);
        long ID = buildID(reader, MED_ID.getValue());
        LOG.info("Set id [" + ID + "].");
        medicine.setId(ID);
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        switch (MedicineEnum.getByString(name)) {
                            case MED_TYPE:
                                MedicineType medType = buildMedType(reader);
                                LOG.info("Set medType [" + medType + "].");
                                medicine.setType(medType);
                                break;
                            case MED_VERSION:
                                MedicineVersion version = buildMedVersion(reader);
                                LOG.info("Set medVersion [" + version + "].");
                                medicine.setVersion(version);
                                break;
                            case ANALOGS:
                                List<String> analogs = buildAnalogs(reader);
                                LOG.info("Set analogs [" + analogs + "].");
                                medicine.setAnalogs(analogs);
                                break;
                            case PHARMS:
                                List<Pharm> pharms = buildPharms(reader);
                                LOG.info("Set pharms [" + pharms + "].");
                                medicine.setPharms(pharms);
                                break;
                        }
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (MedicineEnum.getByString(name) == MEDICINE) {
                            return medicine;
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        throw new BuilderException(new XMLStreamException("Unknown element in tag Medicine"));
    }

    private List<Pharm> buildPharms(XMLStreamReader reader) throws BuilderException {
        List<Pharm> pharms = new ArrayList<>();
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        MedicineEnum pharmEnum = MedicineEnum.getByString(name);
                        checkNull(pharmEnum, PHARMS.getValue());
                        if (pharmEnum == PHARM) {
                            Pharm pharm = buildPharm(reader);
                            pharms.add(pharm);
                        }
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (MedicineEnum.getByString(name) == PHARMS) {
                            return pharms;
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        throw new BuilderException(new XMLStreamException("Unknown element in tag Pharms"));
    }

    private Pharm buildPharm(XMLStreamReader reader) throws BuilderException {
        Pharm pharm = new Pharm();
        String pharmName = reader.getAttributeValue(reader.getNamespaceURI(), PHARM_NAME.getValue());
        checkNull(pharmName, PHARM_NAME.getValue());
        LOG.info("Set pharmName [" + pharmName + "].");
        pharm.setName(pharmName);
        long ID = buildID(reader, PHARM_ID.getValue());
        LOG.info("Set pharmID [" + ID + "].");
        pharm.setId(ID);
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        MedicineEnum pharmEnum = MedicineEnum.getByString(name);
                        checkNull(pharmEnum, PHARM.getValue());
                        switch (pharmEnum) {
                            case CERTIFICATE:
                                Certificate certificate = buildCertificate(reader);
                                LOG.info("Set certificate [" + certificate + "].");
                                pharm.setCertificate(certificate);
                                break;
                            case PACKAGE:
                                Package aPackage = buildPackage(reader);
                                LOG.info("Set package [" + aPackage + "].");
                                pharm.setaPackage(aPackage);
                                break;
                            case DOSAGE:
                                Dosage dosage = buildDosage(reader);
                                LOG.info("Set dosage [" + dosage + "].");
                                pharm.setDosage(dosage);
                                break;
                        }
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (MedicineEnum.getByString(name) == PHARM) {
                            return pharm;
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        throw new BuilderException(new XMLStreamException("Unknown element in tag Pharm"));
    }

    private Dosage buildDosage(XMLStreamReader reader) throws BuilderException {
        Dosage dosage = new Dosage();
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        MedicineEnum pharmEnum = MedicineEnum.getByString(name);
                        checkNull(pharmEnum, PACKAGE.getValue());
                        switch (pharmEnum) {
                            case DOSA:
                                double dosa = buildDosa(reader);
                                LOG.info("Set dosa [" + dosa + "].");
                                dosage.setDosage(dosa);
                                break;
                            case FREQUENCY:
                                int freq = buildFreq(reader);
                                LOG.info("Set freq [" + freq + "].");
                                dosage.setFrequency(freq);
                                break;
                        }
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (MedicineEnum.getByString(name) == DOSAGE) {
                            return dosage;
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        throw new BuilderException(new XMLStreamException("Unknown element in tag Certificate"));
    }

    private int buildFreq(XMLStreamReader reader) throws BuilderException {
        try {
            String strFreq = reader.getElementText();
            return Integer.parseInt(strFreq);
        } catch (XMLStreamException | NumberFormatException e) {
            throw new BuilderException(e);
        }
    }

    private double buildDosa(XMLStreamReader reader) throws BuilderException {
        try {
            String strDosa = reader.getElementText();
            return Double.parseDouble(strDosa);
        } catch (XMLStreamException | NumberFormatException e) {
            throw new BuilderException(e);
        }
    }

    private Package buildPackage(XMLStreamReader reader) throws BuilderException {
        Package aPackage = new Package();
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        MedicineEnum pharmEnum = MedicineEnum.getByString(name);
                        checkNull(pharmEnum, PACKAGE.getValue());
                        switch (pharmEnum) {
                            case PACKAGE_TYPE:
                                String strType = reader.getElementText();
                                PackageType pacType = PackageType.getByString(strType);
                                checkNull(pacType, PACKAGE_TYPE.getValue());
                                LOG.info("Set packageType [" + pacType + "].");
                                aPackage.setType(pacType);
                                break;
                            case COUNT:
                                int count = buildCount(reader, COUNT.getValue());
                                LOG.info("Set count [" + count + "].");
                                aPackage.setCount(count);
                                break;
                            case VALUE:
                                double value = buildValue(reader, VALUE.getValue());
                                LOG.info("Set value [" + value + "].");
                                aPackage.setValue(value);
                                break;
                        }
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (MedicineEnum.getByString(name) == PACKAGE) {
                            return aPackage;
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        throw new BuilderException(new XMLStreamException("Unknown element in tag Certificate"));
    }

    private double buildValue(XMLStreamReader reader, String value) throws BuilderException {
        try {
            String strValue = reader.getElementText();
            return Double.parseDouble(strValue);
        } catch (XMLStreamException | NumberFormatException e) {
            throw new BuilderException(e);
        }
    }

    private int buildCount(XMLStreamReader reader, String value) throws BuilderException {
        try {
            String strCount = reader.getElementText();
            return Integer.parseInt(strCount);
        } catch (XMLStreamException | NumberFormatException e) {
            throw new BuilderException(e);
        }
    }

    private Certificate buildCertificate(XMLStreamReader reader) throws BuilderException {
        Certificate certificate = new Certificate();
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        MedicineEnum pharmEnum = MedicineEnum.getByString(name);
                        checkNull(pharmEnum, PHARM.getValue());
                        switch (pharmEnum) {
                            case NUMBER:
                                long number = buildNumber(reader);
                                LOG.info("Set Number [" + number + "].");
                                certificate.setNumber(number);
                                break;
                            case ISSUE_DATE:
                                Date issueDate = buildDate(reader, ISSUE_DATE.getValue());
                                LOG.info("Set issueDate [" + issueDate + "].");
                                certificate.setIssueDate(issueDate);
                                break;
                            case SHELF_DATE:
                                Date shelfDate = buildDate(reader, SHELF_DATE.getValue());
                                LOG.info("Set issueDate [" + shelfDate + "].");
                                certificate.setShelfDate(shelfDate);
                                break;
                            case REGISTR_ORGANIZATION:
                                String regOrg = reader.getElementText();
                                checkNull(regOrg, REGISTR_ORGANIZATION.getValue());
                                LOG.info("Set regOrganization [" + regOrg + "].");
                                certificate.setRegistrOrganization(regOrg);
                                break;
                        }
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (MedicineEnum.getByString(name) == CERTIFICATE) {
                            return certificate;
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        throw new BuilderException(new XMLStreamException("Unknown element in tag Certificate"));
    }

    private Date buildDate(XMLStreamReader reader, String value) throws BuilderException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateStr;
        try {
            dateStr = reader.getElementText();
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        checkNull(dateStr, value);
        Date date;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new BuilderException(e);
        }
        return date;
    }

    private long buildNumber(XMLStreamReader reader) throws BuilderException {
        String strNumber = null;
        try {
            strNumber = reader.getElementText();
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        checkNull(strNumber, NUMBER.getValue());
        long number;
        try {
            number = Long.parseLong(strNumber);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        return number;
    }

    private List<String> buildAnalogs(XMLStreamReader reader) throws BuilderException {
        List<String> analogs = new ArrayList<>();
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        MedicineEnum medEnum = MedicineEnum.getByString(name);
                        if (medEnum == ANALOG) {
                            analogs.add(buildAnalog(reader));
                        }
                    case XMLStreamConstants.END_ELEMENT:
                        name = reader.getLocalName();
                        if (MedicineEnum.getByString(name) == ANALOGS) {
                            return analogs;
                        }
                }
            }
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
        return analogs;
    }

    private String buildAnalog(XMLStreamReader reader) throws BuilderException {
        try {
            String analog = reader.getElementText();
            checkNull(analog, ANALOG.getValue());
            return analog;
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
    }

    private MedicineVersion buildMedVersion(XMLStreamReader reader) throws BuilderException {
        try {
            String strVersion = reader.getElementText();
            MedicineVersion version = MedicineVersion.getByString(strVersion);
            checkNull(version, MED_VERSION.getValue());
            return version;
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
    }

    private MedicineType buildMedType(XMLStreamReader reader) throws BuilderException {
        try {
            String strMedType = reader.getElementText();
            MedicineType type = MedicineType.getByString(strMedType);
            checkNull(type, MED_TYPE.getValue());
            return type;
        } catch (XMLStreamException e) {
            throw new BuilderException(e);
        }
    }

    private long buildID(XMLStreamReader reader, String value) throws BuilderException {
        String strID = reader.getAttributeValue(reader.getNamespaceURI(), value);
        checkNull(strID, value);
        long ID;
        try {
            ID = Long.parseLong(strID);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        return ID;
    }

    private String buildMedName(XMLStreamReader reader) throws BuilderException {
        String medName = reader.getAttributeValue(reader.getNamespaceURI(), MED_NAME.getValue());
        checkNull(medName, MED_NAME.getValue());
        return medName;
    }

    private void checkNull(Object s, String e) throws BuilderException {
        if (s == null) {
            throw new BuilderException(new NullPointerException(e + " is null."));
        }
    }
}
