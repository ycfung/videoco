package com.example.project.Utils;

import javafx.scene.control.Alert;

public class CustomDialog {
    public static void show(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
