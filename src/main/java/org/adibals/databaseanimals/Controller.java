package org.adibals.databaseanimals;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

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
        for (int i = 0; i < xmlLoader.getAllAnimalNames().length; i++) {
            Node animal = xmlLoader.getAnimalAt(i);
            Button button = new Button(XmlLoader.getNameOf(animal));
            button.setId(XmlLoader.getIDOf(animal));
            button.prefWidthProperty().bind(buttonContainer.widthProperty());
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
            buttonContainer.getChildren().add(button);
        }
    }

    public void initialize() {
        buttonContainer.prefWidthProperty().bind(left.widthProperty());

        populateButtons();
    }
}