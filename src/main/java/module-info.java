module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.project to javafx.fxml, java.base, javafx.graphics;
    opens com.example.project.Model to javafx.base;
    exports com.example.project;
    exports com.example.project.Controller;
    opens com.example.project.Controller to javafx.fxml, java.base, javafx.graphics;
    exports com.example.project.Utils;
    opens com.example.project.Utils to java.base, javafx.fxml, javafx.graphics;
    exports com.example.project.Repo;
    exports com.example.project.Model;
}