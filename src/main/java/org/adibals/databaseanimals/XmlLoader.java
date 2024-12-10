package org.adibals.databaseanimals;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlLoader {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document dataBase = null;
    public static final String HOME = System.getProperty("user.home");

    public XmlLoader() throws ParserConfigurationException, IOException, SAXException {
        System.out.println(HOME);
        dataBase = builder.parse(new File(HOME + "\\DataMaze\\data\\animal-data.xml"));
        if (dataBase == null) {
            App.genFiles();
        }
        dataBase.getDocumentElement().normalize();
    }

    private NodeList getAnimals() {
        return dataBase.getElementsByTagName("animal");
    }

    public String[] getAllAnimalNames() {
        String[] animalNames = new String[getAnimals().getLength()];
        NodeList animals = getAnimals();
        for (int i = 0; i < animals.getLength(); i++) {
            animalNames[i] = animals.item(i).getTextContent();
        }
        return animalNames;
    }

    public Node getAnimalAt(int i) {
        NodeList animals = getAnimals();
        return animals.item(i);
    }

    public static String getNameOf(Node animal) {
        NodeList names = animal.getChildNodes();
        for (int i = 0; i < names.getLength(); i++) {
            if (names.item(i).getNodeType() == Node.ELEMENT_NODE && names.item(i).getNodeName().equals("name")) {
                return names.item(i).getTextContent();
            }
        }
        return null;
    }

    private static String getElementByName(Node node, String string) {
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE && nodes.item(i).getNodeName().equals(string)) {
                return nodes.item(i).getTextContent();
            }
        }
        return null;
    }

    public static String getIDOf(Node node) {
        return node.getAttributes().getNamedItem("id").getNodeValue();
    }

    public static String getImagePathOf(Node node) {
        return System.getProperty("user.home") + "\\DataMaze\\images\\" + getElementByName(node, "image");
    }

    public static String getDescriptionOf(Node node) {
        return getElementByName(node, "description");
    }

    public static String getCategoryOf(Node node) {
        if (node.getParentNode().getAttributes().getNamedItem("id").getNodeValue() != null) {
            return node.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
        }
        return null;
    }

    public String[] getGetAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        NodeList cats = dataBase.getElementsByTagName("category");
        for (int i = 0; i < cats.getLength(); i++) {
            categories.add(cats.item(i).getAttributes().getNamedItem("id").getNodeValue());
        }
        return categories.toArray(new String[0]);
    }

    public static String getHome() {
        String docPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        return docPath;
    }
}
