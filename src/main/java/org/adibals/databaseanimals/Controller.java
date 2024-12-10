package org.adibals.databaseanimals;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Controller {
    XmlLoader xmlLoader;
    Color color;

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

    public void openColorWheel() {
        final Button button = new Button();
        final Stage colorWheelStage = new Stage();
        colorWheelStage.initModality(Modality.APPLICATION_MODAL);
        colorWheelStage.initOwner(mainImageView.getScene().getWindow());
        VBox vbox = new VBox(3);
        ColorPicker colorPicker = new ColorPicker();
        vbox.getChildren().add(new Text("Color picker"));
        vbox.getChildren().add(colorPicker);
        button.setText("Apply");
        button.setOnAction(actionEvent -> {
            colorWheelStage.close();
            color = colorPicker.getValue();
            System.out.println("Color applied: #" + color);
            mainContent.setStyle("-fx-base: #" + color.toString().substring(2) + ";");
        });
        vbox.getChildren().add(button);
        Scene colorWheelScene = new Scene(vbox, 400,400);
        colorWheelStage.setScene(colorWheelScene);
        colorWheelStage.show();

//        colorWheelStage.setTitle("Color Picker");
//        colorWheelStage.initModality(Modality.APPLICATION_MODAL);
//        colorWheelStage.initOwner(this.mainImageView.getScene().getWindow());
//        colorWheelStage.setResizable(false);
//        VBox vbox = new VBox(20);
//        vbox.getChildren().add(new Text("lorem"));
//        Scene dialogScene = new Scene(vbox, 300,300);
//        colorWheelStage.setScene(dialogScene);
//        colorWheelStage.show();
    }
}