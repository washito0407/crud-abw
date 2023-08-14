module com.example.crudabw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.example.crudabw to javafx.fxml;
    exports com.example.crudabw;
}