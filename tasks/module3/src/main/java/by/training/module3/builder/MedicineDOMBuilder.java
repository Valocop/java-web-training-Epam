package by.training.module3.builder;

import by.training.module3.entity.*;
import by.training.module3.entity.Package;
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

import static by.training.module3.parser.MedicineEnum.*;

public class MedicineDOMBuilder implements Builder<Medicine> {
    private List<Medicine> medicines = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Medicine> getEntities() {
        return new ArrayList<>(medicines);
    }

    @Override
    public void buildEntities(String path) throws BuilderException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new BuilderException(e);
        }
        Document doc = null;
        try {
            doc = docBuilder.parse(path);
        } catch (SAXException e) {
            throw new BuilderException(e);
        } catch (IOException e) {
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

    private Medicine buildMedicine(Element medicineElement) throws BuilderException {
        Medicine medicine = new Medicine();
        String strID = medicineElement.getAttribute(MED_ID.getValue());
        try {
            long ID = Long.parseLong(strID);
            medicine.setId(ID);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String name = medicineElement.getAttribute(MED_NAME.getValue());
        checkEmpty(name, MED_NAME.getValue());
        medicine.setName(name);

        String type = getElementTextContent(medicineElement, MED_TYPE.getValue());
        MedicineType medType = MedicineType.getByString(type);
        checkNull(medType, MED_TYPE.getValue());
        medicine.setType(medType);

        String version = getElementTextContent(medicineElement, MED_VERSION.getValue());
        MedicineVersion medVersion = MedicineVersion.getByString(version);
        checkNull(medVersion, MED_VERSION.getValue());
        medicine.setVersion(medVersion);

        NodeList analogsList = medicineElement.getElementsByTagName(ANALOG.getValue());
        for (int i = 0; i < analogsList.getLength(); i++) {
            Element analogElement = (Element) analogsList.item(i);
            checkNull(analogElement, ANALOG.getValue());
            String analog = getElementTextContent(analogElement, ANALOG.getValue());
            medicine.addAnalog(analog);
        }

        NodeList pharmsList = medicineElement.getElementsByTagName(PHARM.getValue());
        for (int i = 0; i < pharmsList.getLength(); i++) {
            Element pharmElement = (Element) pharmsList.item(i);
            checkNull(pharmElement, PHARM.getValue());
            Pharm pharm = buildPharm(pharmElement);
            medicine.addPharm(pharm);
        }
        return medicine;
    }

    private Pharm buildPharm(Element pharmElement) throws BuilderException {
        Pharm pharm = new Pharm();
        String strID = pharmElement.getAttribute(PHARM_ID.getValue());
        try {
            long ID = Long.parseLong(strID);
            pharm.setId(ID);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String name = pharmElement.getAttribute(PHARM_NAME.getValue());
        checkEmpty(name, PHARM_NAME.getValue());
        pharm.setName(name);

        NodeList certificateList = pharmElement.getElementsByTagName(CERTIFICATE.getValue());
        Element certificateElement = (Element) certificateList.item(0);
        checkNull(certificateElement, CERTIFICATE.getValue());
        Certificate certificate = buildCertificate(certificateElement);
        pharm.setCertificate(certificate);

        NodeList dosageList = pharmElement.getElementsByTagName(DOSAGE.getValue());
        Element dosageElement = (Element) dosageList.item(0);
        checkNull(dosageElement, DOSAGE.getValue());
        Dosage dosage = buildDosage(dosageElement);
        pharm.setDosage(dosage);

        NodeList packageList = pharmElement.getElementsByTagName(PACKAGE.getValue());
        Element packageElement = (Element) packageList.item(0);
        checkNull(packageElement, PACKAGE.getValue());
        Package aPackage = buildPackage(packageElement);
        pharm.setaPackage(aPackage);

        return pharm;
    }

    private Package buildPackage(Element packageElement) throws BuilderException {
        Package aPackage = new Package();
        String strType = getElementTextContent(packageElement, PACKAGE_TYPE.getValue());
        PackageType type = PackageType.getByString(strType);
        checkNull(type, PACKAGE_TYPE.getValue());
        aPackage.setType(type);

        String strCount = getElementTextContent(packageElement, COUNT.getValue());
        try {
            int count = Integer.parseInt(strCount);
            aPackage.setCount(count);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String strValue = getElementTextContent(packageElement, VALUE.getValue());
        try {
            double value = Double.parseDouble(strValue);
            aPackage.setValue(value);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        return aPackage;
    }

    private Dosage buildDosage(Element dosageElement) throws BuilderException {
        Dosage dosage = new Dosage();
        String strDosa = getElementTextContent(dosageElement, DOSA.getValue());
        try {
            double dosa = Double.parseDouble(strDosa);
            dosage.setDosage(dosa);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        String strFreq = getElementTextContent(dosageElement, FREQUENCY.getValue());
        try {
            int freq = Integer.parseInt(strFreq);
            dosage.setFrequency(freq);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        return dosage;
    }

    private Certificate buildCertificate(Element certificateElement) throws BuilderException {
        Certificate certificate = new Certificate();
        String strNumber = getElementTextContent(certificateElement, NUMBER.getValue());
        try {
            long number = Long.parseLong(strNumber);
            certificate.setNumber(number);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }

        String regOrganization = getElementTextContent(certificateElement, REGISTR_ORGANIZATION.getValue());
        certificate.setRegistrOrganization(regOrganization);

        String strIssueDate = getElementTextContent(certificateElement, ISSUE_DATE.getValue());
        String strShelfDate = getElementTextContent(certificateElement, SHELF_DATE.getValue());
        try {
            Date issueDate = dateFormat.parse(strIssueDate);
            Date shelfDate = dateFormat.parse(strShelfDate);
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
}
