package edu.ifsp.view;

import edu.ifsp.controller.ControllerGrupo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowGrupo {

    private ControllerGrupo controller;


    public void showAndWait() throws IOException {

        FXMLLoader loader = new FXMLLoader();

        Pane graph = loader.load(getClass().getResource("WindowGrupo.fxml"));
        controller = loader.getController();

        Scene scene = new Scene(graph, 505, 335);
        Stage stage = new Stage();

        stage.setTitle("Gerenciar Grupos");

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}
