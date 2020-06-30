package by.training.module4.builder;

import by.training.module4.entity.Container;
import by.training.module4.entity.Ship;
import by.training.module4.pool.PortPool;
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
import java.util.ArrayList;
import java.util.List;

import static by.training.module4.builder.ShipEnum.*;

public class ShipDomBuilder extends Builder<Ship> {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void buildList(String fileNme) throws BuilderException {
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
        NodeList shipList = root.getElementsByTagName(SHIP.getValue());

        for (int i = 0; i < shipList.getLength(); i++) {
            Element shipElement = (Element) shipList.item(i);
            checkNull(shipElement, SHIP.getValue());
            Ship ship = buildShip(shipElement);
            items.add(ship);
        }
    }

    private Ship buildShip(Element shipElement) throws BuilderException {
        LOG.info("Build ship component.");
        Ship ship = new Ship();
        String strID = shipElement.getAttribute(SHIP_ID.getValue());
        try {
            long ID = Long.parseLong(strID);
            LOG.info("Set id [" + ID + "].");
            ship.setId(ID);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        String name = shipElement.getAttribute(SHIP_NAME.getValue());
        checkEmpty(name, SHIP_NAME.getValue());
        ship.setName(name);
        LOG.info("Set name [" + name + "].");
        String strCapacity = getElementTextContent(shipElement, CAPACITY.getValue());
        try {
            int capacity = Integer.parseInt(strCapacity);
            LOG.info("Set capacity [" + capacity + "].");
            ship.setCapacity(capacity);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        NodeList containersList = shipElement.getElementsByTagName(CONTAINERS.getValue());
        Element containerElement = (Element) containersList.item(0);
        if (containerElement != null) {
            List<Container> containers = buildContainers(containerElement);
            LOG.info("Add containers [" + containers + "] to the ship [" + name + "].");
            ship.setContainers(containers);
        } else {
            LOG.info("Ship [" + name + "] don't contain containers.");
        }
        ship.setPortPool(PortPool.getInstance());
        return ship;
    }

    private List<Container> buildContainers(Element containersElement) throws BuilderException {
        List<Container> containers = new ArrayList<>();
        NodeList containerList = containersElement.getElementsByTagName(CONTAINER.getValue());
        for (int i = 0; i < containerList.getLength(); i++) {
            Element containerElement = (Element) containerList.item(i);
            if (containerElement != null) {
                String analog = containerElement.getTextContent();
                Container container = buildContainer(containerElement);
                containers.add(container);
            }
        }
        return containers;
    }

    private Container buildContainer(Element containerElement) throws BuilderException {
        Container container = new Container();
        LOG.info("Build container component.");
        String strID = containerElement.getAttribute(CONTAINER_ID.getValue());
        try {
            long ID = Long.parseLong(strID);
            LOG.info("Set id to container [" + ID + "].");
            container.setId(ID);
        } catch (NumberFormatException e) {
            throw new BuilderException(e);
        }
        String name = containerElement.getAttribute(CONTAINER_NAME.getValue());
        checkNull(name, CONTAINER_NAME.getValue());
        container.setName(name);
        LOG.info("Set name to container [" + name + "].");
        return container;
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

    private void checkNull(Object s, String e) throws BuilderException {
        if (s == null) {
            throw new BuilderException(new NullPointerException(e + " is null."));
        }
    }

    private void checkEmpty(String s, String e) throws BuilderException {
        if (s.isEmpty()) {
            throw new BuilderException(new IllegalArgumentException(e));
        }
    }
}
