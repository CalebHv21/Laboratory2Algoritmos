module org.example.laboratory2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.laboratory2 to javafx.fxml;
    exports org.example.laboratory2;
    exports controller;
    opens controller to javafx.fxml;
}