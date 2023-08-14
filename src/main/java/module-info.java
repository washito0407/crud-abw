module com.example.crudabw {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.crudabw to javafx.fxml;
    exports com.example.crudabw;
}