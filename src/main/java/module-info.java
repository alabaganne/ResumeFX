module com.example.resumefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.resumefx to javafx.fxml;
    exports com.example.resumefx;
}