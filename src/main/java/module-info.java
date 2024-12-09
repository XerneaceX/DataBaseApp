module org.adibals.databaseanimals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.adibals.databaseanimals to javafx.fxml;
    exports org.adibals.databaseanimals;
}