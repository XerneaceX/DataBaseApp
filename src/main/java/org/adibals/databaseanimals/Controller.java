package org.adibals.databaseanimals;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Controller {
    XmlLoader xmlLoader;

    public Controller() throws ParserConfigurationException, IOException, SAXException {
        xmlLoader = new XmlLoader();
    }

    @FXML
    private VBox buttonContainer;

    @FXML
    private VBox rightVboxPanel;

    @FXML
    private ImageView mainImageView;

    @FXML
    private Button populateButton;

    @FXML
    private Text mainTextArea;

    @FXML
    private VBox mainContent;

    @FXML
    private AnchorPane left;

    @FXML
    private void populateButtons() {
        buttonContainer.getChildren().clear();
        HashMap<String, VBox> categories = new HashMap<>();
        for (String category : xmlLoader.getGetAllCategories()) {
            categories.put(category, createCategory(category, buttonContainer));
        }

        for (int i = 0; i < xmlLoader.getAllAnimalNames().length; i++) {
            Node animal = xmlLoader.getAnimalAt(i);
            System.out.println(XmlLoader.getCategoryOf(animal));
            Button button = new Button(XmlLoader.getNameOf(animal));
            button.setId(XmlLoader.getIDOf(animal));
            button.prefWidthProperty().bind(buttonContainer.widthProperty());
            button.getStyleClass().add("highlight");
            button.setOnAction(actionEvent -> {
                // set image
                mainImageView.setImage(null);
                File file = new File(XmlLoader.getImagePathOf(animal));
                System.out.println(file.toURI().toString());
                Image image = new Image(file.toURI().toString());
                mainImageView.setImage(image);


                // set text under
                mainTextArea.setText(XmlLoader.getDescriptionOf(animal));
            });
            categories.get(XmlLoader.getCategoryOf(animal)).getChildren().add(button);
        }
    }

    private void addChild(ArrayList<TitledPane> arrayList, String name, javafx.scene.Node node) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (Objects.equals(name, arrayList.get(i).getId())) {
                arrayList.get(i).getChildrenUnmodifiable().add(node);
            }
        }
    }

    public VBox createCategory(String name, VBox container) {
        Accordion accordion = new Accordion();
        TitledPane titledPane = new TitledPane();
        AnchorPane pane = new AnchorPane();
        VBox vbox = new VBox();
        accordion.getPanes().add(titledPane);
        titledPane.setContent(pane);
        titledPane.setText(name);
        titledPane.setContent(vbox);
        titledPane.getStyleClass().add("titled-pane");
        container.getChildren().add(accordion);
        return vbox;
    }

    public void initialize() {
        buttonContainer.prefWidthProperty().bind(left.widthProperty());
        populateButtons();
    }
}