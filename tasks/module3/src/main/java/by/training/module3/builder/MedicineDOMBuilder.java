package by.training.module3.builder;

import by.training.module3.entity.*;
import by.training.module3.entity.Package;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static by.training.module3.builder.MedicineEnum.*;

public class MedicineDOMBuilder extends Builder {
    private static final Logger LOG = LogManager.getLogger();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private Medicine buildMedicine(Element medicineElement) throws BuilderException {
        LOG.info("Build medicine component.");
        Medicine medicine = new Medicine();
        String strID = medicineElement.getAttribute(MED_ID.getValue());
        try {
            long ID = Long.parseLong(strID);
            LOG.info("Set id [" + ID + "].");
            medicine.setId(ID);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String name = medicineElement.getAttribute(MED_NAME.getValue());
        checkEmpty(name, MED_NAME.getValue());
        LOG.info("Set name [" + name + "]");
        medicine.setName(name);

        String type = getElementTextContent(medicineElement, MED_TYPE.getValue());
        MedicineType medType = MedicineType.getByString(type);
        checkNull(medType, MED_TYPE.getValue());
        LOG.info("Set type [" + medType.toString() + "].");
        medicine.setType(medType);

        String version = getElementTextContent(medicineElement, MED_VERSION.getValue());
        MedicineVersion medVersion = MedicineVersion.getByString(version);
        checkNull(medVersion, MED_VERSION.getValue());
        LOG.info("Set version [" + medVersion.toString() + "].");
        medicine.setVersion(medVersion);

        NodeList analogsList = medicineElement.getElementsByTagName(ANALOGS.getValue());
        Element analogsElement = (Element) analogsList.item(0);
        if (analogsElement != null) {
            List<String> analogs = buildAnalog(analogsElement);
            LOG.info("Add analogs [" + analogs + "] to medicine [" + name + "].");
            medicine.setAnalogs(analogs);
        } else {
            LOG.info("Medicine [" + name + "] don't contain analogs.");
        }

        NodeList pharmsList = medicineElement.getElementsByTagName(PHARM.getValue());
        for (int i = 0; i < pharmsList.getLength(); i++) {
            Element pharmElement = (Element) pharmsList.item(i);
            checkNull(pharmElement, PHARM.getValue());
            Pharm pharm = buildPharm(pharmElement);
            LOG.info("Add pharm [" + pharm + "]");
            medicine.addPharm(pharm);
        }
        return medicine;
    }

    private List<String> buildAnalog(Element analogsElement) {
        List<String> analogs = new ArrayList<>();
        NodeList analogList = analogsElement.getElementsByTagName(ANALOG.getValue());
        for (int i = 0; i < analogList.getLength(); i++) {
            Element analogElement = (Element) analogList.item(i);
            if (analogElement != null) {
                String analog = analogElement.getTextContent();
                analogs.add(analog);
            }
        }
        return analogs;
    }

    private Pharm buildPharm(Element pharmElement) throws BuilderException {
        LOG.info("Build pharm component.");
        Pharm pharm = new Pharm();
        String strID = pharmElement.getAttribute(PHARM_ID.getValue());
        try {
            long ID = Long.parseLong(strID);
            LOG.info("Set id [" + ID + "].");
            pharm.setId(ID);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String name = pharmElement.getAttribute(PHARM_NAME.getValue());
        checkEmpty(name, PHARM_NAME.getValue());
        LOG.info("Set name [" + name + "].");
        pharm.setName(name);

        NodeList certificateList = pharmElement.getElementsByTagName(CERTIFICATE.getValue());
        Element certificateElement = (Element) certificateList.item(0);
        checkNull(certificateElement, CERTIFICATE.getValue());
        Certificate certificate = buildCertificate(certificateElement);
        LOG.info("Set certificate [" + certificate + "]");
        pharm.setCertificate(certificate);

        NodeList dosageList = pharmElement.getElementsByTagName(DOSAGE.getValue());
        Element dosageElement = (Element) dosageList.item(0);
        checkNull(dosageElement, DOSAGE.getValue());
        Dosage dosage = buildDosage(dosageElement);
        LOG.info("Set dosage [" + dosage + "]");
        pharm.setDosage(dosage);

        NodeList packageList = pharmElement.getElementsByTagName(PACKAGE.getValue());
        Element packageElement = (Element) packageList.item(0);
        checkNull(packageElement, PACKAGE.getValue());
        Package aPackage = buildPackage(packageElement);
        LOG.info("Set package [" + aPackage + "].");
        pharm.setaPackage(aPackage);

        return pharm;
    }

    private Package buildPackage(Element packageElement) throws BuilderException {
        LOG.info("Build Package component.");
        Package aPackage = new Package();
        String strType = getElementTextContent(packageElement, PACKAGE_TYPE.getValue());
        PackageType type = PackageType.getByString(strType);
        checkNull(type, PACKAGE_TYPE.getValue());
        LOG.info("Set package type [" + type.toString() + "].");
        aPackage.setType(type);

        String strCount = getElementTextContent(packageElement, COUNT.getValue());
        try {
            int count = Integer.parseInt(strCount);
            LOG.info("Set count [" + count + "].");
            aPackage.setCount(count);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String strValue = getElementTextContent(packageElement, VALUE.getValue());
        try {
            double value = Double.parseDouble(strValue);
            LOG.info("Set value [" + value + "].");
            aPackage.setValue(value);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        return aPackage;
    }

    private Dosage buildDosage(Element dosageElement) throws BuilderException {
        LOG.info("Build Dosage component.");
        Dosage dosage = new Dosage();
        String strDosa = getElementTextContent(dosageElement, DOSA.getValue());
        try {
            double dosa = Double.parseDouble(strDosa);
            LOG.info("Set dosa [" + dosa + "].");
            dosage.setDosage(dosa);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        String strFreq = getElementTextContent(dosageElement, FREQUENCY.getValue());
        try {
            int freq = Integer.parseInt(strFreq);
            LOG.info("Set freq [" + freq + "].");
            dosage.setFrequency(freq);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        return dosage;
    }

    private Certificate buildCertificate(Element certificateElement) throws BuilderException {
        LOG.info("Build Certificate component.");
        Certificate certificate = new Certificate();
        String strNumber = getElementTextContent(certificateElement, NUMBER.getValue());
        try {
            long number = Long.parseLong(strNumber);
            LOG.info("Set number [" + number + "].");
            certificate.setNumber(number);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String regOrganization = getElementTextContent(certificateElement, REGISTR_ORGANIZATION.getValue());
        LOG.info("Set registr organization [" + regOrganization + "].");
        certificate.setRegistrOrganization(regOrganization);

        String strIssueDate = getElementTextContent(certificateElement, ISSUE_DATE.getValue());
        String strShelfDate = getElementTextContent(certificateElement, SHELF_DATE.getValue());
        try {
            Date issueDate = dateFormat.parse(strIssueDate);
            Date shelfDate = dateFormat.parse(strShelfDate);
            LOG.info("Set issueDate [" + issueDate + "].");
            LOG.info("Set shelfDate [" + shelfDate + "].");
            certificate.setIssueDate(issueDate);
            certificate.setShelfDate(shelfDate);
        } catch (ParseException e) {
            throw new BuilderException(e);
        }
        return certificate;
    }

    private String getElementTextContent(Element element, String elementName) throws BuilderException {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        checkNull(node, elementName);
        String text = node.getTextContent();
        checkNull(text, elementName);
        checkEmpty(text, elementName);
        return text;
    }

    private void checkEmpty(String s, String e) throws BuilderException {
        if (s.isEmpty()) {
            throw new BuilderException(new IllegalArgumentException(e));
        }
    }

    private void checkNull(Object s, String e) throws BuilderException {
        if (s == null) {
            throw new BuilderException(new NullPointerException(e + " is null."));
        }
    }

    @Override
    public void buildListMedicines(String fileNme) throws BuilderException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new BuilderException(e);
        }
        Document doc = null;
        try {
            doc = docBuilder.parse(fileNme);
        } catch (SAXException | IOException e) {
            throw new BuilderException(e);
        }
        Element root = doc.getDocumentElement();
        NodeList medicineList = root.getElementsByTagName(MEDICINE.getValue());

        for (int i = 0; i < medicineList.getLength(); i++) {
            Element medicineElement = (Element) medicineList.item(i);
            checkNull(medicineElement, MEDICINE.getValue());
            Medicine medicine = buildMedicine(medicineElement);
            medicines.add(medicine);
        }
    }
}
